package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * Manages loaded trades to prevent circular loading
 */
public class TradeManager implements IObserver {
	private Map<String, Trade> trades = new HashMap<String, Trade>();

	// Get/Set

	/**
	 * @return A list of loaded trades
	 */
	public List<Trade> getTrades() {
		List<Trade> users = new ArrayList<Trade>();
		for (Trade user : this.trades.values()) {
			users.add(user);
		}
		return users;
	}

	/**
	 * @param id the Id of the trade to look for
	 * @return the trade or null if not found
	 */
	public Trade getTrade(String id) {
		// If we have the trade loaded
		if (this.trades.containsKey(id)) {
			return this.trades.get(id);
		}
		// TODO:
		try {
			SearchHit<Trade> loadedTrades = IOManager.sharedManager().fetchData(Constants.serverTradeExtension() + id, new TypeToken<SearchHit<Trade>>() {
			});
			if (loadedTrades.isFound()) {
				this.addTrade(loadedTrades.getSource());
				return loadedTrades.getSource();
			} else {
				// TODO:
				return null;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param owner  the owner of the service the borrower wants
	 * @param isCounter if its a counter offer or not
	 * @return an empty trade that should be modified then committed
	 */
	public Trade newTrade(User owner, boolean isCounter) {
		Trade trade = new Trade(UUID.randomUUID().toString(), isCounter, owner.getEmail(), MyApplication.getLocalUser().getEmail());
		return trade;
	}

	void addTrade(Trade trade) {
		trade.addObserver(this);
		this.trades.put(trade.getID(), trade);
	}

	/**
	 * Remove the trade from the system and users. Trade should not be null.
	 * @param trade The trade to be removed from the system
	 */
	void removeTrade(Trade trade) {
		trade.removeObserver(this);
		this.trades.remove(trade.getID());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade.getID());
	}

	/**
	 * Clear the loaded trades so they get reloaded
	 */
	public void clearCache() {
		this.trades.clear();
		// TODO:
	}

	// Singleton
	private static TradeManager instance = new TradeManager();

	private TradeManager() {
	}

	/** get the instance of the singleton */
	public static TradeManager sharedManager() {
		return TradeManager.instance;
	}

	// IObserver
	@Override
	/** gets notified by things its watching */
	public void notify(IObservable observable) {
		// TODO:
		Constants.refreshSync = true;
		if (Constants.isOnline) {
			IOManager.sharedManager().storeData(observable, Constants.serverTradeExtension() + ((Trade) observable).getID());
		}
	}
}
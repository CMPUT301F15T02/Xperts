package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;


/**
 * Manages loaded trades to prevent circular loading
 */
public class TradeManager implements IObserver {

	private Map<String, Trade> trades = new HashMap<String, Trade>();
	private ArrayList<Trade> diskTrades = new ArrayList<Trade>();

	/**
	 * Return the found trade, always find online first, only if no internet cache will be loaded
	 * @param id the Id of the trade to look for
	 * @return the trade or null if not found
	 */
	public Trade getTrade(String id) {

		// Push local user's trades if have internet
		diskTrades = IOManager.sharedManager().loadFromFile(MyApplication.getContext(), new TypeToken<ArrayList<Trade>>() {
		}, Constants.diskTrade);
		if (Constants.tradesSync) {
			if (diskTrades != null) {
				try {
					for (Trade trade : diskTrades) {
						IOManager.sharedManager().storeData(trade, Constants.serverTradeExtension() + trade.getID());
					}
					Constants.tradesSync = false;
				} catch (Exception e) {
					// no internet
				}
			}
		}

		// If we have the trade loaded
		if (this.trades.containsKey(id)) {
			return this.trades.get(id);
		}

		try {
			SearchHit<Trade> loadedTrade = IOManager.sharedManager().fetchData(Constants.serverTradeExtension() + id, new TypeToken<SearchHit<Trade>>() {
			});
			if (loadedTrade.isFound()) {
				this.addTrade(loadedTrade.getSource());
				return loadedTrade.getSource();
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
		} catch (Exception e) {

			// no internet
			if (diskTrades != null) {
				for (Trade trade : diskTrades) {
					if (trade.getID().equals(id)) {
						return trade;
					}
				}
			}
		}

		return null;
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
		boolean contains = false;

		// Write disk first
		// Don't add same object
		for (Trade s : diskTrades) {
			if (s.getID().equals(trade.getID())) {
				contains = true;
				break;
			}
		}
		if (!contains) {
			diskTrades.add(trade);
			Constants.tradesSync = true;
		}
		IOManager.sharedManager().writeToFile(diskTrades, MyApplication.getContext(), Constants.diskTrade);

		trade.addObserver(this);
		this.trades.put(trade.getID(), trade);
		this.notify(trade);
	}

	/**
	 * Remove the trade from the system and users. Trade should not be null.
	 * @param trade The trade to be removed from the system
	 */
	void removeTrade(Trade trade) {
		// Delete disk, no sync
		for (Trade t : diskTrades) {
			if (t.getID().equals(trade.getID())) {
				diskTrades.remove(t);
				break;
			}
		}
		IOManager.sharedManager().writeToFile(diskTrades, MyApplication.getContext(), Constants.diskTrade);

		trade.removeObserver(this);
		this.trades.remove(trade.getID());
		try {
			IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade.getID());
		} catch (Exception e) {

		}
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
		try {
			IOManager.sharedManager().storeData(observable, Constants.serverTradeExtension() + ((Trade) observable).getID());
		} catch (Exception e) {

		}
	}
}
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
import ca.ualberta.cs.xpertsapp.model.es.SearchResponse;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * Manages loaded trades to prevent circular loading
 */
public class TradeManager implements IObserver {
	private Map<String, Trade> trades = new HashMap<String, Trade>();

	// Get/Set

	/**
	 * For cache services
	 */
	public void setTrades(List<Trade> trades) {
		for (Trade trade: trades) {
			addTrade(trade);
		}
	}

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
		return null;
	}

	/**
	 * @param borrower  the borrower of the trade
	 * @param isCounter if its a counter offer or not
	 * @return an empty trade that should be modified then committed
	 */
	public Trade newTrade(User borrower, boolean isCounter) {
		Trade trade = new Trade(UUID.randomUUID().toString(), isCounter, MyApplication.getLocalUser().getEmail(), borrower.getEmail());
		return trade;
	}

	void addTrade(Trade trade) {
		trade.addObserver(this);
		this.trades.put(trade.getID(), trade);
		this.notify(trade);
	}

	/**
	 * Clear the loaded trades so they get reloaded
	 */
	public void clearCache() {
		this.trades.clear();
		// TODO:
	}

	/**
	 * @param meta the meta to search for
	 * @return the list of found trades
	 */
	public List<Trade> findTrades(String meta) {
		List<SearchHit<Trade>> found = IOManager.sharedManager().searchData(Constants.serverTradeExtension() + meta, new TypeToken<SearchResponse<Trade>>() {
		});
		List<Trade> trades = new ArrayList<Trade>();
		for (SearchHit<Trade> trade : found) {
			//trades.add(this.getTrade(trade.getSource().getID()));
			trades.add(trade.getSource());
		}
		return trades;
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
			Constants.tradesSync = true;
		}
	}
}
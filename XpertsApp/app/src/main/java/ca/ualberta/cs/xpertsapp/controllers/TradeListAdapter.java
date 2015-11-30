package ca.ualberta.cs.xpertsapp.controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Trade;

/**
 * Created by kmbaker on 11/24/15.
 */
public class TradeListAdapter extends BaseAdapter {


    public List<Trade> tradeData;
    private Context context;
    private LayoutInflater inflater;

    /**
     * Contructor for this Adapter
     * @param trades The list of trades to create an adapter for
     */
    public TradeListAdapter(Context context, List<Trade> trades) {
        this.context = context;
        this.tradeData = trades;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Updates the ListView with a new list of trades
     * @param trades the new list of trades
     */
    public void updateTradeList(List<Trade> trades) {
        tradeData.clear();
        tradeData.addAll(trades);
        this.notifyDataSetChanged();
    }

    /**
     * @return  Number of trades in the adapter
     */
    @Override
    public int getCount() {
        return tradeData.size();
    }

    /**
     * @param position of a trade in the adapter
     * @return The trade belonging to the ListItem in the given position
     */
    @Override
    public Trade getItem(int position) {
        return tradeData.get(position);
    }

    /**
     * @param position of an item
     * @return the position of the item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Lays out the view of a list item representing a trade.
     * @param position of an item in the trade list
     * @param tradeListItem to layout the view for
     * @return the view for the given service list item
     */
    @Override
    public View getView(int position, View tradeListItem, ViewGroup parent) {
        View vi = tradeListItem;
        if (vi == null)
            vi = inflater.inflate(R.layout.trade_list_item, null);
        Trade trade = tradeData.get(position);
        TextView user = (TextView) vi.findViewById(R.id.trade_list_borrower);
        TextView service = (TextView) vi.findViewById(R.id.trade_list_service);
        TextView category = (TextView) vi.findViewById(R.id.trade_list_category);
        TextView state = (TextView) vi.findViewById(R.id.trade_list_state);
        TextView recipient = (TextView) vi.findViewById(R.id.trade_list_receivedRequestFor);

        if (trade.getBorrower().equals(MyApplication.getLocalUser())){
            user.setText(trade.getOwner().getName());
        } else {
            user.setText(trade.getBorrower().getName());
        }
        service.setText(trade.getOwnerServices().get(0).getName());
        category.setText(trade.getOwnerServices().get(0).getCategory().toString());


        //status = 0 -> pending
        //status = 1 -> in progress
        //status = 2 -> cancelled
        //status = 3 -> declined
        //status = 4 -> completed
        if (trade.getStatus()==0) {
            state.setText("• Pending");
            state.setTextColor(Color.parseColor("#ffB7C0C7"));
        } else if (trade.getStatus()==1) {
            state.setText("• In progress");
            state.setTextColor(Color.parseColor("#FFFFC045"));
        } else if (trade.getStatus()==2) {
            state.setText("• Cancelled");
            state.setTextColor(Color.parseColor("#ffD0021B"));
        } else if (trade.getStatus()==3) {
            state.setText("• Declined");
            state.setTextColor(Color.parseColor("#ffD0021B"));
        } else if (trade.getStatus() == 4) {
            state.setText("• Completed");
            state.setTextColor(Color.parseColor("#ff51B46D"));
        }

        if (trade.getBorrower().equals(MyApplication.getLocalUser())) {
            recipient.setText("RECEIVED YOUR REQUEST FOR");
        } else {
            recipient.setText("MADE AN OFFER FOR");
        }
        return vi;
    }
}

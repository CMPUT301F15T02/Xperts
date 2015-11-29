package ca.ualberta.cs.xpertsapp.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.User;

/**
 * Adapter to build ListView of friends.
 */

/**
 * This is a Adapter to display top traders in  a listview.
 */
public class TopTraderAdapter extends BaseAdapter {
    public List<User> userData;
    private Context context;
    private LayoutInflater inflater;

    /**
     * Constructor
     * @param context Context from the activity
     * @param traders List of users
     */
    public TopTraderAdapter(Context context, List<User> traders) {
        this.context = context;
        this.userData = traders;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * clears and re-adds friends list to adapter.
     * @param traders List of users
     */
    public void updateFriendsList(List<User> traders) {
        userData.clear();
        userData.addAll(traders);
        this.notifyDataSetChanged();
    }

    /**
     * Calculates the top trader score for a user
     * @param user The user to calculate the trader score for
     * @return The number of in progress and completed trades for the user
     */
    public Integer getTraderScore(User user) {
        List<Trade> trades = user.getTrades();
        Integer score = 0;
        for(Trade t : trades) {
            int status = t.getStatus();
            if (status == 1 || status == 4)
                score++;
        }
        return score;
    }

    /**
     * @return size of friends list
     */
    @Override
    public int getCount() {
        return userData.size();
    }

    /**
     * @param position position in listView
     * @return The User at position in the list
     */
    @Override
    public User getItem(int position) {
        return userData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Sets the textViews with the user's name and email.
     * @param position position in list
     * @param friendListItem View
     * @param parent ViewGroup
     * @return View that is friendListItem
     */
    @Override
    public View getView(int position, View friendListItem, ViewGroup parent) {
        View vi = friendListItem;
        if (vi == null) {
            vi = inflater.inflate(R.layout.top_trader_list_item, null);
        }
        User u = userData.get(position);
        TextView u_name = (TextView) vi.findViewById(R.id.friend_list_name);
        TextView u_email = (TextView) vi.findViewById(R.id.friend_list_email);
        TextView u_score = (TextView) vi.findViewById(R.id.num_trades);

        u_name.setText(u.getName());
        u_email.setText(u.getEmail());
        u_score.setText(getTraderScore(u).toString());
        return vi;
    }
}

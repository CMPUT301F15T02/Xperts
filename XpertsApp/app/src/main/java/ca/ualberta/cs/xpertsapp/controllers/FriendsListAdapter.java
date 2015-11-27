package ca.ualberta.cs.xpertsapp.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.User;

/**
 * Adapter to build ListView of friends.
 */

/**
 * This is a Adapter to display friends in  a listview.
 */
public class FriendsListAdapter extends BaseAdapter {
    public List<User> friendData;
    private Context context;
    private LayoutInflater inflater;

    /**
     * Constructor
     * @param context Context from the activity
     * @param friends List of users
     */
    public FriendsListAdapter(Context context, List<User> friends) {
        this.context = context;
        this.friendData = friends;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * clears and re-adds friends list to adapter.
     * @param friends List of users
     */
    public void updateFriendsList(List<User> friends) {
        friendData.clear();
        friendData.addAll(friends);
        this.notifyDataSetChanged();
    }

    /**
     * @return size of friends list
     */
    @Override
    public int getCount() {
        return friendData.size();
    }

    /**
     * @param position position in listView
     * @return The User at position in the list
     */
    @Override
    public User getItem(int position) {
        return friendData.get(position);
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
            vi = inflater.inflate(R.layout.friend_list_item, null);
        }
        User u = friendData.get(position);
        TextView u_name = (TextView) vi.findViewById(R.id.friend_list_name);
        TextView u_email = (TextView) vi.findViewById(R.id.friend_list_email);

        u_name.setText(u.getName());
        u_email.setText(u.getEmail());
        return vi;
    }
}

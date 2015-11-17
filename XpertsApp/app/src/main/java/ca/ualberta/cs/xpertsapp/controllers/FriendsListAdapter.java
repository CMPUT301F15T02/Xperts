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
 * Created by kmbaker on 11/5/15.
 */

/**
 * This is a Adapter to display friends in  a listview.
 */
public class FriendsListAdapter extends BaseAdapter {
    public List<User> friendData;
    private Context context;
    private LayoutInflater inflater;

    public FriendsListAdapter(Context context, List<User> friends) {
        this.context = context;
        this.friendData = friends;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateFriendsList(List<User> friends) {
        friendData.clear();
        friendData.addAll(friends);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return friendData.size();
    }

    @Override
    public User getItem(int position) {
        // TODO Auto-generated method stub
        return friendData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View friendListItem, ViewGroup parent) {
        // TODO Auto-generated method stub
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

package ca.ualberta.cs.xpertsapp.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.User;

/**
 *  Adapter to build ListView of services
 *  @author Hammad Jutt
 */
public class ServiceListAdapter extends BaseAdapter {

    public List<Service> serviceData;
    private Context context;
    private LayoutInflater inflater;

    public ServiceListAdapter(Context context, List<Service> services) {
        this.context = context;
        this.serviceData = services;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /**
     * Updates the ListView with a new list of services
     * @param services the new list of services
     */
    public void updateServiceList(List<Service> services) {
        serviceData.clear();
        serviceData.addAll(services);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return serviceData.size();
    }

    @Override
    public Service getItem(int position) {
        return serviceData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View serviceListItem, ViewGroup parent) {
        View vi = serviceListItem;
        if (vi == null)
            vi = inflater.inflate(R.layout.service_list_item, null);
        Service s = serviceData.get(position);
        TextView s_name = (TextView) vi.findViewById(R.id.service_list_name);
        TextView s_owner = (TextView) vi.findViewById(R.id.service_list_owner);
        TextView s_category = (TextView) vi.findViewById(R.id.service_list_category);

        s_name.setText(s.getName());
        s_owner.setText(s.getOwner().getName());
        s_category.setText(s.getCategory().toString());
        return vi;
    }
}
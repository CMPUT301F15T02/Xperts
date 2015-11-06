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


public class ServiceListAdapter extends BaseAdapter {

    public List<Service> serviceData;
    private Context context;
    private LayoutInflater inflater;

    public ServiceListAdapter(Context context, List<Service> services) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.serviceData = services;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateServiceList(List<Service> services) {
        serviceData.clear();
        serviceData.addAll(services);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return serviceData.size();
    }

    @Override
    public Service getItem(int position) {
        // TODO Auto-generated method stub
        return serviceData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View serviceListItem, ViewGroup parent) {
        // TODO Auto-generated method stub
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
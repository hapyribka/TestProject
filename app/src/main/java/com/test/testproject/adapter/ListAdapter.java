package com.test.testproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.test.testproject.R;
import com.test.testproject.model.Order;
import com.test.testproject.model.Orders;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<Orders> imageList = new ArrayList<>();

    private class ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
    }

    public ListAdapter(List<Orders> data){
        imageList = data;
    }

    @Override
    public int getCount() {
        return imageList != null ? imageList.size() : 0;
    }

    @Override
    public Orders getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(R.id.text1);
            holder.text2 = (TextView) convertView.findViewById(R.id.text2);
            holder.text3 = (TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StringBuffer stb = new StringBuffer();
        for(Order order : getItem(position).getOrderList()) {
            if(stb.length() != 0) {
                stb.append("\n");
            }
            stb.append(order.getPart_name());
        }
        holder.text1.setText(stb.toString());
        holder.text2.setText(getItem(position).getCar().getManufacturer_id());
        holder.text3.setText(getItem(position).getCar().getModel_id());
        return convertView;
    }
}

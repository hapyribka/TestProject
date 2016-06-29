package com.preggiapp.testproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.model.AppComponent;
import java.util.ArrayList;

public class AppComponentAdapter extends  RecyclerView.Adapter<AppComponentAdapter.CallViewHolder> {

    private ArrayList<AppComponent> items;

    public AppComponentAdapter(ArrayList<AppComponent> items) {
        this.items = items;
    }

    static class CallViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public ImageView photo;

        public CallViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.ava);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
        }
    }

    public AppComponent getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public CallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.component_item, parent, false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CallViewHolder holder, final int position) {
        AppComponent component = getItem(position);
        holder.name.setText(component.getName());
        holder.photo.setImageDrawable(component.getIcon());
    }
}

package com.preggiapp.testproject.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.model.Contact;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ContactAdapter extends  RecyclerView.Adapter<ContactAdapter.CallViewHolder> {

    private ArrayList<Contact> items;
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void setItems(ArrayList<Contact> items) {
        this.items = items;
        notifyDataSetChanged();
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

    public int getCount() {
        return items == null ? 0 : items.size();
    }

    public Contact getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public CallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CallViewHolder holder, final int position) {
        Contact contact = getItem(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        if(contact.getAva() != null) {
            Picasso.with(context).load(contact.getAva()).into(holder.photo);
        }
    }
}

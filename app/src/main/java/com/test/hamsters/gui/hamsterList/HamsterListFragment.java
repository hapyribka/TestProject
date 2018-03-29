package com.test.hamsters.gui.hamsterList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BaseConnectionFragment;
import com.test.hamsters.base.fragment.BasePresenter;
import com.test.hamsters.models.Hamster;
import java.util.ArrayList;
import butterknife.BindView;

public class HamsterListFragment extends BaseConnectionFragment implements HamsterListMvpView {

    private HamsterListPresenter presenter = new HamsterListPresenter();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hamsters_list;
    }

    @BindView(R.id.list)
    RecyclerView list;

    private HamstersAdapter adapter;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HamstersAdapter();
        list.setAdapter(adapter);
        presenter.attachView(this);
    }

    @Override
    public void showData(ArrayList<Hamster> hamsters) {
        if(hamsters != null) {
            list.setVisibility(View.VISIBLE);
            adapter.setItems(hamsters);
        } else {
            list.setVisibility(View.GONE);
        }
    }

    private class HamstersAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        private ArrayList<Hamster> items = new ArrayList<>();

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.hamster_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
            final Hamster item = items.get(position);
            holder.title.setText(item.getTitle());
            holder.description.setText(item.getDescription());
            Glide.with(getActivity())
                    .load(item.getImage())
                    .centerCrop()
                    .into(holder.image);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onItemSelected(item);
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(ArrayList<Hamster> items) {
            this.items = items;
            notifyDataSetChanged();
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView image;
        private TextView title;
        private TextView description;
        private FrameLayout layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            image = (RoundedImageView) itemView.findViewById(R.id.image);
            layout = (FrameLayout) itemView.findViewById(R.id.layout);
        }
    }
}
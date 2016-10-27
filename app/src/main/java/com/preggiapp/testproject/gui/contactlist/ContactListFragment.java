package com.preggiapp.testproject.gui.contactlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.gui.activity.MainActivity;
import com.preggiapp.testproject.gui.adapter.ContactAdapter;
import com.preggiapp.testproject.gui.base.BaseConnectionFragment;
import com.preggiapp.testproject.gui.base.BasePresenter;
import com.preggiapp.testproject.model.Contact;
import java.util.ArrayList;

public class ContactListFragment extends BaseConnectionFragment implements ContactListMvpView{

    private ContactListPresenter presenter = new ContactListPresenter();

    private ContactAdapter adapter;
    private RecyclerView list;

    @Override
    public int getLayoutResId() {
        return R.layout.contactlist_fragment;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).enableBackButton();
        list = (RecyclerView)view.findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        adapter = new ContactAdapter(getActivity());
        list.setAdapter(adapter);
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void updateList(ArrayList<Contact> contacts) {
        if(adapter != null) {
            adapter.setItems(contacts);
        }
    }
}
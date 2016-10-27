package com.preggiapp.testproject.gui.userlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.gui.activity.MainActivity;
import com.preggiapp.testproject.gui.adapter.AppComponentAdapter;
import com.preggiapp.testproject.gui.adapter.UserPageAdapter;
import com.preggiapp.testproject.gui.base.BaseConnectionFragment;
import com.preggiapp.testproject.gui.base.BasePresenter;
import com.preggiapp.testproject.model.AppComponent;
import com.preggiapp.testproject.model.User;
import java.util.ArrayList;

public class UserListFragment extends BaseConnectionFragment implements UserListMvpView {

    private UserListPresenter presenter = new UserListPresenter();

    private ViewPager pager;
    private RecyclerView list;
    private UserPageAdapter adapter;
    private AppComponentAdapter componentAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.userlist_fragment;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).enableContactsButton();
        getActivity().setTitle(getString(R.string.user_list));
        pager = (ViewPager)view.findViewById(R.id.pager);
        initPager();
        list = (RecyclerView)view.findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        componentAdapter = new AppComponentAdapter();
        list.setAdapter(componentAdapter);
        presenter.attachView(this);
    }

    private void initPager() {
        if (pager != null) {
            adapter = new UserPageAdapter(getChildFragmentManager());
            pager.setAdapter(adapter);
            int width = (int) getResources().getDimension(R.dimen.image_width);
            int screenWidth = getScreenWidth();
            pager.setClipToPadding(false);
            pager.setPadding((screenWidth - width) / 2, 0, (screenWidth - width) / 2, 0);
            pager.setPageMargin(5);
            pager.setCurrentItem(0);
        }
    }

    public void updatePager(ArrayList<User> users) {
        if (adapter != null) {
            adapter.setItems(users);
        }
    }

    public void updateList(ArrayList<AppComponent> components) {
        if(componentAdapter != null) {
            componentAdapter.setItems(components);
        }
    }
}
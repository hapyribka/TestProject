package com.preggiapp.testproject.gui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.preggiapp.testproject.gui.userlist.ImageFragment;
import com.preggiapp.testproject.model.User;
import java.util.ArrayList;

public class UserPageAdapter extends FragmentPagerAdapter {

    private ArrayList<User> userList;

    public UserPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setItems( ArrayList<User> users) {
        userList = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userList == null ? 0 : userList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.createImageFragment(userList.get(position));
    }
}

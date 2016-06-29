package com.preggiapp.testproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.preggiapp.testproject.fragment.ImageFragment;
import com.preggiapp.testproject.model.User;
import java.util.ArrayList;

public class UserPageAdapter extends FragmentPagerAdapter {

    private ArrayList<User> userList;

    public UserPageAdapter(FragmentManager fm, ArrayList<User> userList) {
        super(fm);
        this.userList = userList;
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

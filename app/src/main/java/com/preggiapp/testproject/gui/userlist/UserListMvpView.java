package com.preggiapp.testproject.gui.userlist;

import com.preggiapp.testproject.gui.base.MvpView;
import com.preggiapp.testproject.model.AppComponent;
import com.preggiapp.testproject.model.User;
import java.util.ArrayList;

public interface UserListMvpView extends MvpView {

    void updatePager(ArrayList<User> users);
    void updateList(ArrayList<AppComponent> components);
}
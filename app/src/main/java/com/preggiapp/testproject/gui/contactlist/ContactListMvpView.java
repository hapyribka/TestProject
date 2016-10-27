package com.preggiapp.testproject.gui.contactlist;

import com.preggiapp.testproject.gui.base.MvpView;
import com.preggiapp.testproject.model.Contact;
import java.util.ArrayList;

public interface ContactListMvpView extends MvpView {

    void updateList(ArrayList<Contact> contacts);
}
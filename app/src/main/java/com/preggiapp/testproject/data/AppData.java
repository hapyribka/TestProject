package com.preggiapp.testproject.data;

import com.preggiapp.testproject.model.AppComponent;
import com.preggiapp.testproject.model.Contact;
import com.preggiapp.testproject.model.User;
import java.util.ArrayList;

public class AppData {

    private static AppData instance;
    private ArrayList<User> userList;
    private ArrayList<Contact> contactList;
    private ArrayList<AppComponent> componentList;


    public static AppData getInstance() {
        if(instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    private AppData() {
    }

    public ArrayList<AppComponent> getComponentList() {
        return componentList;
    }

    public void setComponentList(ArrayList<AppComponent> componentList) {
        this.componentList = componentList;
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
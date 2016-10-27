package com.preggiapp.testproject.gui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.gui.contactlist.ContactListFragment;
import com.preggiapp.testproject.gui.userlist.UserListFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Menu commonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addFragment(new UserListFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        commonMenu = menu;
        enableContactsButton();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                    onBackPressed();
                break;

            case R.id.action_contacts:
                    addFragment(new ContactListFragment());
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentById(R.id.container) instanceof ContactListFragment) {
            addFragment(new UserListFragment());
        } else {
            finish();
        }
    }

    public void clearMenu() {
        if(commonMenu != null) {
            commonMenu.clear();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void enableContactsButton() {
        clearMenu();
        if(commonMenu != null) {
            getMenuInflater().inflate(R.menu.contacts, commonMenu);
        }
    }

    public void enableBackButton() {
        clearMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, MainActivity.class.getSimpleName()).commit();

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

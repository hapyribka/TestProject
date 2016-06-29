package com.preggiapp.testproject.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.fragment.ContactListFragment;
import com.preggiapp.testproject.fragment.UserListFragment;

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
        enableSearchButton();
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

            case R.id.action_search:
                    MenuItem myActionMenuItem = commonMenu.findItem(R.id.action_search);
                    final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            if (!searchView.isIconified()) {
                                searchView.setIconified(true);
                            }
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String search) {
                        return false;
                    }
                    });
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

    public void enableSearchButton() {
        clearMenu();
        if(commonMenu != null) {
            getMenuInflater().inflate(R.menu.search, commonMenu);
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

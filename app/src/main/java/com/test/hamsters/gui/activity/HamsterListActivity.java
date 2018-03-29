package com.test.hamsters.gui.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.test.hamsters.R;
import com.test.hamsters.base.activity.BaseActivity;
import com.test.hamsters.event.SearchEvent;
import com.test.hamsters.gui.hamsterList.HamsterListFragment;
import org.greenrobot.eventbus.EventBus;

public class HamsterListActivity extends BaseActivity {

    @Override
    public int getActionBarTitile() {
        return R.string.app_name;
    }

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new HamsterListFragment());
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.search_menu, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                EventBus.getDefault().post(new SearchEvent(s));
                return false;
            }
        });
        return true;
    }

    public void finish() {
        if(!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.finish();
        }
    }
}
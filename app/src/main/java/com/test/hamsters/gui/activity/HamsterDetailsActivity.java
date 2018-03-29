package com.test.hamsters.gui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.test.hamsters.R;
import com.test.hamsters.base.activity.BaseActivity;
import com.test.hamsters.event.ShareDetailsEvent;
import com.test.hamsters.gui.hamsterDetails.HamsterDetailsFragment;
import com.test.hamsters.models.Hamster;
import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

public class HamsterDetailsActivity extends BaseActivity {

    @Override
    public int getActionBarTitile() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hamster hamster = Parcels.unwrap(getIntent().getParcelableExtra(HamsterDetailsFragment.HAMSTER_KEY));
        toolbar.setTitle(hamster.getTitle());
        addFragment(HamsterDetailsFragment.createInstance(hamster));
        enableBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_share:
                    EventBus.getDefault().post(new ShareDetailsEvent());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
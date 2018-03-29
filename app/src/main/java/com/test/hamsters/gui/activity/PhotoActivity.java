package com.test.hamsters.gui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.test.hamsters.R;
import com.test.hamsters.base.activity.BaseActivity;
import com.test.hamsters.event.DownloadImageEvent;
import com.test.hamsters.event.ShareImageEvent;
import com.test.hamsters.gui.photo.PhotoFragment;
import org.greenrobot.eventbus.EventBus;

public class PhotoActivity extends BaseActivity {

    public static final String TITLE_KEY = "title_key";

    @Override
    public int getActionBarTitile() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(PhotoFragment.createInstance(getIntent().getStringExtra(PhotoFragment.IMAGE_URL)));
        toolbar.setTitle(getIntent().getStringExtra(TITLE_KEY));
        enableBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_share:
                    EventBus.getDefault().post(new ShareImageEvent());
                break;
            case R.id.action_save:
                    EventBus.getDefault().post(new DownloadImageEvent());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
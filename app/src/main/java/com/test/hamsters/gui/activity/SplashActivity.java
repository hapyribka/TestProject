package com.test.hamsters.gui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.test.hamsters.base.activity.BaseActivity;
import com.test.hamsters.gui.splash.SplashFragment;

public class SplashActivity extends BaseActivity {

    @Override
    public int getActionBarTitile() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        toolbar.setVisibility(View.GONE);
        addFragment(new SplashFragment());
    }
}
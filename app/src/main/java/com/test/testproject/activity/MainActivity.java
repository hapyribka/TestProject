package com.test.testproject.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;
import com.test.testproject.R;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity);
    }
}

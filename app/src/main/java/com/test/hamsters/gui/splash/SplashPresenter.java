package com.test.hamsters.gui.splash;

import android.content.Intent;
import android.os.CountDownTimer;
import com.test.hamsters.base.fragment.BasePresenter;
import com.test.hamsters.gui.activity.HamsterListActivity;

public class SplashPresenter extends BasePresenter<SplashMvpView> {

    private static final long SPLASH_TIME = 3000;

    @Override
    public void attachView(SplashMvpView view) {
        super.attachView(view);
        new CountDownTimer(SPLASH_TIME, 200) {
            public void onTick(long millisUntilFinished) {            }
            public void onFinish() {
                complete();
            }
        }.start();
    }

    public void complete() {
        startActivity(new Intent(getContext(), HamsterListActivity.class));
        closeActivity();
    }
}
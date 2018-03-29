package com.test.hamsters.gui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BaseConnectionFragment;
import com.test.hamsters.base.fragment.BasePresenter;

public class SplashFragment extends BaseConnectionFragment implements SplashMvpView {

    private SplashPresenter presenter = new SplashPresenter();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_splash;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }
}
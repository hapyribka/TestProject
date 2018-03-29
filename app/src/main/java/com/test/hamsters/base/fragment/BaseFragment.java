package com.test.hamsters.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.test.hamsters.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements MvpView {

    private View commonView;

    public abstract @LayoutRes int getLayoutResId();
    public abstract BasePresenter getPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        commonView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, commonView);
        return commonView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getStringRes(int stringId) {
        return getString(stringId);
    }

    @Override
    public void onDestroy() {
        getPresenter().detachView();
        super.onDestroy();
    }
}
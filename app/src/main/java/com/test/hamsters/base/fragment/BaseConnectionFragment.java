package com.test.hamsters.base.fragment;

import com.test.hamsters.base.activity.BaseActivity;

public abstract class BaseConnectionFragment extends BaseFragment implements MvpView {

    @Override
    public String getStringRes(int stringId) {
        return getString(stringId);
    }

    @Override
    public boolean isOnline() {
        return getActivity() == null ? false : ((BaseActivity) getActivity()).isOnline();
    }

    @Override
    public void startProgressDialog() {
        if(getActivity() != null) {
            ((BaseActivity) getActivity()).startProgressDialog();
        }
    }

    @Override
    public void stopProgressDialog() {
        if(getActivity() != null) {
            ((BaseActivity) getActivity()).stopProgressDialog();
        }
    }
}
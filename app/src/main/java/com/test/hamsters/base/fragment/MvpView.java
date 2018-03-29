package com.test.hamsters.base.fragment;

public interface MvpView {

    boolean isOnline();

    void startProgressDialog();

    void stopProgressDialog();

    void showToast(String message);

    String getStringRes(int stringId);
}

package com.preggiapp.testproject.gui.base;

public interface MvpView {

    void startProgressDialog();
    void stopProgressDialog();
    void showToast(String message);
    String getStringRes(int stringId);
    boolean isOnline();

}

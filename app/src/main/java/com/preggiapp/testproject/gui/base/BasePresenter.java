package com.preggiapp.testproject.gui.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

public class BasePresenter<T extends MvpView> {

    protected T mvpView;

    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    public T getMvpView() {
        return mvpView;
    }

    public String getString(int stringId) {
        return getMvpView().getStringRes(stringId);
    }

    public void showToast(String message) {
        getMvpView().showToast(message);
    }

    public void startProgressDialog() {
        getMvpView().startProgressDialog();
    }

    public void stopProgressDialog() {
        getMvpView().stopProgressDialog();
    }

    public boolean isOnline() {
        return getMvpView().isOnline();
    }

    public Context getContext() {
        return ((Fragment)mvpView).getActivity();
    }

    public Resources getResources() {
        return ((Fragment)mvpView).getResources();
    }
}
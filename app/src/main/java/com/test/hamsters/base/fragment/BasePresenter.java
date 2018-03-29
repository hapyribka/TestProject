package com.test.hamsters.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.test.hamsters.network.NetworkConnection;

public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    protected T mvpView;
    protected NetworkConnection connection = new NetworkConnection();

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public String getString(int stringId) {
        if(getMvpView() != null) {
            return getMvpView().getStringRes(stringId);
        } else {
            return null;
        }
    }

    public void showToast(String message) {
        if(getMvpView() != null) {
            getMvpView().showToast(message);
        }
    }

    public boolean isOnline() {
        return mvpView.isOnline();
    }


    public Context getContext() {
        if(mvpView instanceof Fragment) {
            return ((Fragment)mvpView).getActivity();
        }
        return ((Fragment)mvpView).getActivity();
    }

    public void startActivity(Intent intent) {
        if(getContext() != null) {
            getContext().startActivity(intent);
        }
    }

    public void closeActivity() {
        if(getContext() != null) {
            ((Activity) getContext()).finish();
        }
    }

    public void startProgressDialog() {
        getMvpView().startProgressDialog();
    }

    public void stopProgressDialog() {
        getMvpView().stopProgressDialog();
    }
}
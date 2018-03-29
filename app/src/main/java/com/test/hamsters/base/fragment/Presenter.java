package com.test.hamsters.base.fragment;

public interface Presenter<T extends MvpView> {

    void attachView(T mvpView);

    void detachView();
}

package com.preggiapp.testproject.gui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseFragment extends Fragment {

    public abstract @LayoutRes int getLayoutResId();
    protected View commonView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View commonView = inflater.inflate(getLayoutResId(), container, false);
        return commonView;
    }

    public int getScreenWidth() {
        WindowManager w = getActivity().getWindowManager();
        Display d = w.getDefaultDisplay();
        return d.getWidth();
    }
}
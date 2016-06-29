package com.preggiapp.testproject.fragment;

import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseFragment extends Fragment {

    public View commonView;

    public abstract void update();

    public void hideKeyboard() {
        if(commonView != null) {
            hideKeyboard(commonView);
        }
    }

    public void hideKeyboard(View view) {
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

   @Override
    public void onPause() {
        hideKeyboard();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        hideKeyboard();
        super.onDestroy();
    }

    public int getScreenWidth() {
        WindowManager w = getActivity().getWindowManager();
        Display d = w.getDefaultDisplay();
        return d.getWidth();
    }
}
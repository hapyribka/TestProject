package com.preggiapp.testproject.gui.base;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.gui.activity.MainActivity;

public abstract class BaseConnectionFragment extends BaseFragment implements MvpView {

    public abstract BasePresenter getPresenter();

/*
    @Override
    public void onLoadFinished(String error) {
        stopProgressDialog();
        Log.i("BaseConntFragment", "onLoadFinished()  "+ error);
        if(error != null) {
            Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_LONG).show();
            return;
        }
        update();
    }
*/

    public void startProgressDialog() {
        if(commonView != null) {
            ProgressBar progress = (ProgressBar) commonView.findViewById(R.id.progress);
            if(progress != null) {
                progress.setVisibility(View.VISIBLE);
            }
        }
    }

    public void stopProgressDialog() {
        if(commonView != null) {
            ProgressBar progress = (ProgressBar) commonView.findViewById(R.id.progress);
            if(progress != null) {
                progress.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
    }

    @Override
    public String getStringRes(int stringId) {
        return getString(stringId);
    }

    public boolean isOnline() {
        return ((MainActivity) getActivity()).isOnline();
    }
}
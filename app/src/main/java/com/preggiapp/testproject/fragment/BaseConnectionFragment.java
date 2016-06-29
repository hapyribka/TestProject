package com.preggiapp.testproject.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.activity.MainActivity;
import com.preggiapp.testproject.network.callbacks.FragmentCallBack;
import com.preggiapp.testproject.network.command.BaseCommand;

public abstract class BaseConnectionFragment extends BaseFragment implements FragmentCallBack {

    public abstract void update();

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

    public void startConnection(BaseCommand command) {
        if (!isOnline()) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_network_error), Toast.LENGTH_LONG).show();
            return;
        }
        startProgressDialog();
        command.execute();
    }

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

    public boolean isOnline() {
        return ((MainActivity) getActivity()).isOnline();
    }
}
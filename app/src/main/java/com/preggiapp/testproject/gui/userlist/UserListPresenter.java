package com.preggiapp.testproject.gui.userlist;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.preggiapp.testproject.R;
import com.preggiapp.testproject.data.AppData;
import com.preggiapp.testproject.gui.base.BasePresenter;
import com.preggiapp.testproject.model.AppComponent;
import com.preggiapp.testproject.model.User;
import com.preggiapp.testproject.network.callbacks.ServiceCallback;
import com.preggiapp.testproject.network.response.UsersListResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

import static com.preggiapp.testproject.network.command.BaseCommand.API_VERSION;

public class UserListPresenter extends BasePresenter<UserListMvpView> {

    public void attachView(UserListMvpView mvpView) {
        super.attachView(mvpView);
        new GettingAppComponentTask().execute();
        getUserList();
    }

    public void getUserList() {
        if (!isOnline()) {
            Toast.makeText(getContext(), getString(R.string.no_network_error), Toast.LENGTH_LONG).show();
            return;
        }
        startProgressDialog();
        Call<UsersListResponse> resp = AppData.getInstance().getBaseCommand().service.getUsers(API_VERSION);
        resp.enqueue(new ServiceCallback<UsersListResponse>() {
            @Override
            public void onResponse(Response<UsersListResponse> response, String error) {
                if (error != null) {
                    showToast(error);
                } else {
                    UsersListResponse body = response.body();
                    getMvpView().updatePager((ArrayList<User>) body.getResult());
                }
                stopProgressDialog();
            }
        });
    }

    private List<AppComponent> getInstalledComponentList() throws PackageManager.NameNotFoundException {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ril = getContext().getPackageManager().queryIntentActivities(mainIntent, 0);
        List<AppComponent> componentList = new ArrayList<AppComponent>();
        componentList.add(getContactsAppComponent());
        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                Resources res = getContext().getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
                String name = ri.activityInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                }
                ri.loadIcon(getContext().getPackageManager());
                if(checkComponentName(ri.activityInfo.packageName)) {
                    componentList.add(new AppComponent(name, ri.loadIcon(getContext().getPackageManager())));
                }
            }
        }
        componentList.add(getOtherAppComponent());
        return componentList;
    }

    private boolean checkComponentName(String name) {
        Log.i("UserListFr", "pack name:  "+name);
        if(name.indexOf("facebook") >= 0
                || name.indexOf("twitter") >= 0
                || name.indexOf("instagram") >= 0
                || name.indexOf(".ok.") >= 0
                || name.indexOf("viber") >= 0
                || name.indexOf("mail") >= 0
                || name.indexOf("sms") >= 0 || name.indexOf("mms") >= 0
                || name.indexOf("whatsapp") >= 0
                || name.indexOf("vkontakte") >= 0) {
            return true;
        }
        return false;
    }

    private AppComponent getContactsAppComponent() {
        return new AppComponent(getString(R.string.address_book), getResources().getDrawable(R.drawable.address_icon));
    }

    private AppComponent getOtherAppComponent() {
        return new AppComponent(getString(R.string.other_methods), getResources().getDrawable(R.drawable.other_icon));
    }

    class GettingAppComponentTask extends AsyncTask<Void, ArrayList<AppComponent>, ArrayList<AppComponent>> {
        @Override
        protected ArrayList<AppComponent> doInBackground(Void... voids) {
            try {
                return (ArrayList<AppComponent>) getInstalledComponentList();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return new ArrayList<AppComponent>();
        }

        @Override
        public void onPostExecute(ArrayList<AppComponent> result) {
            super.onPostExecute(result);
            getMvpView().updateList(result);
        }
    }
}
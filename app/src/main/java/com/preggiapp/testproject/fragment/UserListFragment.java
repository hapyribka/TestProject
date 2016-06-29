package com.preggiapp.testproject.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.activity.MainActivity;
import com.preggiapp.testproject.adapter.AppComponentAdapter;
import com.preggiapp.testproject.adapter.UserPageAdapter;
import com.preggiapp.testproject.data.AppData;
import com.preggiapp.testproject.model.AppComponent;
import com.preggiapp.testproject.network.command.GetUserListCommand;
import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends BaseConnectionFragment {

    private ViewPager pager;
    private RecyclerView list;
    private UserPageAdapter adapter;
    private AppComponentAdapter componentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commonView = inflater.inflate(R.layout.userlist_fragment, container, false);
        pager = (ViewPager)commonView.findViewById(R.id.pager);
        list = (RecyclerView)commonView.findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        return commonView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).enableSearchButton();
        update();
        startConnection(new GetUserListCommand(this));
        getActivity().setTitle(getString(R.string.user_list));
    }

    @Override
    public void update() {
        Log.i("UserListFragment", "update()  "+AppData.getInstance().getUserList());
        if(AppData.getInstance().getUserList() != null
                && getActivity() != null
                && pager != null) {
            adapter = new UserPageAdapter(getChildFragmentManager(), AppData.getInstance().getUserList());
            pager.setAdapter(adapter);
            int width = (int) getResources().getDimension(R.dimen.image_width);
            int screenWidth = getScreenWidth();
            pager.setClipToPadding(false);
            pager.setPadding((screenWidth - width)/2,0,(screenWidth - width)/2,0);
            pager.setPageMargin(5);
            pager.setCurrentItem(0);
            ((MainActivity)getActivity()).enableSearchButton();
        }

        if(AppData.getInstance().getComponentList() != null) {
            componentAdapter = new AppComponentAdapter(AppData.getInstance().getComponentList());
            list.setAdapter(componentAdapter);
        } else {
            new GettingAppComponentTask().execute();
            startProgressDialog();
        }
    }

    private List<AppComponent> getInstalledComponentList() throws PackageManager.NameNotFoundException {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ril = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);
        List<AppComponent> componentList = new ArrayList<AppComponent>();
        componentList.add(getContactsAppComponent());
        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                Resources res = getActivity().getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
                String name = ri.activityInfo.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                }
                ri.loadIcon(getActivity().getPackageManager());
                if(checkComponentName(ri.activityInfo.packageName)) {
                    componentList.add(new AppComponent(name, ri.loadIcon(getActivity().getPackageManager())));
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

    class GettingAppComponentTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                AppData.getInstance().setComponentList((ArrayList<AppComponent>) getInstalledComponentList());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            stopProgressDialog();
            update();
        }
    }
}
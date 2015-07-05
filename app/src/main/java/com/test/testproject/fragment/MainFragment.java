package com.test.testproject.fragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.test.testproject.R;
import com.test.testproject.adapter.ListAdapter;
import com.test.testproject.db.CacheUtils;
import com.test.testproject.model.Order;
import com.test.testproject.model.Orders;
import com.test.testproject.network.ApiContent;
import com.test.testproject.network.ApiService;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private ListAdapter adapter;
    private ListView list;
    private View rootView;
    public static final long REQUEST_DELTA_TIME = 60000;
    private static long lastRequestTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        if(adapter == null) {
            (new ConnectionTask()).execute(ApiContent.createApiContentToGetOrderList(getActivity().getApplicationContext(), "0"));
        }
        return rootView;
    }

    class ConnectionTask extends AsyncTask<ApiContent, ApiContent, ApiContent> {

        ArrayList<Orders> orderList;
        @Override
        protected void onPreExecute() {
            startProgressDialog();
            orderList = loadCachedData();
        }

        @Override
        protected ApiContent doInBackground(ApiContent... params) {
            if(orderList != null && System.currentTimeMillis() - lastRequestTime <= REQUEST_DELTA_TIME) {
                Log.e("ConnectionTask", "Used cached data!!!");
                params[0].setStatus(ApiContent.SUCCESS_STATUS);
                params[0].setResponseObject(orderList);
            } else {
                (new ApiService()).loadOrders(params[0]);
                lastRequestTime = System.currentTimeMillis();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(ApiContent content) {
            if (content.getStatus().equals(ApiContent.ERROR_STATUS)) {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error)+"! "+ content.getError() != null ? content.getError() : getString(R.string.some_error), Toast.LENGTH_LONG).show();
            } else {
                List<Orders> orders = (List<Orders>)content.getResponseObject();
                saveCachedData(orders);
                adapter = new ListAdapter(orders);
                list.setAdapter(adapter);
            }
            stopProgressDialog();
        }
    }

    public void saveCachedData(List<Orders> orders) {
        ArrayList<Order> order = new ArrayList<Order>();
        for(Orders ord: orders) {
            ord.prepareToSave();
            order.addAll(ord.getOrderList());
        }
        CacheUtils.saveOrderAll(getActivity(), order, true);
        CacheUtils.saveOrders(getActivity(), orders, true);
    }

    public ArrayList<Orders> loadCachedData() {
        ArrayList<Orders> orderList = CacheUtils.getAllOrders(getActivity());
        for(Orders ord: orderList) {
            ord.load(getActivity());
        }
        return orderList;
    }

    public void startProgressDialog() {
        if(rootView != null)
            (rootView.findViewById(R.id.progress)).setVisibility(View.VISIBLE);
    }

    public void stopProgressDialog() {
        if(rootView != null)
            (rootView.findViewById(R.id.progress)).setVisibility(View.GONE);
    }

}

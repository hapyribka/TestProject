package com.test.testproject.network;

import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.testproject.model.Orders;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApiService {

    public static final String TAG ="ApiService";
    public static final int TIMEOUT =15000;
    public static final String JSON_FIELD_ERROR = "error";
    public static final String JSON_FIELD_RULT = "result";
    public static final String JSON_FIELD_LAST_ORDER_ID = "last_order_id";
    public static final String JSON_FIELD_TOKEN = "token";


    public void loadOrders(ApiContent content) {
        Log.e(TAG, "Request parameters:  " + content.getUrl());
        String response = httpGet(content.getUrl(), content.getParams());
        Log.e(TAG, "Response:  " + response);
        if(isResponseHasError(response, content))
            return;
        content.setResponse(response);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ArrayList<Orders> orderList = new ArrayList<Orders>();
        try {
            orderList.addAll(Arrays.asList(gson.fromJson((new JSONObject(content.getResponse())).getString(JSON_FIELD_RULT), Orders[].class)));
            content.setResponseObject(orderList);
        } catch (JSONException e) {
            Log.e(TAG, "Error parse responce:  " + e);
        }
        Collections.sort(orderList, new Comparator<Orders>() {
            @Override
            public int compare(Orders s1, Orders s2) {
                try {
                    return (DateFormat.getInstance().parse(s1.getCreated())).compareTo(DateFormat.getInstance().parse((s2.getCreated())));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });
        content.setResponseObject(orderList);
    }

    public boolean isResponseHasError(String data, ApiContent content) {
        if(data == null) {
            content.setStatus(ApiContent.ERROR_STATUS);
            return true;
        }

        try {
            JSONObject obj = new JSONObject(data);
            if(obj.has(JSON_FIELD_ERROR)) {
                content.setStatus(ApiContent.ERROR_STATUS);
                content.setError(obj.getString(JSON_FIELD_ERROR));
                return true;
            }
            content.setStatus(ApiContent.SUCCESS_STATUS);
            return false;
        } catch (JSONException e) {
            Log.e(TAG, "Error parse isResponseHasError: "+e);
            content.setStatus(ApiContent.ERROR_STATUS);
            return true;
        }
    }

//    public String httpGet(String ur, HashMap<String, String> paramsMap) {
//        return "{\"result\":[\t{\"id\":\"152945\",\t\"city_id\":\"54\",\t\"show_phone\":\"1\",\t\"notes\":\"\",\t\"car\":\t{\"year\":\"2000\",\t\"wheel\":\"1\",\"body\":\"BJ5W\",\"engine\":\"\",\"vin\":\"\",\t\"manufacturer_id\":\"97\",\t\"model_id\":\"10386\"},\"created\":\"2015-06-23 22:26:37\",\"orderlist\":[{\"id\":\"206221\",\"part_name\":\"\\u0431\\u0430\\u043c\\u043f\\u0435\\u0440 \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u0438\\u0439\",\"type\":\t[\"old\"]\t}]},{\"id\":\"152946\",\"city_id\":\"54\",\"show_phone\":\"1\",\"notes\":\"\\u0441 \\u0442\\u0443\\u043c\\u0430\\u043d\\u043a\\u0430\\u043c\\u0438\",\"car\":{\"year\":\"2006\",\"wheel\":\"1\",\"body\":\"CBA-ACU30W\",\"engine\":\"\",\"vin\":\"\",\"manufacturer_id\":\"86\",\"model_id\":\"10111\"},\"created\":\"2015-06-25 08:57:52\",\"orderlist\":[{\"id\":\"206222\",\"part_name\":\"\\u0431\\u0430\\u043c\\u043f\\u0435\\u0440 \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u0438\\u0439 \",\"type\":[\"old\"]}]}]}";
//    }

    public String httpGet(String ur, HashMap<String, String> paramsMap) {
        Log.d(TAG, "httpGet. Start.  "+paramsMap);
        HttpURLConnection connection = null;
        try {
            Uri.Builder builtUri = Uri.parse(ur).buildUpon();
            Iterator<Map.Entry<String, String>> iter = paramsMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                if (entry != null) {
                    builtUri.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            URL url = new URL(builtUri.build().toString());
            StringBuffer stb = new StringBuffer();
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestProperty("Accept", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while((str = reader.readLine()) != null) {
                stb.append(str);
                stb.append("\n");
            }
            reader.close();
            connection.disconnect();
            return stb.toString();
        } catch (SocketException ex) {
            Log.e(TAG, "Error getting data from server(httpGet). SocketException:  "+ex);
        } catch (IOException ex) {
            Log.e(TAG, "Error getting data from server(httpGet). IOException:  "+ex);
        }
        return null;
    }
}
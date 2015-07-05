package com.test.testproject.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.test.testproject.db.CacheUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DatabaseTable
public class Orders {

    @DatabaseField(id = true, useGetSet = true)
    private String id;

    @DatabaseField(useGetSet = true)
    private String created;

    @DatabaseField(useGetSet = true)
    private String city_id;

    @DatabaseField(useGetSet = true)
    private String show_phone;

    @DatabaseField(useGetSet = true)
    private String notes;

    @DatabaseField(useGetSet = true)
    private String car_string;

    @DatabaseField(useGetSet = true)
    private String orderlist_string;

    private Car car;
    private List<Order> orderlist;

    public Orders() {
    }

    public void prepareToSave() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        car_string = gson.toJson(car);
        ArrayList<String> orderIds = new ArrayList<String>();
        for(Order ord : orderlist) {
            ord.prepareToSave();
            orderIds.add(ord.getId());
        }
        orderlist_string = gson.toJson(orderIds);
    }

    public void load(Context context) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        car = gson.fromJson(car_string, Car.class);
        ArrayList<String> orderIds = new ArrayList<>();
//        Log.e("", "car_string - "+car_string);
        orderIds.addAll(Arrays.asList(gson.fromJson(orderlist_string, String[].class)));
        if(orderIds != null && orderIds.size() > 0) {
            orderlist = new ArrayList<Order>();
            for(String id : orderIds) {
                Order ord = CacheUtils.getOrderById(context, Integer.parseInt(id));
                ord.load();
                orderlist.add(ord);
            }
        }
    }

    public String getCar_string() {
        return car_string;
    }

    public void setCar_string(String car_string) {
        this.car_string = car_string;
    }

    public String getOrderlist_string() {
        return orderlist_string;
    }

    public void setOrderlist_string(String orderlist_string) {
        this.orderlist_string = orderlist_string;
    }

    public List<Order> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<Order> orderlist) {
        this.orderlist = orderlist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getShow_phone() {
        return show_phone;
    }

    public void setShow_phone(String show_phone) {
        this.show_phone = show_phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Order> getOrderList() {
        return orderlist;
    }

    public void setOrderList(List<Order> orderlist) {
        this.orderlist = orderlist;
    }
}
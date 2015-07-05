package com.test.testproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.test.testproject.R;
import com.test.testproject.model.Car;
import com.test.testproject.model.Order;
import com.test.testproject.model.Orders;
import com.test.testproject.model.Request;
import java.sql.SQLException;

class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<Order, Integer> orderDao = null;
    private RuntimeExceptionDao<Car, Integer> carDao = null;
    private RuntimeExceptionDao<Orders, Integer> ordersDao = null;
    private RuntimeExceptionDao<Request, Integer> requestDao = null;

    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Order.class);
            TableUtils.createTable(connectionSource, Orders.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getSimpleName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion < newVersion){
            try {
                TableUtils.dropTable(connectionSource, Order.class, true);
                TableUtils.dropTable(connectionSource, Orders.class, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                Log.e(DatabaseHelper.class.getSimpleName(), "Can't drop databases", e);
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void clear(Class<T> clazz) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onClearTable " + clazz.getName());
            TableUtils.clearTable(connectionSource, clazz);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getSimpleName(), "Can't clear table " + clazz.getName(), e);
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<Request, Integer> getRequestDao() {
        if (requestDao == null) {
            requestDao = getRuntimeExceptionDao(Request.class);
        }
        return requestDao;
    }

    public RuntimeExceptionDao<Order, Integer> getOrderDao() {
        if (orderDao == null) {
            orderDao = getRuntimeExceptionDao(Order.class);
        }
        return orderDao;
    }

    public RuntimeExceptionDao<Car, Integer> getCarDao() {
        if (carDao == null) {
            carDao = getRuntimeExceptionDao(Car.class);
        }
        return carDao;
    }

    public RuntimeExceptionDao<Orders, Integer> getOrdersDao() {
        if (ordersDao == null) {
            ordersDao = getRuntimeExceptionDao(Orders.class);
        }
        return ordersDao;
    }

    @Override
    public void close() {
        super.close();
        ordersDao = null;
        carDao = null;
        orderDao = null;
        requestDao = null;
    }
}

package com.test.testproject.db;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.test.testproject.model.Car;
import com.test.testproject.model.Order;
import com.test.testproject.model.Orders;
import com.test.testproject.model.Request;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CacheUtils {

    private static DatabaseHelper instance = null;

    public static void saveOrderAll(Context context, final List<Order> orderList, boolean clearOldData) {
        if (context == null || orderList == null || orderList.isEmpty()) return;
        if (clearOldData) {
            getInstance(context).clear(Order.class);
        }
        final RuntimeExceptionDao<Order, Integer> orderDao = getInstance(context).getOrderDao();
        orderDao.callBatchTasks(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Order i : orderList) {
                    orderDao.createOrUpdate(i);
                }
                return null;
            }
        });
    }

    public static void saveCars(Context context, final List<Car> carList, boolean clearOldData) {
        if (context == null || carList == null || carList.isEmpty()) return;
        if (clearOldData) {
            getInstance(context).clear(Order.class);
        }
        final RuntimeExceptionDao<Car, Integer> carDao = getInstance(context).getCarDao();
        carDao.callBatchTasks(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Car i : carList) {
                    carDao.createOrUpdate(i);
                }
                return null;
            }
        });
    }

    public static void saveOrders(Context context, final List<Orders> ordersList, boolean clearOldData) {
        if (context == null || ordersList == null || ordersList.isEmpty()) return;
        if (clearOldData) {
            getInstance(context).clear(Orders.class);
        }
        final RuntimeExceptionDao<Orders, Integer> ordersDao = getInstance(context).getOrdersDao();
        ordersDao.callBatchTasks(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Orders i : ordersList) {
                    ordersDao.createOrUpdate(i);
                }
                return null;
            }
        });
    }

    public static void saveRequest(Context context, final List<Request> requestList, boolean clearOldData) {
        if (context == null || requestList == null || requestList.isEmpty()) return;
        if (clearOldData) {
            getInstance(context).clear(Request.class);
        }
        final RuntimeExceptionDao<Request, Integer> requestDao = getInstance(context).getRequestDao();
        requestDao.callBatchTasks(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Request i : requestList) {
                    requestDao.createOrUpdate(i);
                }
                return null;
            }
        });
    }

    public static ArrayList<Order> getAllOrder(Context context) {
        if (context == null) return new ArrayList<Order>();
        try {
            QueryBuilder<Order, Integer> orderOb = getInstance(context).getOrderDao().queryBuilder();
            return (ArrayList<Order>) getInstance(context).getOrderDao().query(orderOb.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Order>();
        }
    }

    public static ArrayList<Car> getAllCars(Context context) {
        if (context == null) return new ArrayList<Car>();
        try {
            QueryBuilder<Car, Integer> carOb = getInstance(context).getCarDao().queryBuilder();
            return (ArrayList<Car>) getInstance(context).getCarDao().query(carOb.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Car>();
        }
    }

    public static ArrayList<Orders> getAllOrders(Context context) {
        if (context == null) return new ArrayList<Orders>();
        try {
            QueryBuilder<Orders, Integer> ordersOb = getInstance(context).getOrdersDao().queryBuilder();
            return (ArrayList<Orders>) getInstance(context).getOrdersDao().query(ordersOb.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Orders>();
        }
    }

    public static ArrayList<Request> getAllRequest(Context context) {
        if (context == null) return new ArrayList<Request>();
        try {
            QueryBuilder<Request, Integer> requestOb = getInstance(context).getRequestDao().queryBuilder();
            return (ArrayList<Request>) getInstance(context).getRequestDao().query(requestOb.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Request>();
        }
    }

    public static Order getOrderById(Context context, int id) {
        if (context == null) return new Order();
        return getInstance(context).getOrderDao().queryForId(id);
    }

    public static Car getCarById(Context context, int id) {
        if (context == null) return new Car();
        return getInstance(context).getCarDao().queryForId(id);
    }

    public static Orders getOrdersById(Context context, int id) {
        if (context == null) return new Orders();
        return getInstance(context).getOrdersDao().queryForId(id);
    }

    public static List<Request> getRequestByUrl(Context context, String url) {
        if (context == null) return new ArrayList<Request>();
        return getInstance(context).getRequestDao().queryForEq("url", url);
    }

    private static DatabaseHelper getInstance(Context context) {
        if (context != null && instance == null) {
            instance = OpenHelperManager.getHelper(context.getApplicationContext(), DatabaseHelper.class);
        }
        return instance;
    }

    public void finish() {
        if (instance != null) {
            instance.close();
            instance = null;
            OpenHelperManager.releaseHelper();
        }
    }
}

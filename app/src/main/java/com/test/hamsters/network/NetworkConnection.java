package com.test.hamsters.network;

import com.test.hamsters.models.Hamster;
import java.util.ArrayList;
import rx.Observable;

public class NetworkConnection extends BaseConnection {

    public NetworkConnection() {
        super();
    }

    public Observable<ArrayList<Hamster>> getHamsters() {
        return setThreads(apiInterface.getHamsters());
    }
}
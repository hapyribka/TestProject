package com.test.hamsters.gui.hamsterList;

import android.content.Intent;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BasePresenter;
import com.test.hamsters.event.SearchEvent;
import com.test.hamsters.gui.activity.HamsterDetailsActivity;
import com.test.hamsters.gui.hamsterDetails.HamsterDetailsFragment;
import com.test.hamsters.models.Hamster;
import com.test.hamsters.network.AppObserver;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.parceler.Parcels;
import java.util.ArrayList;

public class HamsterListPresenter extends BasePresenter<HamsterListMvpView> {

    private ArrayList<Hamster> hamstersAll;
    private ArrayList<Hamster> hamsters;
    private String searchString;

    public void attachView(HamsterListMvpView view) {
        super.attachView(view);
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if(hamstersAll != null) {
            updateSearchList();
        } else {
            getHamster();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    public void getHamster() {
        if(!isOnline()) {
            showToast(getString(R.string.no_internet_connection));
            return;
        }
        startProgressDialog();

        connection.getHamsters().subscribe(new AppObserver<ArrayList<Hamster>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onErrorMessage(String message) {
                stopProgressDialog();
                showToast(message);
            }

            @Override
            public void onNext(ArrayList<Hamster> response) {
                hamstersAll = response;
                stopProgressDialog();
                updateSearchList();
            }
        });
    }

    public void updateSearchList() {
        if(hamstersAll != null) {
            hamsters = new ArrayList<>();
            if(searchString != null && searchString.length() >= 3) {
                for(Hamster hamster : hamstersAll) {
                    if(hamster.getTitle().toLowerCase().indexOf(searchString.toLowerCase()) >= 0) {
                        hamsters.add(hamster);
                    }
                }
            } else {
                hamsters = hamstersAll;
            }
            getMvpView().showData(hamsters);
        }
    }

    private void setSearchSring(String searchString) {
        this.searchString = searchString;
    }

    @Subscribe
    public void onEvent(SearchEvent event) {
        setSearchSring(event.getSearchText());
        updateSearchList();
    }

    public void onItemSelected(Hamster item) {
        Intent intent = new Intent(getContext(), HamsterDetailsActivity.class);
        intent.putExtra(HamsterDetailsFragment.HAMSTER_KEY, Parcels.wrap(item));
        startActivity(intent);
    }
}
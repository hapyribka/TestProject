package com.test.hamsters.gui.hamsterList;

import com.test.hamsters.base.fragment.MvpView;
import com.test.hamsters.models.Hamster;
import java.util.ArrayList;

public interface HamsterListMvpView extends MvpView {
    void showData(ArrayList<Hamster> hamsters);
}
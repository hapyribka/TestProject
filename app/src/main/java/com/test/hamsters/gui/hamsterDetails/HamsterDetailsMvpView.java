package com.test.hamsters.gui.hamsterDetails;

import com.test.hamsters.base.fragment.MvpView;
import com.test.hamsters.models.Hamster;
import java.util.ArrayList;

public interface HamsterDetailsMvpView extends MvpView {
    void showData(Hamster hamster);
}
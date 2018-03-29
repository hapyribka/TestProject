package  com.test.hamsters.gui.photo;

import com.test.hamsters.base.fragment.MvpView;

public interface PhotoMvpView extends MvpView {
    void showImage(String url);
}
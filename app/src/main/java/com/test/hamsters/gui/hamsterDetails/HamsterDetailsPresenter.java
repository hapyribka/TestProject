package com.test.hamsters.gui.hamsterDetails;

import android.content.Intent;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BasePresenter;
import com.test.hamsters.event.ShareDetailsEvent;
import com.test.hamsters.gui.activity.PhotoActivity;
import com.test.hamsters.gui.photo.PhotoFragment;
import com.test.hamsters.models.Hamster;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HamsterDetailsPresenter extends BasePresenter<HamsterDetailsMvpView> {

    private Hamster hamster;

    public void attachView(HamsterDetailsMvpView view, Hamster hamster) {
        super.attachView(view);
        this.hamster = hamster;
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getMvpView().showData(hamster);
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    public void onImagePressed() {
        if(hamster != null && hamster.getImage() != null) {
            Intent intent = new Intent(getContext(), PhotoActivity.class);
            intent.putExtra(PhotoFragment.IMAGE_URL, hamster.getImage());
            intent.putExtra(PhotoActivity.TITLE_KEY, hamster.getTitle());
            startActivity(intent);
        }
    }

    @Subscribe
    public void onEvent(ShareDetailsEvent event) {
        shareDetails();
    }

    private void shareDetails() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        String text = String.format(getString(R.string.text_to_share), hamster.getTitle(), hamster.getDescription(), hamster.getImage());
        intent.putExtra(Intent.EXTRA_STREAM, text);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        getContext().startActivity(Intent.createChooser(intent, getString(R.string.share_image_by)));
    }
}
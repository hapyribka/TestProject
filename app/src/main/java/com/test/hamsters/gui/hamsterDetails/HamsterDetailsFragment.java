package com.test.hamsters.gui.hamsterDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BaseConnectionFragment;
import com.test.hamsters.base.fragment.BasePresenter;
import com.test.hamsters.models.Hamster;
import org.parceler.Parcels;
import butterknife.BindView;
import butterknife.OnClick;

public class HamsterDetailsFragment extends BaseConnectionFragment implements HamsterDetailsMvpView {

    public static final String HAMSTER_KEY = "hamster_key";

    public static Fragment createInstance(Hamster hamster) {
        HamsterDetailsFragment fragment = new HamsterDetailsFragment();
        Bundle arg = new Bundle();
        arg.putParcelable(HAMSTER_KEY, Parcels.wrap(hamster));
        fragment.setArguments(arg);
        return fragment;
    }

    private HamsterDetailsPresenter presenter = new HamsterDetailsPresenter();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hamster_details;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.image)
    ImageView image;

    @OnClick(R.id.image) void onImagePressed() {
        presenter.onImagePressed();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arg = getArguments();
        Hamster hamster = null;
        if(arg != null) {
            hamster = Parcels.unwrap(arg.getParcelable(HAMSTER_KEY));
        }
        presenter.attachView(this, hamster);
    }

    @Override
    public void showData(Hamster hamster) {
        if(hamster != null) {
            description.setText(hamster.getDescription());
            Glide.with(getActivity())
                    .load(hamster.getImage())
                    .centerCrop()
                    .into(image);
        }
    }
}
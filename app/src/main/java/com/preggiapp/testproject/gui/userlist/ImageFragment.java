package com.preggiapp.testproject.gui.userlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.joooonho.SelectableRoundedImageView;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.model.User;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ImageFragment extends Fragment {

    private User user;

    public static ImageFragment createImageFragment(User user) {
        ImageFragment fragment = new ImageFragment();
        fragment.user = user;
        return fragment;
    }

    public ImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View commonView = inflater.inflate(R.layout.image_item, container, false);
        int size = (int) getResources().getDimension(R.dimen.image_width);
        if(user == null) {
            return inflater.inflate(R.layout.image_item_empty, container, false);
        } else {
            SelectableRoundedImageView photo = (SelectableRoundedImageView) commonView .findViewById(R.id.photo);
            TextView name = (TextView) commonView .findViewById(R.id.name);
            TextView code = (TextView) commonView .findViewById(R.id.code);
            TextView address = (TextView) commonView .findViewById(R.id.address);
            if(user.getLogin() != null) {
                name.setText(user.getLogin());
            }
            if(user.getLocality() != null) {
                address.setText(user.getLocality());
            }
            if(user.getPregnancy_settings() != null) {
                code.setText(user.getPregnancy_settings().getPregnancy()+"");
            }
            Picasso.with(getActivity()).load(user.getAvatar().getFull_url()).resize(size, size).centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(photo);
        }
        return commonView;
    }
}
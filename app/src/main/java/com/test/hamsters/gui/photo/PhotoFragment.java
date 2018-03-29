package  com.test.hamsters.gui.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BaseConnectionFragment;
import com.test.hamsters.base.fragment.BasePresenter;
import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoFragment extends BaseConnectionFragment implements PhotoMvpView {

    public static final String IMAGE_URL = "image_url";

    public static PhotoFragment createInstance(String photo) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, photo);
        fragment.setArguments(args);
        return fragment;
    }

    private PhotoPresenter presenter = new PhotoPresenter();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_photo;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @BindView(R.id.image)
    ImageView image;

    private PhotoViewAttacher attacher;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        String photoUrl = null;
        if(args != null) {
            photoUrl = getArguments().getString(IMAGE_URL);
        }
        presenter.attachView(this, photoUrl);
    }

    @Override
    public void showImage(String url) {
        if(url != null) {
            Glide.with(this)
                    .load(url)
                    .listener(presenter)
                    .into(image);
            attacher = new PhotoViewAttacher(image);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
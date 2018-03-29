package  com.test.hamsters.gui.photo;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.hamsters.R;
import com.test.hamsters.base.fragment.BasePresenter;
import com.test.hamsters.event.DownloadImageEvent;
import com.test.hamsters.event.ShareImageEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.File;
import static android.content.Context.DOWNLOAD_SERVICE;

public class PhotoPresenter extends BasePresenter<PhotoMvpView> implements RequestListener<String, GlideDrawable> {

    public static final int MY_PERMISSIONS_REQUEST_FINE = 2;
    private DownloadManager downloadManager;
    private String filename;
    private String photoUrl;

    public void attachView(PhotoFragment fragment, String photoUrl) {
        super.attachView(fragment);
        this.photoUrl = photoUrl;
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        startProgressDialog();
        getMvpView().showImage(photoUrl);
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        stopProgressDialog();
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        stopProgressDialog();
        return false;
    }

    private long downloadData (String url) {
        Uri uri = Uri.parse(url);
        startProgressDialog();
        long downloadReference;
        downloadManager = (DownloadManager)getContext().getSystemService(DOWNLOAD_SERVICE);
        if(downloadManager == null) {
            showToast(getString(R.string.no_downloadmanager_message));
            return 0;
        }
        DownloadManager.Request request = new DownloadManager.Request(uri);
        filename = getFileName(url);
        request.setTitle(filename);
        request.setDescription(filename);
        request.setDestinationInExternalPublicDir("/Hamsters", filename);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadReference = downloadManager.enqueue(request);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getContext().registerReceiver(downloadReceiver, filter);
        File file = new File(Environment.getExternalStorageDirectory()+"/Hamsters");
        if(!file.exists()) {
            file.mkdir();
        }
        return downloadReference;
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopProgressDialog();
            if((new File(Environment.getExternalStorageDirectory()+"/Hamsters", filename)).exists()) {
                showToast(getString(R.string.finish_download_image));
            } else {
                showToast(getString(R.string.error_download_image));
            }
        }
    };

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadData(photoUrl);
                }
                return;
            }
        }
    }

    @Subscribe
    public void onEvent(DownloadImageEvent event) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(((Fragment)getMvpView()).getActivity(),
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    MY_PERMISSIONS_REQUEST_FINE);
            return;
        } else {
            downloadData(photoUrl);
        }
    }

    @Subscribe
    public void onEvent(ShareImageEvent event) {
        shareImage(photoUrl);
    }

    private void shareImage(String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, url);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        getContext().startActivity(Intent.createChooser(intent, getString(R.string.share_image_by)));
    }

    private String getFileName(String str) {
        if(str == null)
            return "";
        int lastSlash = str.lastIndexOf('/');
        String fileName = "file.bin";
        if(lastSlash >= 0) {
            fileName = str.substring(lastSlash + 1);
        }
        if(fileName.equals("")) {
            fileName = "file.bin";
        }
        return fileName;
    }
}
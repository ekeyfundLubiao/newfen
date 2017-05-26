package com.m086.ad.mobi.view.photo;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

//
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.base.BaseDialog;
import com.m086.ad.mobi.view.dynamics.adapter.SendPicAdapter;
import com.m086.ad.mobi.view.photo.local.ConfigHelper;

/**
 * Created by Administrator on 2017/2/17.
 */
public class PhotoDialog extends BaseDialog {
    public static int SmallSize = 1;
    public static int BigSize = 2;
    private LinearLayout mCamaraButton;
    private LinearLayout mAlbumButton;
    private Button mCancleButton;
    private PhotoListener mListener;
    private int maxSelectSize = 9;
    private SendPicAdapter mSendPicAdapter;

    public void setMaxSelectSize(int maxSelectSize) {
        this.maxSelectSize = maxSelectSize;
    }

    private int ImageSize = 0;


    public PhotoDialog(BaseActivity activity,int size) {
        super(activity);
        mActivity = activity;
        setContentView(R.layout.pop_select_photo);
        initBottomDialog();
        ImageSize=size;
    }

    public PhotoDialog(BaseActivity activity, SendPicAdapter sendPicAdapter, int size) {
        super(activity);
        mActivity = activity;
        mSendPicAdapter = sendPicAdapter;
        setContentView(R.layout.pop_select_photo);
        initBottomDialog();
        ImageSize=size;
    }

    @Override
    public void initView() {
        super.initView();
        mCamaraButton = (LinearLayout) findViewById(R.id.llcamera);
        mAlbumButton = (LinearLayout) findViewById(R.id.llPhote);
        mCancleButton = (Button) findViewById(R.id.btn_cancel);
    }

    @Override
    public void setData() {
        super.setData();
        setLayoutBg(R.color.white);

    }

    @Override
    public void setListener() {
        super.setListener();
        mCamaraButton.setOnClickListener(mOnClickListener);
        mAlbumButton.setOnClickListener(mOnClickListener);
        mCancleButton.setOnClickListener(mOnClickListener);
    }

    public void setPhotoListener(PhotoListener l) {
        mListener = l;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llcamera:
                    GalleryPick.getInstance().setGalleryConfig(ConfigHelper.getInstance().getPhotoConfig(iHandlerCallBack)).open(mActivity);
                    break;
                case R.id.llPhote:

                    if (ImageSize == SmallSize) {
                        maxSelectSize = 1;
                    } else if (ImageSize == BigSize) {
                        if (mSendPicAdapter != null) {
                            int size = mSendPicAdapter.getItemCount();
                            if (size < 10) {
                                maxSelectSize = 9 - mSendPicAdapter.getItemCount() + 1;
                            }
                        }
                    }
                    GalleryPick.getInstance().setGalleryConfig(ConfigHelper.getInstance().getLocalConfig(iHandlerCallBack, maxSelectSize)).open(mActivity);
                    break;
                case R.id.btn_cancel:
                    onBack();
                    break;
            }
        }
    };

    private IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
            //开启

        }

        @Override
        public void onSuccess(List<String> photoList) {
            dismiss();
            if (null != mListener) {
                mListener.onSuccess(photoList);
            }
        }

        @Override
        public void onCancel() {
            dismiss();
            //取消
            if (null != mListener) {
                mListener.onCancel();
            }
        }

        @Override
        public void onFinish() {
            dismiss();
            //结束
            if (null != mListener) {
                mListener.onFinish();
            }
        }

        @Override
        public void onError() {
            dismiss();
            //出错
            if (null != mListener) {
                mListener.onError();
            }
        }
    };

    public static interface PhotoListener {
        public void onSuccess(List<String> photoList);

        public void onCancel();

        public void onError();

        public void onFinish();
    }
}

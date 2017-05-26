package com.m086.ad.mobi.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.io.File;
import java.io.IOException;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.tool.AppTools;

/**
 * Created by lubiao on 2016/10/30.
 */
public class PopSelectPicture extends PopupWindow implements View.OnClickListener{
    public final static int IMAGE_OPEN = 1; // 打开图片标记
    public final static int IMAGE_OPEN_2 = 2;// 相册

    public  static File mPhotoFile = null;
    public  static String mPhotoPath;

    private Button btnCommit;
    private Button btnCancle;
    private ImageView ivPhoto;
    private ImageView ivAlbum;
    private View mView;
    Activity mContext;

    public PopSelectPicture(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        mContext = context;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mView = mInflater.inflate(R.layout.pop_select_photo, null);
        btnCommit = (Button) mView.findViewById(R.id.btn_commit);
        btnCancle = (Button) mView.findViewById(R.id.btn_cancel);
        ivAlbum = (ImageView) mView.findViewById(R.id.iv_album);
        ivPhoto = (ImageView) mView.findViewById(R.id.iv_photo);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnCommit.setOnClickListener(itemsOnClick);
        ivAlbum.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        //设置SelectPicPopupWindow的View
        this.setContentView(mView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        backgroundAlpha(0.5f);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        this.setOnDismissListener(new poponDismissListener());
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_album:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mContext.startActivityForResult(intent, IMAGE_OPEN);
               dismiss();
                break;
            case R.id.iv_photo:
                Intent intentp = new Intent("android.media.action.IMAGE_CAPTURE");
                mPhotoPath = Environment.getExternalStorageDirectory() + "/" + AppTools.getPhotoFileName();
                mPhotoFile = new File(mPhotoPath);
                if (!mPhotoFile.exists()) {
                    try {
                        mPhotoFile.createNewFile();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
                intentp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                mContext.startActivityForResult(intentp, IMAGE_OPEN_2);
                break;
            default:
                break;
        }
    }


    class poponDismissListener implements OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }
}

package moni.anyou.com.view.view.dynamics;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.dynamics.adapter.SendPicAdapter;
import moni.anyou.com.view.widget.dialog.PopSelectPicture;

import static moni.anyou.com.view.widget.dialog.PopSelectPicture.IMAGE_OPEN_2;

public class SendDynamicActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rcPic;
    private SendPicAdapter mySentPicAdapter;
    ArrayList<SentPicBean> list=new ArrayList<SentPicBean>();
    PopSelectPicture mPopSelectPicture;

    private String pathImage; // 选择图片路径
    private Bitmap bmp; // 导入临时图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("");
        tvRight.setText("发送");
        tvRight.setVisibility(View.VISIBLE);
        rcPic = (RecyclerView) findViewById(R.id.rc_pic);
        mPopSelectPicture = new PopSelectPicture(mBaseActivity, this);
    }

    @Override
    public void setAction() {
        tvRight.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void setData() {
        super.setData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcPic.setLayoutManager(linearLayoutManager);
        list.add(new SentPicBean());
        mySentPicAdapter = new SendPicAdapter(this, list);
        rcPic.setAdapter(mySentPicAdapter);
        mySentPicAdapter.setmOnItemClickListener(new SendPicAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, SentPicBean data) {
                if ("".equals(data.filePathName)) {
                    mPopSelectPicture.showAtLocation(mBaseActivity.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
                    ToastTools.showShort(mContext,"添加");
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.right_tv:
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 打开图片
        if (resultCode == RESULT_OK && requestCode == PopSelectPicture.IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils
                    .isEmpty(uri.getAuthority())) {
                // 查询选择图片
                Cursor cursor = getContentResolver().query(uri, new String[] { MediaStore.Images.Media.DATA }, null,
                        null, null);
                // 返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                // 光标移动至开头 获取图片路径
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        } else if (requestCode == IMAGE_OPEN_2) {
            pathImage = PopSelectPicture.mPhotoPath;
        } // end if 打开图片
    }

    // 刷新图片
    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(pathImage)) {


//            picMap.put("picOBj", new Documents(pathImage, "00000000-0000-0000-0000-000000000000", true, pathImage));
//            SentPicBean pic = new SentPicBean(pathImage,null);
//            list.add(picMap);

            mySentPicAdapter.notifyDataSetChanged();
            // 刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }

    }

}

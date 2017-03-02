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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.bean.request.ReqSentDynamicsBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.KeyBoardTools;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.dynamics.adapter.SendPicAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.dialog.PopSelectPicture;
import moni.anyou.com.view.widget.pikerview.Utils.TextUtil;

import static moni.anyou.com.view.widget.dialog.PopSelectPicture.IMAGE_OPEN_2;

public class SendDynamicActivity extends BaseActivity implements View.OnClickListener {
    private NetProgressWindowDialog window;
    private RecyclerView rcPic;
    private EditText etContentDynamic;
    private SendPicAdapter mySentPicAdapter;
    ArrayList<SentPicBean> list = new ArrayList<SentPicBean>();
    PopSelectPicture mPopSelectPicture;

    private String pathImage; // 选择图片路径
    private Bitmap bmp; // 导入临时图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        init();
        KeyBoardTools.closeKeybord(etContentDynamic, mContext);
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        window = new NetProgressWindowDialog(mContext);
        tvTitle.setText("");
        tvRight.setText("发送");
        tvRight.setVisibility(View.VISIBLE);
        etContentDynamic = (EditText) findViewById(R.id.et_content_dynamic);
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
            public void onItemClick(View view, SentPicBean data, int position) {
                if (position == list.size() - 1) {
                    mPopSelectPicture.showAtLocation(mBaseActivity.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
                    ToastTools.showShort(mContext, "添加:" + position);
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
                if (TextUtils.isEmpty(etContentDynamic.getText())) {
                    ToastTools.showShort(mContext, "不能为空");
                    return;
                }
                mPic = "5.png,6.png,7.png";

                posySentDynamics();
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
                Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null,
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
            SentPicBean pic = new SentPicBean(pathImage, null);
            mySentPicAdapter.addPic(pic);
//           list.add(pic);
//            mySentPicAdapter.notifyDataSetChanged();
            // 刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }

    }


    public String mContent;
    private String mPic;

    public void posySentDynamics() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqSentDynamicsBean("18", SysConfig.uid, SysConfig.token, ReqSentDynamicsBean.TYPEID_ADD, ReqSentDynamicsBean.ARTICLEID_ADD, etContentDynamic.getText().toString(), mPic).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    //Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {

                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();

                }
                window.closeWindow();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();

                window.closeWindow();
            }
        });


    }

    public void postLikeArticle(int position, ResDynamicsBean.ListBean bean) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsLikeTeacherBean("15", SysConfig.uid, SysConfig.token, bean.getUserid(), "article").ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    //Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {



                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();

                }
                window.closeWindow();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();

                window.closeWindow();
            }
        });
    }


}

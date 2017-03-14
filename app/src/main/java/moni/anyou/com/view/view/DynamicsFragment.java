package moni.anyou.com.view.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.BaseInfo;
import moni.anyou.com.view.bean.DynamicsTempItems;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.request.ReqLiveBean;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.view.dynamics.SendDynamicActivity;
import moni.anyou.com.view.view.dynamics.adapter.DynamicsItemAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;


public class DynamicsFragment extends BaseFragment implements View.OnClickListener {
    private static int Fresh = 1;
    private static int LoadMore = 2;
    private View mView;
    private NetProgressWindowDialog window;
    private CircleImageView cvHeadIcon;
    private TextView tvLeft;
    private TextView tvTitle;
    private ImageView iv_icon;
    private ImageView ivRight;

    private ListView lvDynamics;
    private DynamicsItemAdapter dynamicsItemAdapter;
    private ArrayList<DynamicsTempItems> mItems;
    private int pageSize = 12;
    private int pageNo = 1;

    public DynamicsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamics, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        window = new NetProgressWindowDialog(mContext);
        tvLeft = (TextView) mView.findViewById(R.id.tv_left);
        tvTitle = (TextView) mView.findViewById(R.id.page_title);
        iv_icon = (ImageView) mView.findViewById(R.id.iv_icon);
        ivRight = (ImageView) mView.findViewById(R.id.right_tv);
        tvTitle.setText("动态");
        lvDynamics = (ListView) mView.findViewById(R.id.lv_dynamics);
        cvHeadIcon = (CircleImageView) mView.findViewById(R.id.civ_headIcon);
        dynamicsItemAdapter = new DynamicsItemAdapter(this);
        lvDynamics.setAdapter(dynamicsItemAdapter);
        mItems = new ArrayList<>();
        BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
        setBitmaptoImageView11(SysConfig.PicUrl+baseInfo.icon,cvHeadIcon);
    }

    @Override
    public void setAction() {
        super.setAction();
        ivRight.setOnClickListener(this);
    }

    public void marklike(int position, ResDynamicsBean.ListBean bean) {
        postLikeArticle(position, bean);
    }

    @Override
    public void setData() {
        super.setData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqPageBean("17", SysConfig.uid, SysConfig.token, "" + pageNo, "" + pageSize).ToJsonString();
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
                        ResDynamicsBean temp = new Gson().fromJson(t, ResDynamicsBean.class);
                        dynamicsItemAdapter.setDatas(temp.getList());
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
        String cmdPara = new ReqsLikeTeacherBean("15", SysConfig.uid, SysConfig.token, bean.getArticleid(), "article").ToJsonString();
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

                        getData();
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                startActivity(new Intent(mContext, SendDynamicActivity.class));
                activityAnimation(RIGHT_IN);
                break;
        }
    }
}

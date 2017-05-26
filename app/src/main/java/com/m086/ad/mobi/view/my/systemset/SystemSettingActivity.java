package com.m086.ad.mobi.view.my.systemset;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.bean.HomeItemBean;
import com.m086.ad.mobi.bean.request.ReqExitBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.view.my.systemset.adapter.SettingItemslAdapter;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;
import com.m086.ad.mobi.widget.NoListview;
import com.m086.ad.mobi.widget.dialog.MessgeDialog;

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {
    private NoListview listview;
    RelativeLayout rlExitLoad;
    private TextView tvExitLogin;

    private ArrayList<HomeItemBean> setItems;
    private SettingItemslAdapter myAdapter;
    private NetProgressWindowDialog window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        rlExitLoad = (RelativeLayout) findViewById(R.id.rl_exit_load);
        tvExitLogin = (TextView) findViewById(R.id.tv_exitLogin);
        listview = (NoListview) findViewById(R.id.lv_setsys);
        myAdapter = new SettingItemslAdapter(this);
        listview.setAdapter(myAdapter);
        window = new NetProgressWindowDialog(mBaseActivity);
    }

    @Override
    public void setData() {
        super.setData();
        tvTitle.setText("设置");
        setItems = new ArrayList<>();
        setItems.add(new HomeItemBean("清除缓存", AppTools.getFileCacheSize(mBaseActivity)));
        setItems.add(new HomeItemBean("关于我们", ""));
        myAdapter.setDatas(setItems);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.rl_exit_load:
                postExitLogin();
                break;
        }
    }

    @Override
    public void setAction() {
        super.setAction();
        initMsgview();
        tvExitLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlExitLoad.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    showMsgDialog("您确定要清除吗？", "取消", "确定", new MessgeDialog.MsgDialogListener() {
                        @Override
                        public void OnMsgClick() {

                        }

                        @Override
                        public void OnLeftClick() {
                            dismissMsgDialog();
                        }

                        @Override
                        public void OnRightClick() {
                            AppTools.clearFileCache(mBaseActivity);
                            myAdapter.replace(0, new HomeItemBean("清除缓存", AppTools.getFileCacheSize(mBaseActivity)));
                            dismissMsgDialog();
                        }

                        @Override
                        public void onDismiss() {
                            dismissMsgDialog();
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }

    public void postExitLogin() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqExitBean("5", SysConfig.uid).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {
                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);

                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {

                        AppTools.jumptoLogin(mBaseActivity);
                        ToastTools.showShort(mContext, "退出成功");
                        onBack();
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

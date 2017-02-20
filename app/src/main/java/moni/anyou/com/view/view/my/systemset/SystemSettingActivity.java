package moni.anyou.com.view.view.my.systemset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.request.ReqExitBean;
import moni.anyou.com.view.bean.request.ReqHomeBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.request.base.RequestStandard;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.my.systemset.adapter.SettingItemslAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.NoListview;

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {
    private NoListview listview;
    private ArrayList<HomeItemBean> setItems;
    private SettingItemslAdapter myAdapter;
    private NetProgressWindowDialog window;
    private TextView tvExitLogin;

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
        tvExitLogin = (TextView) findViewById(R.id.tv_exitLogin);
        listview = (NoListview) findViewById(R.id.lv_setsys);
        myAdapter=new SettingItemslAdapter(this);
        listview.setAdapter(myAdapter);
        window = new NetProgressWindowDialog(mContext);
    }
    @Override
    public void setData() {
        super.setData();
        tvTitle.setText("设置");
        setItems = new ArrayList<>();
        setItems.add(new HomeItemBean("关于我们", ""));
        setItems.add(new HomeItemBean("清除缓存", "111.8M"));
        myAdapter.setDatas(setItems);
        myAdapter.notifyDataSetChanged();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_exitLogin:
                postExitLogin();
                break;
        }
    }
    @Override
    public void setAction() {
        super.setAction();
        tvExitLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                        ToastTools.showShort(mContext,"退出成功");
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

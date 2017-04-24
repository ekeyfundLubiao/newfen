package moni.anyou.com.view.view.my.systemset;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.request.ReqHomeBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class SelectGardenActivity extends BaseActivity {
    private ListView lvSelect;
    private NetProgressWindowDialog window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garden);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("选择");
        lvSelect = (ListView) findViewById(R.id.lv_select);
    }

    @Override
    public void setData() {
        super.setData();
        window = new NetProgressWindowDialog
                (mBaseActivity);
    }

    @Override
    public void setAction() {
        super.setAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        postTeacherInfo();
    }

    public void postTeacherInfo() {
        JSONObject a = SysConfig.dataJson;
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqHomeBean("12", SysConfig.uid, SysConfig.token).ToJsonString();
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

package moni.anyou.com.view.view.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.request.ReqExitBean;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class SystemsNoticeActivity extends BaseActivity {

    private NetProgressWindowDialog window;
    private int pageSize=12;
    private int pageNo=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systems_notice);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        window = new NetProgressWindowDialog(mContext);
        initTitle();
        tvTitle.setText("系统公告");
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        postgetNotice();
    }

    public void postgetNotice() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqPageBean("5", SysConfig.uid,SysConfig.token,""+pageNo,""+pageSize).ToJsonString();
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
                        //ToastTools.showShort(mContext,"退出成功");
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

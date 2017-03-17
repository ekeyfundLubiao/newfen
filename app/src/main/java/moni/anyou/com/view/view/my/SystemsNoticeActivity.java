package moni.anyou.com.view.view.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.request.ReqExitBean;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.bean.response.ResNoticeData;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.my.adapter.NoticeItemslAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class SystemsNoticeActivity extends BaseActivity {

    private NetProgressWindowDialog window;
    private int pageSize=12;
    private int pageNo=0;
    private NoticeItemslAdapter mNoticeItemslAdapter;
    private ListView lv_notice;
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
        mNoticeItemslAdapter = new NoticeItemslAdapter(this);
        lv_notice = (ListView) findViewById(R.id.lv_notice);
        lv_notice.setAdapter(mNoticeItemslAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        postgetNotice();
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
                activityAnimation(RIGHT_OUT);
            }
        });
    }

    public void postgetNotice() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqPageBean("8", SysConfig.uid,SysConfig.token,""+pageNo,""+pageSize).ToJsonString();
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
                        mNoticeItemslAdapter.addDatas(new Gson().fromJson(t, ResNoticeData.class).getList());
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

package moni.anyou.com.view.view.my.invitefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.DataClassBean;
import moni.anyou.com.view.bean.request.ReqAddFamilyBean;
import moni.anyou.com.view.bean.request.ReqsFaimilyNunbersBean;
import moni.anyou.com.view.bean.response.ResFamilyNumer;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.TextTool;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.tool.VerificationTools;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class InviteFamilyActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvInvitedNumbers;
    private Button btnAddnumber;
    private Button btnSearchTelephoneBook;
    private EditText etPhoneNum;
    private NetProgressWindowDialog window;
    ResFamilyNumer.RelationBean mRelationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_numbers);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        window = new NetProgressWindowDialog(mContext);
        tvInvitedNumbers = (TextView) findViewById(R.id.tv_who);
        btnAddnumber = (Button) findViewById(R.id.btn_addnumber);
        btnSearchTelephoneBook = (Button) findViewById(R.id.btn_sheach_invitephonebook);
        etPhoneNum = (EditText) findViewById(R.id.et_phoneNum);
        tvTitle.setText("家庭成员");
        ivBack.setOnClickListener(this);
        btnAddnumber.setOnClickListener(this);
        btnSearchTelephoneBook.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.btn_addnumber:
                if (TextUtils.isEmpty(etPhoneNum.getText())) {
                    ToastTools.showShort(mContext, "邀请号码不能为空");
                    return;
                }
                getAddNumber();
                break;
            case R.id.btn_sheach_invitephonebook:
                break;
            default:
                break;
        }
    }

    @Override
    public void setData() {
        super.setData();

        mRelationBean = (ResFamilyNumer.RelationBean) getIntent().getSerializableExtra("bean");
//        TextTool.forDiffText("邀请"+SysConfig.userInfoJson.get("nick")+"的"+ Tools.getRole(mRelationBean.roleid)+"加入", tvInvitedNumbers, 2, mContext);
    }

    public void getAddNumber() {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqAddFamilyBean("2", SysConfig.uid, SysConfig.token, etPhoneNum.getText().toString(), mRelationBean.roleid).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        setResult(0x1111);
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


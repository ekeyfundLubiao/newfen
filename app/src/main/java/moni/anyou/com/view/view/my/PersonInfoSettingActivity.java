package moni.anyou.com.view.view.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import moni.anyou.com.view.bean.BaseInfo;
import moni.anyou.com.view.bean.DataClassBean;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.request.ReqUpdateChildBaseInfoBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.my.systemset.UpdateLoginActivity;
import moni.anyou.com.view.view.my.systemset.adapter.SettingItemslAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.NoListview;
import moni.anyou.com.view.widget.pikerview.view.RelationSeletor;

import static moni.anyou.com.view.config.SysConfig.dataJson;
import static moni.anyou.com.view.tool.Tools.getBaseRelatenumberdatas;

public class PersonInfoSettingActivity extends BaseActivity implements View.OnClickListener {
    private static String TYPE_SEX = "sex";
    private static String TYPE_BIRTHDAY = "birthday";
    private static String TYPE_ROLE = "role";
    private static String TYPE_ICON = "icon";
    private static String TYPE_CONTETS = "contents"; //老师寄语

    private NetProgressWindowDialog window;

    private ImageView tvHeadIcon;
    private TextView tvChangepwd;
    private TextView tvSex;
    private TextView tvBrithday;
    private TextView tvRelatetobaby;
    private TextView tvGarden;
    private TextView tvAccount;
    private RelativeLayout rlSex;
    private RelativeLayout rlBrithday;
    private RelativeLayout rlRelate;
    private RelativeLayout rlUpdatepwd;
    private RelativeLayout rlGarden;
    private RelativeLayout rlAccount;



    private RelationSeletor mRelationSeletor;
    private RelationSeletor mSexSeletor;


    ArrayList<HomeItemBean> baseInfoList;
    ArrayList<String> mStringRelations = new ArrayList<>();
    ArrayList<String> mStringSexs = new ArrayList<>();

    private String mType;
    private String mVaule;
    private ArrayList<DataClassBean> baseFamily = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info_setting);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        baseFamily = getBaseRelatenumberdatas();

        window = new NetProgressWindowDialog(mContext);
        tvTitle.setText("个人资料");
        rlGarden = (RelativeLayout) findViewById(R.id.rl_garden);
        rlAccount = (RelativeLayout) findViewById(R.id.rl_accout);
        rlBrithday = (RelativeLayout) findViewById(R.id.rl_brith);
        rlRelate = (RelativeLayout) findViewById(R.id.rl_relate);
        rlSex = (RelativeLayout) findViewById(R.id.rl_sex);
        rlUpdatepwd = (RelativeLayout) findViewById(R.id.rl_updatepwd);

        tvHeadIcon = (ImageView) findViewById(R.id.tv_headIcon);
        tvChangepwd = (TextView) findViewById(R.id.tv_changepwd);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvBrithday = (TextView) findViewById(R.id.tv_brithday);
        tvRelatetobaby = (TextView) findViewById(R.id.tv_relatetobaby);
        tvAccount = (TextView) findViewById(R.id.tv_accout);
        tvGarden = (TextView) findViewById(R.id.tv_garden);


        rlBrithday.setVisibility(View.GONE);
        rlSex.setVisibility(View.GONE);
        rlRelate.setVisibility(View.GONE);

    }

    @Override
    public void setData() {
        super.setData();
        viewforFamiy();
        initSeleterData();


    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        tvHeadIcon.setOnClickListener(this);
        tvChangepwd.setOnClickListener(this);
        tvRelatetobaby.setOnClickListener(this);
        tvBrithday.setOnClickListener(this);
        rlUpdatepwd.setOnClickListener(this);
        tvSex.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_headIcon:
                mType = TYPE_ICON;
                mVaule = "icon.png";
                postUpdateInfo();
//                ToastTools.showShort(mContext, "设置头像");
                break;
            case R.id.tv_changepwd:
                startActivity(new Intent(mBaseActivity, UpdateLoginActivity.class));
                // ToastTools.showShort(mContext,"修改密码");
                break;
            case R.id.tv_brithday:
                break;
            case R.id.tv_relatetobaby:
                mRelationSeletor.show();
                break;
            case R.id.tv_sex:
                mSexSeletor.show();
                break;
            case R.id.rl_updatepwd:
                break;
            default:
                break;
        }
    }

    public static void jumpTo(int position, HomeItemBean bean) {
        Log.d(TAG, "jumpTo: " + bean.value);
        if ("".equals(bean.value)) {

        } else if ("".equals(bean.value)) {

        }
        if ("".equals(bean.value)) {
        }
    }

    //teacher headmaster
    public void viewforTeacheandMaster() {
        baseInfoList.add(new HomeItemBean("班级", "class", false));
    }

    //headermasterD
    public void viewforHMD() {
    }

    //family
    public void viewforFamiy() {
        //  baseInfoList.add(new HomeItemBean("班级", "class", false));
        rlBrithday.setVisibility(View.VISIBLE);
        rlSex.setVisibility(View.VISIBLE);
        rlRelate.setVisibility(View.VISIBLE);
    }


    public void postUpdateInfo() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqUpdateChildBaseInfoBean("6", SysConfig.uid, SysConfig.token, mType, mVaule).ToJsonString();
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
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();


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

    private void initSeleterData() {

        for (int i = 0, size = baseFamily.size(); i < size; i++) {
            mStringRelations.add(baseFamily.get(i).getClassName());
        }
        mRelationSeletor = new RelationSeletor(mBaseActivity, mStringRelations, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String relation) {
                mType = TYPE_ROLE;
                mVaule = Tools.getRoleId(relation);
                postUpdateInfo();
                tvRelatetobaby.setText(relation);
            }

            @Override
            public void handle(int position) {

            }
        });
        mStringSexs.add("男");
        mStringSexs.add("女");
        mStringSexs.add("保密");
        mSexSeletor = new RelationSeletor(mBaseActivity, mStringSexs, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String sex) {
                mType = TYPE_SEX;

                if ("男".equals(sex)) {
                    mVaule = "1";
                } else if ("女".equals(sex)) {
                    mVaule = "2";
                } else if ("保密".equals(sex)) {
                    mVaule = "3";
                }
                postUpdateInfo();
                tvSex.setText(sex);
            }

            @Override
            public void handle(int position) {
                ToastTools.showShort(mContext, "" + position);
            }
        });


        BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);

        setBitmaptoImageView11(SysConfig.FileUrl + baseInfo.icon, tvHeadIcon);
        tvBrithday.setText(baseInfo.childbirthday);
        tvRelatetobaby.setText(Tools.getRole(baseInfo.role));
        tvGarden.setText(baseInfo.gradename);
        tvAccount.setText(baseInfo.mobile);
        switch (baseInfo.childsex) {
            case 1:
                tvSex.setText("男");
                break;
            case 2:
                tvSex.setText("女");
                break;
            case 3:
                tvSex.setText("保密");
                break;
        }
    }

}



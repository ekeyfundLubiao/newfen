package moni.anyou.com.view.view.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.my.systemset.UpdateLoginActivity;
import moni.anyou.com.view.view.my.systemset.adapter.SettingItemslAdapter;
import moni.anyou.com.view.widget.NoListview;
import moni.anyou.com.view.widget.pikerview.view.RelationSeletor;

public class PersonInfoSettingActivity extends BaseActivity implements View.OnClickListener{

    private NoListview lv_setselfInfo;
    private ImageView tvHeadIcon;
    private LinearLayout ll_changepwd;
    private TextView  tv_sex;
    private TextView tvBrithday;
    private TextView tvRelatetobaby;
    private RelativeLayout rl_sex;
    private RelativeLayout rl_brithday;
    private RelativeLayout rl_relate;
    private RelativeLayout rlUpdatepwd;
    private RelationSeletor mRelationSeletor;

    private SettingItemslAdapter myAdapter;
    ArrayList<HomeItemBean> baseInfoList;
    ArrayList<String> mStringRelations = new ArrayList<>();
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
        tvTitle.setText("个人资料");
        mStringRelations.add("爸爸");
        mStringRelations.add("妈妈");
        mStringRelations.add("爷爷");
        mStringRelations.add("奶奶");
        mStringRelations.add("叔叔");
        mRelationSeletor = new RelationSeletor(mBaseActivity, mStringRelations, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String relation) {
                tvRelatetobaby.setText(relation);
            }
        }, "","");
        rl_brithday = (RelativeLayout) findViewById(R.id.rl_brith);
        rl_relate=(RelativeLayout) findViewById(R.id.rl_relate);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rlUpdatepwd= (RelativeLayout) findViewById(R.id.rl_updatepwd);
        lv_setselfInfo = (NoListview) findViewById(R.id.lv_setselfInfo);
        tvHeadIcon = (ImageView) findViewById(R.id.tv_headIcon);
        ll_changepwd=(LinearLayout) findViewById(R.id.ll_changepwd);
        tv_sex= (TextView) findViewById(R.id.tv_sex);
        tvBrithday = (TextView) findViewById(R.id.tv_brithday);
        tvRelatetobaby=(TextView) findViewById(R.id.tv_relatetobaby);
        myAdapter=new SettingItemslAdapter(this);
        baseInfoList = new ArrayList<>();
        lv_setselfInfo.setAdapter(myAdapter);
        rl_brithday.setVisibility(View.GONE);
        rl_sex.setVisibility(View.GONE);
        rl_relate.setVisibility(View.GONE);
    }

    @Override
    public void setData() {
        super.setData();
        baseInfoList.add(new HomeItemBean("账号","900786",false));
        baseInfoList.add(new HomeItemBean("幼儿园","双星幼儿园",false));
        myAdapter.setDatas(baseInfoList);
        viewforFamiy();

    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        tvHeadIcon.setOnClickListener(this);
        ll_changepwd.setOnClickListener(this);
        tvRelatetobaby.setOnClickListener(this);
        tvBrithday.setOnClickListener(this);
        rlUpdatepwd.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_headIcon:
                ToastTools.showShort(mContext,"设置头像");
                break;
            case R.id.ll_changepwd:
                startActivity(new Intent(mBaseActivity, UpdateLoginActivity.class));
               // ToastTools.showShort(mContext,"修改密码");
                break;
            case R.id.tv_brithday:
                break;
            case R.id.tv_relatetobaby:
                mRelationSeletor.show();
                break;
            case R.id.rl_updatepwd:
                break;
            default:
                break;
        }
    }

    //teacher headmaster
    public void viewforTeacheandMaster(){
        baseInfoList.add(new HomeItemBean("班级","class",false));
    }
    //headermasterD
    public void viewforHMD(){
    }
    //family
    public void viewforFamiy(){
        baseInfoList.add(new HomeItemBean("班级","class",false));
        rl_brithday.setVisibility(View.VISIBLE);
        rl_sex.setVisibility(View.VISIBLE);
        rl_relate.setVisibility(View.VISIBLE);
    }
}

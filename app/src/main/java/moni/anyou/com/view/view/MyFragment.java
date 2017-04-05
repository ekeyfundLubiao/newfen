package moni.anyou.com.view.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.BaseInfo;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.AppTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.my.PersonInfoSettingActivity;
import moni.anyou.com.view.view.my.SuggestActivity;
import moni.anyou.com.view.view.my.adapter.HomeItemslAdapter;
import moni.anyou.com.view.view.my.systemset.SystemSettingActivity;
import moni.anyou.com.view.widget.NoListview;


public class MyFragment extends BaseFragment implements View.OnClickListener {
    private static int HEADMASTERLEAD=1;
    private static int HEADMASTER=2;
    private static int FAMILY=3;

    private View mView;
    private NoListview lv_home;
    private CircleImageView ivIconhead;
    private HomeItemslAdapter myAdaper;
    private ImageView llSetInfo;
    private TextView tvfamilyName;
    private TextView tvGardenName;
    ArrayList<HomeItemBean> itemBeens;
    private int typeRole=-1;
    private RelativeLayout rlSetting;
    private RelativeLayout RlSuggestion;
    private RelativeLayout llRightarr;
    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, container, false);
        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        initTitle(mView);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("我");
        ivIconhead = (CircleImageView)mView.findViewById(R.id.iv_iconhead);
        lv_home = (NoListview) mView.findViewById(R.id.lv_home);
        llSetInfo = (ImageView) mView.findViewById(R.id.ll_setInfo);
        tvGardenName= (TextView) mView.findViewById(R.id.tv_className);
        tvfamilyName= (TextView) mView.findViewById(R.id.tv_familyName);
        rlSetting= (RelativeLayout) mView.findViewById(R.id.rl_setting);
        RlSuggestion= (RelativeLayout) mView.findViewById(R.id.rl_suggestion);
        llRightarr= (RelativeLayout) mView.findViewById(R.id.ll_Right_arr);
        myAdaper = new HomeItemslAdapter(this);
        lv_home.setAdapter(myAdaper);
        itemBeens = new ArrayList<HomeItemBean>();
    }

    @Override
    public void setData() {
        super.setData();

        switch (typeRole) {
            case 1:
                initChangeGraden();
                initHeadmaster();
                break;
            case 2:
                initHeadmaster();
                break;
            case 3:
                initInvitefamily();
                break;
            default:
                initInvitefamily();
                break;

        }
        itemBeens.add(new HomeItemBean(R.mipmap.tongxunlu, "通讯录"));
        itemBeens.add(new HomeItemBean(R.mipmap.icon_notice, "系统公告"));
        itemBeens.add(new HomeItemBean(R.mipmap.icon_help_center, "使用帮助"));
        myAdaper.setDatas(itemBeens);

    }

    @Override
    public void setAction() {
        super.setAction();
        llSetInfo.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        RlSuggestion.setOnClickListener(this);
        llRightarr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setInfo:
                Intent i = new Intent();
                i.setClass(mBaseActivity, PersonInfoSettingActivity.class);
                mBaseActivity.startActivity(i);
                activityAnimation(RIGHT_IN);
                break;
            case R.id.rl_setting:
                startActivity(new Intent(mContext, SystemSettingActivity.class));
                activityAnimation(RIGHT_IN);
                break;
            case R.id.rl_suggestion:
                startActivity(new Intent(mContext, SuggestActivity.class));
                activityAnimation(RIGHT_IN);
                break;
            case R.id.ll_Right_arr:
                startActivity(new Intent(mContext, PersonInfoSettingActivity.class));
                activityAnimation(RIGHT_IN);
                break;
            default:
                break;
        }
    }
    // hmaster teacher
    public void initHeadmaster() {
        itemBeens.add(new HomeItemBean(R.mipmap.tongxunlu, "通讯录"));
    }
    //family
    public void initInvitefamily(){
        itemBeens.add(new HomeItemBean(R.mipmap.jiating, "邀请家人"));
    }
    /**
     * 总园长
     */
    public void initChangeGraden(){
        itemBeens.add(new HomeItemBean(R.mipmap.home_icon_changegarde, "切换幼儿园","徐家汇中心幼儿园",false));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppTools.isLogin(mBaseActivity)) {
            BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
            tvfamilyName.setText(baseInfo.child+ Tools.getRole(baseInfo.role));
            tvGardenName.setText(baseInfo.companyname+baseInfo.gradename);
          setBitmaptoImageView11(SysConfig.PicUrl+baseInfo.icon,ivIconhead);
            //setBitmaptoImageView11("file://"+mContext.getFilesDir().getPath()+"user/0/top.zibin.luban.example/cache/luban_disk_cache/1490487176029.jpg",ivIconhead);
        }

    }
}

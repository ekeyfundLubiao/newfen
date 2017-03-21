package moni.anyou.com.view.view.my.invitefamily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.DataClassBean;
import moni.anyou.com.view.bean.InvitedInfo;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SelectFamily;
import moni.anyou.com.view.bean.request.ReqUnbindFBean;
import moni.anyou.com.view.bean.request.ReqsFaimilyNunbersBean;
import moni.anyou.com.view.bean.response.ResFamilyNumer;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.TextTool;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.tool.VerificationTools;
import moni.anyou.com.view.view.account.CompleteBaseInfoActivity;
import moni.anyou.com.view.view.my.invitefamily.adapter.FamilyNumberAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.dialog.PopAddFamilySuccess;
import moni.anyou.com.view.widget.dialog.PopunbindFamily;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;

import static moni.anyou.com.view.tool.Tools.getBaseRelatenumberdatas;

public class FamilyNumbersActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView rcFamilyNumbers;
    private FamilyNumberAdapter MyAdapter;
    private ArrayList<ResFamilyNumer.RelationBean> numberBeans;
    private PopAddFamilySuccess mPopAddFamilySuccess;
    private PopunbindFamily mPopunbindFamily;
    private NetProgressWindowDialog window;
    private ArrayList<DataClassBean> baseFamily = null;
    private String relativeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_family);
        init();

    }

    @Override
    public void initView() {
        super.initView();
        initTitlewithCheckbox();
        checkbox.setText("解绑");
        try {
            if (SysConfig.userInfoJson.getInt("recommendId")>0) {
                checkbox.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        window = new NetProgressWindowDialog(mContext);
        rcFamilyNumbers = (RecyclerView) findViewById(R.id.rc_numbers);
        tvTitle.setText("邀请家人");
        ivBack.setOnClickListener(this);
        getdata();
    }

    @Override
    public void setData() {
        super.setData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rcFamilyNumbers.setLayoutManager(gridLayoutManager);
        numberBeans = new ArrayList<>();
        MyAdapter = new FamilyNumberAdapter(this, numberBeans);
        rcFamilyNumbers.setAdapter(MyAdapter);
        initData();
        MyAdapter.notifyDataSetChanged();


    }

    @Override
    public void setAction() {
        super.setAction();
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkbox.setText("取消");
                } else {
                    checkbox.setText("解绑");

                }

                int size = getList(isChecked).size();
                for (int i = 0; i < size; i++) {
                    SelectFamily tempBean = (SelectFamily) checkBeans.get(i);
                    MyAdapter.notifyItemChanged(tempBean.positon, tempBean.bean);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.btn_commit:
                MyAdapter.notifyItemChanged(mBean.positon, mBean.bean);
                mPopunbindFamily.dismiss();
                break;
            case R.id.btn_unbindcommit:
                getUnbindF(relativeId);
                mPopunbindFamily.dismiss();
                break;
        }
    }

    public void initData() {
    }

    SelectFamily mBean;

    /**
     * 解绑成员
     *
     * @param bean
     */
    public void removeFamoilyNumbers(SelectFamily bean) {
        mBean = bean;
        relativeId = bean.bean.getUser_id();
        mPopunbindFamily = new PopunbindFamily(this, this);
        mPopunbindFamily.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
        mPopunbindFamily.isShowing();


    }

    public void addFamoilyNumbers(SelectFamily bean, int position) {

        Intent addNumntent = new Intent();
        addNumntent.putExtra("bean", numberBeans.get(position));
        addNumntent.setClass(mBaseActivity, InviteFamilyActivity.class);
        startActivityForResult(addNumntent, 9111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            mPopAddFamilySuccess = new PopAddFamilySuccess(this, new InvitedInfo("伯伯", "123456", "774590"), this);
//            mPopAddFamilySuccess.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
//            mPopAddFamilySuccess.isShowing();
//        }
        if (requestCode == 9111 && resultCode == 0x1111) {
            if (data != null) {
                data.getExtras().getString("account");
                data.getExtras().getString("pwd");
            }
            mPopAddFamilySuccess = new PopAddFamilySuccess(this, new InvitedInfo(data.getExtras().getString("role"), data.getExtras().getString("account"), data.getExtras().getString("pwd")), this);
            mPopAddFamilySuccess.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
            mPopAddFamilySuccess.isShowing();
        }

    }


    ArrayList<SelectFamily> checkBeans = new ArrayList<>();

    /**
     * 获取可删除集合
     *
     * @param isCheck
     * @return
     */
    public ArrayList<SelectFamily> getList(boolean isCheck) {
        checkBeans.clear();
        int size = numberBeans.size();
        for (int i = 1; i < size; i++) {
            ResFamilyNumer.RelationBean tempBean = numberBeans.get(i);
            if (tempBean.status == 1) {
                tempBean.boolDelete = isCheck;
                checkBeans.add(new SelectFamily(i, tempBean));
            }
        }
        return checkBeans;
    }

    public void getdata() {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsFaimilyNunbersBean("10", SysConfig.uid, SysConfig.token).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    // Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        numberBeans.clear();
                        ResFamilyNumer Fnumber = new Gson().fromJson(t, ResFamilyNumer.class);
//                        int baseNumSize = baseFamily.size();
                       // numberBeans = Fnumber.getList();
//                        ArrayList<ResFamilyNumer.RelationBean> numberBeanArray = Fnumber.getList();
//                        for (int i = 0, size = numberBeanArray.size(); i < size; i++) {
//
//                            ResFamilyNumer.RelationBean tempbean = numberBeanArray.get(i);
//                            numberBeans.set(i, new ResFamilyNumer.RelationBean(
//                                    tempbean.getUser_id(),
//                                    tempbean.getRecommendId(),
//                                    tempbean.getStatus(),
//                                    (tempbean.getNick().equals("") ? "匿名" : tempbean.getNick()),
//                                    tempbean.getMobile(),
//                                    (tempbean.getIcon().equals("")
//                                            ? Tools.getRoledefaultIcon(tempbean.role) : tempbean.getIcon()),
//                                    Tools.getRole(tempbean.role)));
//
//
//                        }
                       // numberBeans.clear();
                        ArrayList<ResFamilyNumer.RelationBean> changdataArray = new ArrayList<ResFamilyNumer.RelationBean>();
                        baseFamily = Tools.replaceNum(Fnumber.getList());
                        for (int i = 0, size = baseFamily.size(); i < size; i++) {

                            DataClassBean tempbean = baseFamily.get(i);
                            changdataArray.add(new ResFamilyNumer.RelationBean(
                                    tempbean.getId(),
                                    "",
                                    tempbean.status,
                                    (tempbean.nickname == null || tempbean.nickname.equals("") ? "匿名" : tempbean.getNickname()),
                                    (tempbean.tellPhoneNum == null || tempbean.tellPhoneNum.equals("") ? "" : tempbean.getTellPhoneNum()),
                                    (tempbean.getPic().equals("")
                                            ? Tools.getRoledefaultIcon(tempbean.getClassID()) : tempbean.getPic()),
                                    Tools.getRole(tempbean.getClassID())));
                        }

                        Log.d(TAG, "numbeansize:" + numberBeans.size());

                        ToastTools.showShort(mContext, "numbeansize:" + numberBeans.size());
                        numberBeans=changdataArray;

                        MyAdapter.setDatas(numberBeans);
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

    public void getUnbindF(String id) {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqUnbindFBean("11", SysConfig.uid, SysConfig.token, id).ToJsonString();
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
                        getdata();
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

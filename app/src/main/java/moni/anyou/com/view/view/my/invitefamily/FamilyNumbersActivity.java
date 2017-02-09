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

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.lang.reflect.Array;
import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.InvitedInfo;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SelectFamily;
import moni.anyou.com.view.bean.request.ReqsFaimilyNunbersBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.TextTool;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.tool.VerificationTools;
import moni.anyou.com.view.view.account.CompleteBaseInfoActivity;
import moni.anyou.com.view.view.my.invitefamily.adapter.FamilyNumberAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.dialog.PopAddFamilySuccess;
import moni.anyou.com.view.widget.dialog.PopunbindFamily;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;

public class FamilyNumbersActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView rcFamilyNumbers;
    private FamilyNumberAdapter MyAdapter;
    private ArrayList<RelationBean> numberBeans;
    private PopAddFamilySuccess mPopAddFamilySuccess;
    private PopunbindFamily mPopunbindFamily;
    private NetProgressWindowDialog window;

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
        // rcFamilyNumbers.addItemDecoration(new DividerItemDecoration(mContext,GridLayoutManager.HORIZONTAL));
        // rcFamilyNumbers.addItemDecoration(new DividerItemDecoration(mContext,GridLayoutManager.VERTICAL));
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
//                    RelationBean temp = numberBeans.get(1);
//                    temp.boolDelete = true;
                } else {
                    checkbox.setText("解绑");
//                    RelationBean temp = numberBeans.get(1);
//                    temp.boolDelete = false;
//                    MyAdapter.notifyItemChanged(1, temp);
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
        }
    }

    public void initData() {
        RelationBean ftemp = new RelationBean("李铁牛", "http://img3.imgtn.bdimg.com/it/u=2364754357,1896189482&fm=21&gp=0.jpg", 1, "自己", "18909876789");
        RelationBean mtemp = new RelationBean("刘红梅", "http://img4.imgtn.bdimg.com/it/u=2040109377,1412473547&fm=21&gp=0.jpg", 1, "妈妈", "18923456789");
        RelationBean gftemp = new RelationBean("李从军", "http://qq1234.org/uploads/allimg/140926/3_140926144058_3.jpg", 1, "爷爷", "18909876749");
        RelationBean gmtemp = new RelationBean("黄小华", "http://img0.imgtn.bdimg.com/it/u=2752436590,1904914861&fm=21&gp=0.jpg", 1, "奶奶", "18909865789");
        RelationBean utemp = new RelationBean("李魏国", "http://img3.imgtn.bdimg.com/it/u=2364754357,1896189482&fm=21&gp=0.jpg", 0, "叔叔", "18909876789");
        RelationBean atemp = new RelationBean("刘小米", "http://img4.imgtn.bdimg.com/it/u=2040109377,1412473547&fm=21&gp=0.jpg", 0, "婶婶", "18923456789");
        numberBeans.add(ftemp);
        numberBeans.add(mtemp);
        numberBeans.add(gftemp);
        numberBeans.add(gmtemp);
        numberBeans.add(utemp);
        numberBeans.add(atemp);
        //  MyAdapter.notifyDataSetChanged();
    }

    SelectFamily mBean;

    /**
     * 解绑成员
     *
     * @param bean
     */
    public void removeFamoilyNumbers(SelectFamily bean) {
        mBean = bean;
        mPopunbindFamily = new PopunbindFamily(this, this);
        mPopunbindFamily.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
        mPopunbindFamily.isShowing();


    }

    public void addFamoilyNumbers(SelectFamily bean) {

        Intent addNumntent = new Intent();
        addNumntent.setClass(mBaseActivity, InviteFamilyActivity.class);
        startActivityForResult(addNumntent, 9111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            mPopAddFamilySuccess = new PopAddFamilySuccess(this, new InvitedInfo("伯伯", "123456", "774590"), this);
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
            RelationBean tempBean = numberBeans.get(i);
            if (tempBean.mark == 1) {
                tempBean.boolDelete = isCheck;
                checkBeans.add(new SelectFamily(i, tempBean));
            }
        }
        return checkBeans;
    }

//    {"cmd":"10","uid":"1000162","token":"20111222"}


    public void getdata() {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsFaimilyNunbersBean("10",SysConfig.uid,SysConfig.token).ToJsonString();
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

                    }else {
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

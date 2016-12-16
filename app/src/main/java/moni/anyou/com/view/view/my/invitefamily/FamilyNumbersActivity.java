package moni.anyou.com.view.view.my.invitefamily;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SelectFamily;
import moni.anyou.com.view.tool.TextTool;
import moni.anyou.com.view.tool.VerificationTools;
import moni.anyou.com.view.view.my.invitefamily.adapter.FamilyNumberAdapter;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;

public class FamilyNumbersActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView rcFamilyNumbers;
    private FamilyNumberAdapter MyAdapter;
    private ArrayList<RelationBean> numberBeans;

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
        rcFamilyNumbers = (RecyclerView) findViewById(R.id.rc_numbers);
        tvTitle.setText("邀请家人");
        ivBack.setOnClickListener(this);

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

                int size=getList(isChecked).size();
                for (int i=0;i<size;i++) {
                    SelectFamily tempBean=(SelectFamily) checkBeans.get(i);
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

    /**
     * 解绑成员
     * @param bean
     */
    public void removeFamoilyNumbers(SelectFamily bean){
        MyAdapter.notifyItemChanged(bean.positon, bean.bean);
    }
    ArrayList<SelectFamily> checkBeans = new ArrayList<>();

    /**
     * 获取可删除集合
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
        return  checkBeans;
    }

}

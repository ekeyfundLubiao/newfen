package moni.anyou.com.view.view.my.systemset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.view.my.systemset.adapter.SettingItemslAdapter;
import moni.anyou.com.view.widget.NoListview;

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {

    private NoListview listview;
    private ArrayList<HomeItemBean> setItems;
    private SettingItemslAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        listview = (NoListview) findViewById(R.id.lv_setsys);
        myAdapter=new SettingItemslAdapter(this);
        listview.setAdapter(myAdapter);
    }

    @Override
    public void setData() {
        super.setData();
        tvTitle.setText("设置");
        setItems = new ArrayList<>();
        setItems.add(new HomeItemBean("关于我们", ""));
        setItems.add(new HomeItemBean("清除缓存", "111.8M"));
        myAdapter.setDatas(setItems);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
        }
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }


//    sendMsg={"cmd":"5","uid":"1000162"}
}

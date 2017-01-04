package moni.anyou.com.view.view.dynamics;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.dynamics.adapter.SendPicAdapter;

public class SendDynamicActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rcPic;
    private SendPicAdapter mySentPicAdapter;
    ArrayList<SentPicBean> list=new ArrayList<SentPicBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("");
        tvRight.setText("发送");
        tvRight.setVisibility(View.VISIBLE);
        rcPic = (RecyclerView) findViewById(R.id.rc_pic);
    }

    @Override
    public void setAction() {
        tvRight.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void setData() {
        super.setData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcPic.setLayoutManager(linearLayoutManager);
        list.add(new SentPicBean());
        mySentPicAdapter = new SendPicAdapter(this, list);
        rcPic.setAdapter(mySentPicAdapter);
        mySentPicAdapter.setmOnItemClickListener(new SendPicAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, SentPicBean data) {
                if ("".equals(data.filePathName)) {
                    ToastTools.showShort(mContext,"添加");
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
            case R.id.right_tv:
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }
}

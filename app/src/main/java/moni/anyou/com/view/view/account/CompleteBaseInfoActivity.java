package moni.anyou.com.view.view.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.widget.pikerview.TimeSelector;
import moni.anyou.com.view.widget.pikerview.view.RelationSeletor;

public class CompleteBaseInfoActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup radiogroup_sex;
    private TextView tvBrithday;
    private TextView tvRelatetobaby;


    private TimeSelector timeSelector = null;
    RelationSeletor mRelationSeletor = null;
    ArrayList<String> mStringRelations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_base_info);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvTitle.setText("完善信息");
        radiogroup_sex = (RadioGroup) findViewById(R.id.radiogroup_sex);
        tvBrithday = (TextView) findViewById(R.id.tv_brithday);
        tvRelatetobaby = (TextView) findViewById(R.id.tv_relatewithbaby);
        timeSelector = new TimeSelector(mContext, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                tvBrithday.setText(time);
            }
        }, "1989-01-30 00:00", "2018-12-31 00:00");
    }

    @Override
    public void setAction() {
        super.setAction();
        tvRight.setOnClickListener(this);
        tvBrithday.setOnClickListener(this);
        tvRelatetobaby.setOnClickListener(this);
        radiogroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ToastTools.showShort(mContext, "xingbie" + checkedId);
            }
        });
        mStringRelations.add("爸爸");
        mStringRelations.add("妈妈");
        mStringRelations.add("爷爷");
        mStringRelations.add("奶奶");
        mStringRelations.add("叔叔");
        mRelationSeletor = new RelationSeletor(this, mStringRelations, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String text) {
               tvRelatetobaby.setText(text);
            }
        }, "", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                break;
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_brithday:
                timeSelector.show();
                break;
            case R.id.tv_relatewithbaby:
                mRelationSeletor.show();

                break;
            default:
                break;
        }

    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }
}

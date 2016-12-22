package moni.anyou.com.view.view.daily;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.widget.pikerview.view.RelationSeletor;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView open;
    RelationSeletor mRelationSeletor = null;
    ArrayList<String> mStringRelations = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        context = this;
        mStringRelations.add("爸爸");
        mStringRelations.add("妈妈");
        mStringRelations.add("爷爷");
        mStringRelations.add("奶奶");
        mStringRelations.add("叔叔");
        open = (ImageView) findViewById(R.id.open);
        mRelationSeletor = new RelationSeletor(this, mStringRelations, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String text) {
                ToastTools.showShort(context, text);
            }
        }, "", "");
    }

    @Override
    public void setAction() {
        super.setAction();
        open.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
                mRelationSeletor.show();
                break;
            default:
                break;
        }
    }
}

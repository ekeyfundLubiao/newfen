package moni.anyou.com.view.view.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class SuggestActivity extends BaseActivity implements View.OnClickListener{

    private Button btnCommit;
    private EditText etSuggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("意见反馈");
        btnCommit = (Button) findViewById(R.id.btn_commit);
        etSuggestion = (EditText)findViewById(R.id.et_suggestion);
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
        }
    }
}

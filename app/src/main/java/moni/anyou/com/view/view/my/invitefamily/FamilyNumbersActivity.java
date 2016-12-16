package moni.anyou.com.view.view.my.invitefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.tool.TextTool;
import moni.anyou.com.view.tool.VerificationTools;

public class FamilyNumbersActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvInvitedNumbers;
    private Button btnAddnumber;
    private Button btnSearchTelephoneBook;
    private EditText etPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_numbers);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvInvitedNumbers = (TextView) findViewById(R.id.tv_who);
        btnAddnumber = (Button) findViewById(R.id.btn_addnumber);
        btnSearchTelephoneBook = (Button) findViewById(R.id.btn_sheach_invitephonebook);
        etPhoneNum = (EditText) findViewById(R.id.et_phoneNum);
        tvTitle.setText("家庭成员");
        ivBack.setOnClickListener(this);
        btnAddnumber.setOnClickListener(this);
        btnSearchTelephoneBook.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.btn_addnumber:
                if (TextUtils.isEmpty(etPhoneNum.getText())&&VerificationTools.isMobile(etPhoneNum.getText().toString().trim())) {
                    getAddNumber();
                }
                break;
            case R.id.btn_sheach_invitephonebook:
                break;
            default:
                break;
        }
    }

    @Override
    public void setData() {
        super.setData();
        TextTool.forDiffText("邀请XXXX的爷爷加入", tvInvitedNumbers, 2, mContext);
    }

    public void getAddNumber(){
    }
}

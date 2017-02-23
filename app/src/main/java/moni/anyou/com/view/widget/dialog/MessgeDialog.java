package moni.anyou.com.view.widget.dialog;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.base.BaseDialog;
import moni.anyou.com.view.view.StartActivity;


/**
 * Created by Administrator on 2017/2/21.
 */
public class MessgeDialog extends BaseDialog {
    private TextView mMsgText;
    private Button mLeftBtn;
    private Button mRightBtn;
    private MsgDialogListener mMsgDialogListener;

    public MessgeDialog(StartActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_message);
        initWidthDialog(R.style.dialog_animation, false, 0.7f);
    }

    @Override
    public void initView() {
        super.initView();
        mMsgText = (TextView) findViewById(R.id.text_msg);
        mLeftBtn = (Button) findViewById(R.id.btn_left);
        mRightBtn = (Button) findViewById(R.id.btn_right);
    }

    public void setMRL(String message,String leftStr,String rightStr) {
        setMessage(message);
        setLeft(leftStr);
        setRight(rightStr);
    }
    public void setMessage(String message) {
        setMessage(message, R.color.color_left_title);
    }

    public void setMessage(String message, int colorResId) {
        mMsgText.setText(message);
        mMsgText.setTextColor(ContextCompat.getColor(mContext, colorResId));
    }

    public void setLeft(String message) {
        setLeft(message, R.color.color_left_title);
    }

    public void setLeft(String message, int colorResId) {
        mLeftBtn.setText(message);
        mLeftBtn.setTextColor(ContextCompat.getColor(mContext, colorResId));
    }

    public void setRight(String message) {
        setRight(message, R.color.color_left_title);
    }

    public void setRight(String message, int colorResId) {
        mRightBtn.setText(message);
        mRightBtn.setTextColor(ContextCompat.getColor(mContext, colorResId));
    }

    public void setMsgDialogListener(MsgDialogListener l) {
        mMsgDialogListener = l;
    }

    @Override
    public void setData() {
        super.setData();
        setLayoutBg(R.color.white);
    }

    @Override
    public void setListener() {
        super.setListener();
        mMsgText.setOnClickListener(mOnClickListener);
        mLeftBtn.setOnClickListener(mOnClickListener);
        mRightBtn.setOnClickListener(mOnClickListener);
    }

    @Override
    public void sendHttp() {
        super.sendHttp();
    }

    @Override
    public void onBack() {
        super.onBack();
        if(null!=mMsgDialogListener)
        {
            mMsgDialogListener.onDismiss();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.text_msg:
                    if(null!=mMsgDialogListener)
                    {
                        mMsgDialogListener.OnMsgClick();
                    }
                    break;
                case R.id.btn_left:
                     if(null!=mMsgDialogListener)
                     {
                         mMsgDialogListener.OnLeftClick();
                     }
                    break;
                case R.id.btn_right:
                    if(null!=mMsgDialogListener)
                    {
                        mMsgDialogListener.OnRightClick();
                    }
                    break;
            }
        }
    };

    public static interface MsgDialogListener {
        public void OnMsgClick();

        public void OnLeftClick();

        public void OnRightClick();

        public void onDismiss();
    }
}

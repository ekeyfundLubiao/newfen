package com.m086.ad.mobi.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.bean.InvitedInfo;

/**
 * Created by lubiao on 2017/3/22.
 */

public class PopAddSuccess extends PopupWindow {
    private Button btnCommit;
    private Button btnCancle;
    private TextView tvRelation;
    private TextView tvAccount;
    private TextView tvNumbersPwd;
    private RelativeLayout llMsg;
    private RelativeLayout llWechat;
    private View mView;
    Activity mcontext;
    InvitedInfo mbeanInfo;

    public SentInfoBean getsentInf() {
        String phoneNum =mbeanInfo.account;
        String msgInf =   tvNumbersPwd.getText().toString();
        return new SentInfoBean(phoneNum,msgInf);
    }

    public PopAddSuccess(Activity context, InvitedInfo beanInfo, View.OnClickListener itemsOnClick) {
        super(context);
        mcontext = context;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mView = mInflater.inflate(R.layout.pop_add_family_number, null);
        btnCommit = (Button) mView.findViewById(R.id.btn_commit);
        btnCancle = (Button) mView.findViewById(R.id.btn_cancel);
        tvRelation = (TextView) mView.findViewById(R.id.tv_relation);
        tvAccount = (TextView) mView.findViewById(R.id.tv_account);
        tvNumbersPwd = (TextView) mView.findViewById(R.id.tv_passwold);
        llMsg = (RelativeLayout) mView.findViewById(R.id.llMsg);
        llWechat = (RelativeLayout) mView.findViewById(R.id.llWechat);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnCommit.setOnClickListener(itemsOnClick);
        llMsg.setOnClickListener(itemsOnClick);
        llWechat.setOnClickListener(itemsOnClick);
        mbeanInfo = beanInfo;
        initData(beanInfo);
        //设置SelectPicPopupWindow的View
        this.setContentView(mView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        backgroundAlpha(1f);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0xb0000000);
        ColorDrawable dw = new ColorDrawable(0xff000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        backgroundAlpha(1f);
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setOnDismissListener(new PopAddSuccess.poponDismissListener());

    }

    @Override
    public boolean isShowing() {
        backgroundAlpha(0.5f);
        return super.isShowing();
    }


    private void initData(InvitedInfo invitedInfo) {
        tvAccount.setText("账号：" + invitedInfo.account);
        tvRelation.setText("关系：" + invitedInfo.relation);
        tvNumbersPwd.setText("密码：" + invitedInfo.pwd);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mcontext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mcontext.getWindow().setAttributes(lp);
    }


    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

   public class SentInfoBean {
        private String phoneNum;
        private String msgcontants;

        public SentInfoBean(String phoneNum, String msgcontants) {
            this.phoneNum = phoneNum;
            this.msgcontants = msgcontants;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

       public String getMsgcontants() {
           return msgcontants;
       }

       public void setMsgcontants(String msgcontants) {
           this.msgcontants = msgcontants;
       }
   }

}

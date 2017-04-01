package moni.anyou.com.view.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.config.AnyouerApplication;
import moni.anyou.com.view.tool.AppTools;
import moni.anyou.com.view.tool.ToastTools;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    public static final String SHARE_FROM_ACTIVITY = "wasShare";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_weixin);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initView() {

        AnyouerApplication.mIWXAPI.handleIntent(getIntent(), this);
    }

    @Override
    public void setData() {
    }


    @Override
    public void onReq(BaseReq arg0) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                ToastTools.showShort(mContext, "发送成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastTools.showShort(mContext, "取消发送");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //ToastTools.showShort(mContext, R.string.errcode_deny);
                break;
            default:
                //ToastTools.showShort(mContext, R.string.errcode_unknown);
                break;
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AnyouerApplication.mIWXAPI.handleIntent(intent, this);
    }


}

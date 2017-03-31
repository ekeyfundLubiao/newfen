package moni.anyou.com.view.view.daily;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.photo.local.ConfigHelper;
import moni.anyou.com.view.widget.pikerview.TimeSelector;
import moni.anyou.com.view.widget.pikerview.view.RelationSeletor;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView open;
    RelationSeletor mRelationSeletor = null;
    ArrayList<String> mStringRelations = new ArrayList<>();
    Context context;
    private Button btn_camera;
    private Button btn_grelly;
    private Button btn_time;

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
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_grelly = (Button) findViewById(R.id.btn_grelly);

        initPermissions();
    }

    @Override
    public void setAction() {
        super.setAction();
        // open.setOnClickListener(this);
        btn_grelly.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        findViewById(R.id.btn_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimeSelector(mContext, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {

                    }
                }, "2001-01-01 00:00", "2018-12-31 00:00").show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_grelly:
                GalleryPick.getInstance().setGalleryConfig(ConfigHelper.getInstance().getLocalConfig(iHandlerCallBack, 1)).open(this);
                break;
            case R.id.btn_camera:
                GalleryPick.getInstance().setGalleryConfig(ConfigHelper.getInstance().getPhotoConfig(iHandlerCallBack)).open(this);
            default:
                break;
        }
    }


    private IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
            //开启

        }

        @Override
        public void onSuccess(List<String> photoList) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onError() {

        }
    };

    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "拒绝过了");
                Toast.makeText(mContext, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Log.i(TAG, "不需要授权 ");
        }
    }

}

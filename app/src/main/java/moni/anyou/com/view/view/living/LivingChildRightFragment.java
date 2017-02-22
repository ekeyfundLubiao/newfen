package moni.anyou.com.view.view.living;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.bean.request.ReqLiveBean;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.living.adapter.VideoPublicAdapter;
import moni.anyou.com.view.view.my.systemset.adapter.SettingItemslAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.NoListview;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;


public class LivingChildRightFragment extends BaseFragment {


    private static int Fresh = 1;
    private static int LoadMore = 2;
    private NetProgressWindowDialog window;
    private int pageSize=12;
    private int  pageNo=1;
    private View mView;
    private PtrClassicDefaultHeader  header;
    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView listview;

    private VideoPublicAdapter publicAdapter;
     private  boolean isVisible=false;
    ArrayList<VideoBean> mVideoArray;
    public LivingChildRightFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_living_right, container, false);

        init(mView);
        isCreate=true;
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        window = new NetProgressWindowDialog(mContext);
        mVideoArray = new ArrayList<>();
        listview=(RecyclerView) mView.findViewById(R.id.listview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        listview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
        listview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        listview.setLayoutManager(gridLayoutManager);

        header = new PtrClassicDefaultHeader (mBaseActivity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);

        ptrFrame = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_list_view_frame);
        ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);

    }

    @Override
    public void setData() {
        super.setData();
        mVideoArray.clear();
        publicAdapter = new VideoPublicAdapter(this);
        listview.setAdapter(publicAdapter);
        publicAdapter.setmOnItemClickListener(new VideoPublicAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ResLiveBean.LiveBean data) {
                Intent i = new Intent();
                i.putExtra("data", data);
                i.setClass(mBaseActivity, ALivingActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible && isCreate) {
            ptrFrame.autoRefresh();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isCreate) {
            ptrFrame.autoRefresh();
        }
    }
    @Override
    public void setAction() {
        super.setAction();

        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                pageNo++;
                getData(2);
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                pageNo = 1;
                getData(1);
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();

                    }
                }, 1000);
            }
        });

        // the following are default settings
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(1000);
        // default is false
        ptrFrame.setPullToRefresh(true);
        // default is true
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {

                ptrFrame.autoRefresh();
            }
        }, 100);

    }


    public void getData( final int Type) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqLiveBean("16", SysConfig.uid, SysConfig.token,""+pageNo,""+pageSize,"publiclive").ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    //Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        ResLiveBean temp= new Gson().fromJson(t, ResLiveBean.class);
                        switch (Type) {
                            case 1:
                                publicAdapter.setDatas(temp.getList());
                                break;
                            case 2:
                                publicAdapter.addDatas(temp.getList());
                                break;
                        }
                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();

                }
                window.closeWindow();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();

                window.closeWindow();
            }
        });


    }

}

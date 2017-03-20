package moni.anyou.com.view.view.living;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.bean.request.ReqLiveBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.living.adapter.VideoRecycleAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;


public class LivingChildFragment extends BaseFragment {

    private static int Fresh = 1;
    private static int LoadMore = 2;
    private NetProgressWindowDialog window;
    private View mView;
    //    private StoreHouseHeader header;
//    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView rvBabyVideo;
    private SHSwipeRefreshLayout swipeRefreshLayout;

    private VideoRecycleAdapter mVideoRecycleAdapter;
    List<ResLiveBean.LiveBean> mVideoArray;
    private boolean isVisible = false;

    private int pageSize = 12;
    private int pageNo = 1;
    private int totalCount;


    public LivingChildFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_living_left, container, false);
        isCreate = true;
        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();

        initSwipeRefreshLayout();
        window = new NetProgressWindowDialog(mContext);
        rvBabyVideo = (RecyclerView) mView.findViewById(R.id.rv_baby_video);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBabyVideo.setLayoutManager(linearLayoutManager);
        mVideoArray = new ArrayList<>();
        mVideoRecycleAdapter = new VideoRecycleAdapter(this);
        rvBabyVideo.setAdapter(mVideoRecycleAdapter);
    }

    public static String[] appArrays;

    @Override
    public void setData() {
        super.setData();
//        JSONArray aa = Tools.getModuleJsonArray("live");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isCreate&&isVisible) {
            getData(1);
        }
    }

    @Override
    public void setAction() {
        super.setAction();
    }


    public void getData(final int Type) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqLiveBean("16", SysConfig.uid, SysConfig.token, "" + pageNo, "" + pageSize, "babylive").ToJsonString();
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
                    totalCount = jsonObject.getInt("totalCount");
                    if (result >= 1) {
                        ResLiveBean temp = new Gson().fromJson(t, ResLiveBean.class);
                        switch (Type) {
                            case 1:
                                swipeRefreshLayout.finishRefresh();
                                mVideoRecycleAdapter.setDatas(temp.getList());
                                break;
                            case 2:
                                swipeRefreshLayout.finishLoadmore();
                                mVideoRecycleAdapter.addDatas(temp.getList());
                                break;
                        }
                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show(); swipeRefreshLayout.finishRefresh();
                    swipeRefreshLayout.finishLoadmore();

                }
                window.closeWindow();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                swipeRefreshLayout.finishRefresh();
                swipeRefreshLayout.finishLoadmore();
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
                window.closeWindow();
            }
        });


    }


    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SHSwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.refresh_view, null);
        final TextView textView = (TextView) view.findViewById(R.id.title);
        swipeRefreshLayout.setFooterView(view);
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getData(1);
            }

            @Override
            public void onLoading() {

                if (pageNo * pageSize < totalCount) {
                    pageNo++;
                    getData(2);
                } else {
                    swipeRefreshLayout.finishLoadmore();
                }
            }

            /**
             * 监听下拉刷新过程中的状态改变
             * @param percent 当前下拉距离的百分比（0-1）
             * @param state 分三种状态{NOT_OVER_TRIGGER_POINT：还未到触发下拉刷新的距离；OVER_TRIGGER_POINT：已经到触发下拉刷新的距离；START：正在下拉刷新}
             */
            @Override
            public void onRefreshPulStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText("");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText("");
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setLoaderViewText("");
                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        textView.setText("上拉加载");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        textView.setText("松开加载");
                        break;
                    case SHSwipeRefreshLayout.START:
                        textView.setText("正在加载...");
                        break;
                }
            }
        });
    }


}

package com.m086.ad.mobi.view.ads;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;
import com.m086.ad.mobi.bean.request.ReqLiveBean;
import com.m086.ad.mobi.bean.request.SentAdsRe;
import com.m086.ad.mobi.bean.response.ResAdsBean;
import com.m086.ad.mobi.bean.response.ResLiveBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.view.ads.adapter.LeftRecycleAdapter;
import com.m086.ad.mobi.view.ads.adapter.RightAdapter;
import com.m086.ad.mobi.view.dynamics.SendDynamicActivity;
import com.m086.ad.mobi.view.living.adapter.VideoRecycleAdapter;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;
import java.util.List;


public class LeftFragment extends BaseFragment {

    private static int Fresh = 1;
    private static int LoadMore = 2;
    private NetProgressWindowDialog window;
    private View mView;

    private RecyclerView rvBabyVideo;
    private SHSwipeRefreshLayout swipeRefreshLayout;

    private LeftRecycleAdapter mVideoRecycleAdapter;
    List<ResLiveBean.LiveBean> mVideoArray;
    private boolean isVisible = false;

    private int pageSize = 12;
    private int pageNo = 1;
    private int totalCount;


    public LeftFragment() {

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
        window = new NetProgressWindowDialog(mBaseActivity);
        rvBabyVideo = (RecyclerView) mView.findViewById(R.id.rv_baby_video);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBabyVideo.setLayoutManager(linearLayoutManager);
        mVideoArray = new ArrayList<>();
        mVideoRecycleAdapter = new LeftRecycleAdapter(this);
        rvBabyVideo.setAdapter(mVideoRecycleAdapter);
        mVideoRecycleAdapter.setOnDeleteClickListener(new LeftRecycleAdapter.OnDeleteClickListener() {
            @Override
            public void onItemClick(int position, ResAdsBean.ListBean data) {
                postSentAds(SentAdsRe.TYPEID_Del, data, position);
            }
        });
        mVideoRecycleAdapter.setmOnEffectiveClincklistener(new LeftRecycleAdapter.onEffectiveClincklistener() {
            @Override
            public void onItemClick(int position, ResAdsBean.ListBean data) {
                postSentAds(SentAdsRe.TYPEID_Eff, data, position);
            }
        });
    }


    @Override
    public void setData() {
        super.setData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;

    }

    @Override
    public void setAction() {
        super.setAction();
        getActivity().findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AddAdsActivity.class));
                activityAnimation(RIGHT_IN);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
            getData(1);


    }

    public void getData(final int Type) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqLiveBean("50", SysConfig.uid, SysConfig.token, "" + pageNo, "" + pageSize, "").ToJsonString();
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
                    JSONArray mJsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0, size = mJsonArray.length(); i < size; i++) {

                    }
                    totalCount = jsonObject.getInt("totalCount");
                    if (result >= 1) {
                        ResAdsBean temp = new Gson().fromJson(t, ResAdsBean.class);
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
                        AppTools.jumptoLogin(mBaseActivity, result);
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.finishRefresh();
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

    public void postSentAds(final String type, final ResAdsBean.ListBean bean, final int position) {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new SentAdsRe("51", SysConfig.uid, SysConfig.token, type, bean.getAdid(), bean.getContent(), bean.getPic()).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {
                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        if (type.equals(SentAdsRe.TYPEID_Del)) {
                            mVideoRecycleAdapter.removeAds(position);
                        } else {
                            bean.setStatus("1");
                            mVideoRecycleAdapter.notifyItemChanged(position, bean);

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

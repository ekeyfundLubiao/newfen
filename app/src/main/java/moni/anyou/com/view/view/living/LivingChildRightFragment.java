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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

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
    private int pageSize = 12;
    private int pageNo = 1;
    private View mView;
    private PtrClassicDefaultHeader header;
    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView listview;
    int totalCount = 0;
    private VideoPublicAdapter publicAdapter;
    private boolean isVisible = false;
    ArrayList<VideoBean> mVideoArray;
    SHSwipeRefreshLayout swipeRefreshLayout;

    public LivingChildRightFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_living_right, container, false);

        init(mView);
        isCreate = true;
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        window = new NetProgressWindowDialog(mContext);
        mVideoArray = new ArrayList<>();
        listview = (RecyclerView) mView.findViewById(R.id.listview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        listview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
        listview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        listview.setLayoutManager(gridLayoutManager);
        initSwipeRefreshLayout();
//
//        header = new PtrClassicDefaultHeader (mBaseActivity);
//        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
//        ptrFrame = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_list_view_frame);
//        ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
//        ptrFrame.setHeaderView(header);
//        ptrFrame.addPtrUIHandler(header);

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
            getData(1);
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isCreate) {
            getData(1);
        }
    }

    @Override
    public void setAction() {
        super.setAction();

//        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
//            @Override
//            public void onLoadMoreBegin(final PtrFrameLayout frame) {
//                if (totalCount > pageNo * pageSize) {
//                    pageNo++;
//                    getData(2);
//                    ptrFrame.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            frame.refreshComplete();
//                        }
//                    }, 1000);
//                } else {
//                    frame.refreshComplete();
//                    ptrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
//                }
//
//            }
//
//            @Override
//            public void onRefreshBegin(final PtrFrameLayout frame) {
//                pageNo = 1;
//                getData(1);
//                ptrFrame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        frame.refreshComplete();
//
//                    }
//                }, 1000);
//            }
//        });

        // the following are default settings
//        ptrFrame.setResistance(1.7f);
//        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
//        ptrFrame.setDurationToClose(200);
//        ptrFrame.setDurationToCloseHeader(1000);
//        // default is false
//        ptrFrame.setPullToRefresh(true);
//        // default is true
//        ptrFrame.setKeepHeaderWhenRefresh(true);
//        ptrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                ptrFrame.autoRefresh();
//            }
//        }, 100);

    }


    public void getData(final int Type) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqLiveBean("16", SysConfig.uid, SysConfig.token, "" + pageNo, "" + pageSize, "publiclive").ToJsonString();
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
                        ResLiveBean temp = new Gson().fromJson(t, ResLiveBean.class);
                        totalCount = temp.getTotalCount();
                        switch (Type) {
                            case 1:
                                swipeRefreshLayout.finishRefresh();
                                publicAdapter.setDatas(temp.getList());
                                break;
                            case 2:
                                swipeRefreshLayout.finishLoadmore();
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
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.finishRefresh();
                        Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1600);
            }

            @Override
            public void onLoading() {

                if (totalCount > pageNo * pageSize) {
                    pageNo++;
                    getData(2);

                }
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.finishLoadmore();
                        Toast.makeText(mContext, "加载完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1600);
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

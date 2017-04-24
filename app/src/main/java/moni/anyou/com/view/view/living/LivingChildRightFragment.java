package moni.anyou.com.view.view.living;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.bean.request.ReqLiveBean;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.view.living.adapter.SpacesItemDecoration;
import moni.anyou.com.view.view.living.adapter.VideoPublicAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;


public class LivingChildRightFragment extends BaseFragment {


    private static int Fresh = 1;
    private static int LoadMore = 2;
    private NetProgressWindowDialog window;
    private int pageSize = 12;
    private int pageNo = 1;
    private View mView;
    //    private PtrClassicDefaultHeader header;
//    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView listview;
    private SHSwipeRefreshLayout swipeRefreshLayout;
    private int totalCout = 1;


    private VideoPublicAdapter publicAdapter;
    private boolean isVisible = false;
    ArrayList<VideoBean> mVideoArray;

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
        initSwipeRefreshLayout();
        window = new NetProgressWindowDialog(mBaseActivity);
        mVideoArray = new ArrayList<>();
        initRvContent();
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
    }

    String[] picArry = {"http://img07.tooopen.com/images/20170316/tooopen_sy_202028912158.jpg",
            "http://img04.tooopen.com/images/20121109/tooopen_201211091418005900.jpg", "http://img04.tooopen.com/images/20130617/tooopen_21382885.jpg",
            "http://img03.tooopen.com/images/20130509/tooopen_09413727.jpg",
            "http://img04.tooopen.com/images/20130417/tooopen_15321807.jpg",
            "http://img04.tooopen.com/images/20130228/tooopen_12120276.jpg",
            "http://img07.tooopen.com/images/20170320/tooopen_sy_202527818519.jpg",
            "http://img06.tooopen.com/images/20161214/tooopen_sy_190570171299.jpg",
            "http://img06.tooopen.com/images/20161106/tooopen_sy_185050549459.jpg",
            "http://img06.tooopen.com/images/20161101/tooopen_sy_184377491944.jpg",
            "http://img06.tooopen.com/images/20160906/tooopen_sy_177992564428.jpg",
            "http://img06.tooopen.com/images/20160906/tooopen_sy_177973146222.jpg"
            , "http://img06.tooopen.com/images/20160710/tooopen_sy_169923037699.jpg"};

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
                    totalCout = jsonObject.getInt("totalCount");
                    if (result >= 1) {
                        ResLiveBean temp = new Gson().fromJson(t, ResLiveBean.class);
                        switch (Type) {
                            case 1:
                                swipeRefreshLayout.finishRefresh();
                                ArrayList<ResLiveBean.LiveBean> Arrylist = new ArrayList<ResLiveBean.LiveBean>();
                                for (int i = 0; i < 12; i++) {
                                    ResLiveBean.LiveBean tempBean = new ResLiveBean.LiveBean();
                                    tempBean.pic = picArry[i];
                                    tempBean.onlinenum = "" + i;
                                    tempBean.status = i / 2;
                                    tempBean.liveName = "跳跳蛙" + i;
                                    Arrylist.add(tempBean);
                                }
                                publicAdapter.setDatas(Arrylist);
//                                publicAdapter.setDatas(temp.getList());
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
            }

            @Override
            public void onLoading() {

                if (pageNo * pageSize < totalCout) {
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


    private RecyclerView.LayoutManager mLayoutManager;

    private void initRvContent() {


        listview = (RecyclerView) mView.findViewById(R.id.listview);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
//        listview.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.HORIZONTAL));
//        listview.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
//        listview.setLayoutManager(gridLayoutManager);
        mLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        listview.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(5);
        int topBottom = dip2px(5);

        listview.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom,getResources().getColor(R.color.white)));
        //    rv_content.addItemDecoration(new SpacesItemDecoration(dip2px(1), dip2px(1), Color.BLUE));

    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }


}

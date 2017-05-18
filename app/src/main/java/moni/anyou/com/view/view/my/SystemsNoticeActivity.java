package moni.anyou.com.view.view.my;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.bean.response.ResNoticeData;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.AppTools;
import moni.anyou.com.view.view.my.adapter.NoticeItemslAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;


public class SystemsNoticeActivity extends BaseActivity {

    private NetProgressWindowDialog window;
    private int pageSize = 12;
    private int pageNo = 0;
    private NoticeItemslAdapter mNoticeItemslAdapter;
    private RecyclerView lv_notice;
    private SHSwipeRefreshLayout swipeRefreshLayout;
    private int totalCout = 0;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systems_notice);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        window = new NetProgressWindowDialog(mBaseActivity);
        initTitle();
        tvTitle.setText("系统公告");
        mNoticeItemslAdapter = new NoticeItemslAdapter(this);
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        postgetNotice();

    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
                activityAnimation(RIGHT_OUT);
            }
        });


    }

    public void postgetNotice() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqPageBean("8", SysConfig.uid, SysConfig.token, "" + pageNo, "" + pageSize).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {
                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);

                    int result = Integer.parseInt(jsonObject.getString("result"));
                    totalCout = jsonObject.getInt("totalCount");
                    if (result >= 1) {
                        if (pageNo > 1) {

                            mNoticeItemslAdapter.addDatas(new Gson().fromJson(t, ResNoticeData.class).getList());
                        } else {
                            swipeRefreshLayout.finishRefresh();
                            mNoticeItemslAdapter.setDatas(new Gson().fromJson(t, ResNoticeData.class).getList());
                        }

                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.finishRefresh();
                        AppTools.jumptoLogin(mBaseActivity, result);
                    }
                } catch (Exception ex) {
                    swipeRefreshLayout.finishRefresh();
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
        swipeRefreshLayout = (SHSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        final View view = inflater.inflate(R.layout.refresh_view, null);
        final TextView textView = (TextView) view.findViewById(R.id.title);
        swipeRefreshLayout.setFooterView(view);
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                postgetNotice();
//                swipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.finishRefresh();
//                        Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
//                    }
//                }, 1600);
            }

            @Override
            public void onLoading() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pageNo * pageSize < totalCout) {
                            pageNo++;
                            postgetNotice();
                        }
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


    private void initRecyclerView() {
        lv_notice = (RecyclerView) findViewById(R.id.lv_notice);
        lv_notice.setLayoutManager(new LinearLayoutManager(this));
        lv_notice.setAdapter(mNoticeItemslAdapter);
        lv_notice.setItemAnimator(new DefaultItemAnimator());
        lv_notice.addItemDecoration(new DividerRVDecoration(this,
                DividerRVDecoration.VERTICAL_LIST));
    }


}

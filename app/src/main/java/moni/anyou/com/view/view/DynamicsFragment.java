package moni.anyou.com.view.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.BaseInfo;
import moni.anyou.com.view.bean.DynamicsTempItems;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.request.ReqLiveBean;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.bean.request.ReqSentDynamicsBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.AppTools;
import moni.anyou.com.view.view.dynamics.SendDynamicActivity;
import moni.anyou.com.view.view.dynamics.adapter.DynamicsItemAdapter;
import moni.anyou.com.view.view.dynamics.adapter.DynamicsItemsAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.NoListview;
import moni.anyou.com.view.widget.recycleview.MyRecycleView;


public class DynamicsFragment extends BaseFragment implements View.OnClickListener {
    private static int Fresh = 1;
    private static int LoadMore = 2;
    private View mView;
    private NetProgressWindowDialog window;
    private CircleImageView cvHeadIcon;
    private TextView tvLeft;
    private TextView tvTitle;
    private ImageView iv_icon;
    private ImageView ivRight;
    private MyRecycleView lvDynamics;
    private SHSwipeRefreshLayout swipeRefreshLayout;

    private DynamicsItemsAdapter dynamicsItemAdapter;

    private List<DynamicsTempItems> mItems;
    private int pageSize = 5;
    private int pageNo = 1;
    int totalCount = 0;


    public DynamicsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamics, container, false);
        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        initSwipeRefreshLayout();
        window = new NetProgressWindowDialog(mContext);
        tvLeft = (TextView) mView.findViewById(R.id.tv_left);
        tvTitle = (TextView) mView.findViewById(R.id.page_title);
        iv_icon = (ImageView) mView.findViewById(R.id.iv_icon);
        ivRight = (ImageView) mView.findViewById(R.id.right_tv);


        tvTitle.setText("动态");
        lvDynamics = (MyRecycleView) mView.findViewById(R.id.lv_dynamics);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvDynamics.setLayoutManager(linearLayoutManager);
        cvHeadIcon = (CircleImageView) mView.findViewById(R.id.civ_headIcon);
        dynamicsItemAdapter = new DynamicsItemsAdapter(this);
        lvDynamics.setAdapter(dynamicsItemAdapter);
        mItems = new ArrayList<>();

    }

    @Override
    public void setAction() {
        super.setAction();
        ivRight.setOnClickListener(this);
    }

    public void marklike(int position, ResDynamicsBean.ListBean bean) {
        postLikeArticle(position, bean);
    }

    @Override
    public void setData() {
        super.setData();
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
        setBitmaptoImageView11(SysConfig.PicUrl + baseInfo.icon, cvHeadIcon);
        getData();
    }

    public void getData() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqPageBean("17", SysConfig.uid, SysConfig.token, "" + pageNo, "" + pageSize).ToJsonString();
        params.put("sendMsg", cmdPara);

        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    totalCount = jsonObject.getInt("totalCount");
                    if (result >= 1) {
                        ResDynamicsBean temp = new Gson().fromJson(t, ResDynamicsBean.class);
                        if (pageNo > 1) {
                            dynamicsItemAdapter.AddDatas(temp.getList());
                            swipeRefreshLayout.finishLoadmore();
                        } else {
                            swipeRefreshLayout.finishRefresh();
                            dynamicsItemAdapter.setDatas(temp.getList());
                        }


                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();

            }
        });


    }

    public void postLikeArticle(final int position, final ResDynamicsBean.ListBean bean) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsLikeTeacherBean("15", SysConfig.uid, SysConfig.token, bean.getArticleid(), "article").ToJsonString();
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
                        bean.setLikeuser(AppTools.likeUsers(bean));
                        dynamicsItemAdapter.notifyItemChanged(position, bean);
                        // getData();
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                startActivity(new Intent(mContext, SendDynamicActivity.class));
                activityAnimation(RIGHT_IN);
                break;
        }
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
                getData();
            }

            @Override
            public void onLoading() {

                if (pageNo * pageSize < totalCount) {
                    pageNo++;
                    getData();
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


    public void postDeleteDynamics(ResDynamicsBean.ListBean bean, final int position) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqSentDynamicsBean("18", SysConfig.uid, SysConfig.token, ReqSentDynamicsBean.TYPEID_DELETE, bean.getArticleid(), bean.getContent(), bean.getPic()).ToJsonString();
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
                        dynamicsItemAdapter.removeDynamics(position);
                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show();
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

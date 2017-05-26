package com.m086.ad.mobi.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;
import com.m086.ad.mobi.bean.BaseInfo;
import com.m086.ad.mobi.bean.DynamicsTempItems;
import com.m086.ad.mobi.bean.request.ReqDynamicsCommentBean;
import com.m086.ad.mobi.bean.request.ReqPageBean;
import com.m086.ad.mobi.bean.request.ReqSentDynamicsBean;
import com.m086.ad.mobi.bean.request.ReqsLikeTeacherBean;
import com.m086.ad.mobi.bean.response.ResDynamicsBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.tool.KeyBoardTools;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.view.dynamics.SendDynamicActivity;
import com.m086.ad.mobi.view.dynamics.adapter.DynamicsItemsAdapter;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;
import com.m086.ad.mobi.widget.dialog.PopCommentSent;
import com.m086.ad.mobi.widget.recycleview.MyRecycleView;


public class DynamicsFragment extends BaseFragment implements View.OnClickListener {
    private static int Fresh = 1;
    private static int LoadMore = 2;
    private View mView;
    private NetProgressWindowDialog window;
    private TextView tvLeft;
    private TextView tvTitle;
    private ImageView iv_icon;
    private ImageView ivRight;
    private MyRecycleView lvDynamics;
    private SHSwipeRefreshLayout swipeRefreshLayout;

    private DynamicsItemsAdapter dynamicsItemAdapter;
    private PopCommentSent mPopCommentSent;
    private List<DynamicsTempItems> mItems;
    private int pageSize = 5;
    private int pageNo = 1;
    int totalCount = 0;
    BaseInfo mBaseInfo;

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
        window = new NetProgressWindowDialog(mBaseActivity);
        mPopCommentSent = new PopCommentSent(mBaseActivity, this);
        tvLeft = (TextView) mView.findViewById(R.id.tv_left);
        tvTitle = (TextView) mView.findViewById(R.id.page_title);
        iv_icon = (ImageView) mView.findViewById(R.id.iv_icon);
        ivRight = (ImageView) mView.findViewById(R.id.right_tv);


        tvTitle.setText("动态");
        lvDynamics = (MyRecycleView) mView.findViewById(R.id.lv_dynamics);


        mItems = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvDynamics.setLayoutManager(linearLayoutManager);
        dynamicsItemAdapter = new DynamicsItemsAdapter(this);
        lvDynamics.setAdapter(dynamicsItemAdapter);
    }

    @Override
    public void setAction() {
        super.setAction();
        ivRight.setOnClickListener(this);
        dynamicsItemAdapter.setComentClickListener(new DynamicsItemsAdapter.ComentClickListener() {
            @Override
            public void onItemClick(int position, ResDynamicsBean.ListBean data) {
                ToastTools.showShort(mContext, "" + position);
                mPopCommentSent.showAtLocation(mView.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
                mPopCommentSent.position = position;
                mPopCommentSent.data = data;
                mPopCommentSent.isShowing();
                KeyBoardTools.openKeybord(mPopCommentSent.etComments, mContext);

            }
        });
        dynamicsItemAdapter.setOnDeleteClickListener(new DynamicsItemsAdapter.OnDeleteClickListener() {
            @Override
            public void onItemClick(int position, ResDynamicsBean.ListBean data) {
                postDeleteDynamics(data, position);
            }
        });
        dynamicsItemAdapter.setIPraiseClickListener(new DynamicsItemsAdapter.IPraiseClickListener() {
            @Override
            public void onItemClick(int position, ResDynamicsBean.ListBean data) {
                postLikeArticle(position, data);
            }
        });

    }


    @Override
    public void setData() {
        super.setData();
        try {
            mBaseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
        } catch (Exception e) {

        }

    }

    @Override
    public void onResume() {
        super.onResume();


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
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    totalCount = jsonObject.getInt("totalCount");
                    if (result >= 1) {
                        ResDynamicsBean temp = new Gson().fromJson(t, ResDynamicsBean.class);
                        if (pageNo > 1) {
                            dynamicsItemAdapter.AddDatas(AppTools.getDynamicsItemBean(temp));
                            swipeRefreshLayout.finishLoadmore();
                        } else {
                            swipeRefreshLayout.finishRefresh();
                            List<ResDynamicsBean.ListBean> bean = new ArrayList<ResDynamicsBean.ListBean>();
                            bean.add(new ResDynamicsBean.ListBean());
                            bean.addAll(AppTools.getDynamicsItemBean(temp));
                            dynamicsItemAdapter.setDatas(bean);
                        }


                    } else {
                        AppTools.jumptoLogin(mBaseActivity, result);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                startActivity(new Intent(mContext, SendDynamicActivity.class));
                activityAnimation(RIGHT_IN);
                break;
            case R.id.btn_pop_sent:
                postAddCommentDynamics(mPopCommentSent.data, mPopCommentSent.position);

                KeyBoardTools.closeKeybord(mPopCommentSent.etComments, mContext);
                mPopCommentSent.backgroundAlpha(1f);
                mPopCommentSent.dismiss();
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
                    ToastTools.showShort(mContext, "没有更多了");
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
        String cmdPara = new ReqSentDynamicsBean("18", SysConfig.uid, SysConfig.token, ReqSentDynamicsBean.TYPEID_DELETE, bean.articleid, bean.content, bean.pic).ToJsonString();
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
                        AppTools.jumptoLogin(mBaseActivity, result);
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


    //添加评论
    public void postAddCommentDynamics(final ResDynamicsBean.ListBean bean, final int position) {


        if (TextUtils.isEmpty(mPopCommentSent.etComments.getText())) {
            ToastTools.showShort(mContext, "评论内容不能为空");
        } else {


            KJHttp kjh = new KJHttp();
            KJStringParams params = new KJStringParams();
            String cmdPara = new ReqDynamicsCommentBean("25", SysConfig.uid, SysConfig.token, bean.articleid, mPopCommentSent.etComments.getText().toString()).ToJsonString();
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
                            String tempstr = mPopCommentSent.etComments.getText().toString();
                            bean.commentList.add(new ResDynamicsBean.CommentListBean(tempstr, mBaseInfo.nick));
                            mPopCommentSent.etComments.setText("");
                            dynamicsItemAdapter.notifyItemChanged(position, bean);
                        } else {
                            AppTools.jumptoLogin(mBaseActivity, result);
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

    public void postLikeArticle(final int position, final ResDynamicsBean.ListBean bean) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsLikeTeacherBean("15", SysConfig.uid, SysConfig.token, bean.articleid, "article").ToJsonString();
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
                        bean.likeuser = AppTools.likeUsers(bean);
                        dynamicsItemAdapter.notifyItemChanged(position, bean);
                    } else {
                        AppTools.jumptoLogin(mBaseActivity, result);
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

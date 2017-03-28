package moni.anyou.com.view.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import org.json.JSONObject;
import org.kymjs.aframe.database.utils.Id;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.Banner;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.RecycleViewBean;
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.bean.request.ReqCompleteFInishBabyInfoBean;
import moni.anyou.com.view.bean.request.ReqHomeBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.KindergardenImageTextAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.RecyclerViewAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.TeacherShowAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.picshow.NewsPicDetaiActivity;
import moni.anyou.com.view.view.web.ShowInfoActivity;
import moni.anyou.com.view.webview.ShowWebActivity;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.banner.AutoScrollViewPager;


public class KindergartenFragment extends BaseFragment {
    private AutoScrollViewPager bannerView;
    private View mView;
    private LinearLayout mTextLayout;
    private RecyclerView rvImages;
    private RecyclerView rvTeachShow;
    private TextView tv_intro_content;
    private TextView tv_Address;
    private TextView tv_tellPhoe;
    private TextView tv_shcoolIntr;

    private RecyclerViewAdapter myAdpter;
    private TeacherShowAdapter showTeacherAdapter;
    //    PtrClassicFrameLayout root;
    SHSwipeRefreshLayout swipeRefreshLayout;
    ResHomeData resHomeData;
    private NetProgressWindowDialog window;
    List<ResHomeData.TopTeachersBean> mItems = new ArrayList<>();
    ResHomeData.CompanyInfoBean mCompanyInfoBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_kindergarten, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void setAction() {
        super.setAction();
        tv_shcoolIntr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("title", mCompanyInfoBean.getCompany());
                intent.putExtra("url", mCompanyInfoBean.getUrl());
                intent.setClass(mContext, ShowWebActivity.class);
                startActivity(intent);
                activityAnimation(RIGHT_IN);

            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        initTitle(mView);
        window = new NetProgressWindowDialog(mContext);
        ivBack.setVisibility(View.GONE);
//        root = (PtrClassicFrameLayout) mView.findViewById(R.id.root);
//        root.disableWhenHorizontalMove(true);
        bannerView = (AutoScrollViewPager) mView.findViewById(R.id.bannerView);
        mTextLayout = (LinearLayout) mView.findViewById(R.id.layout_index_points);
        rvImages = (RecyclerView) mView.findViewById(R.id.rc_image);
        tv_Address = (TextView) mView.findViewById(R.id.tv_Address);
        tv_intro_content = (TextView) mView.findViewById(R.id.tv_intro_content);
        tv_tellPhoe = (TextView) mView.findViewById(R.id.tv_tellPhoe);
        tv_shcoolIntr = (TextView) mView.findViewById(R.id.tv_shcoolIntr);
        rvTeachShow = (RecyclerView) mView.findViewById(R.id.rv_teach_show);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);

        initRefresh();
        //  initSwipeRefreshLayout();
        initShowTeacherPic();
//        root.autoRefresh();

    }

    @Override
    public void setData() {
        super.setData();
        getdata(-1);

    }

    private void initShowTeacherPic() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTeachShow.setLayoutManager(linearLayoutManager);

    }


    private void initRefresh() {
//        root.setMode(PtrFrameLayout.Mode.REFRESH);
//        root.setPtrHandler(new PtrDefaultHandler2() {
//            @Override
//            public void onLoadMoreBegin(final PtrFrameLayout frame) {
//                // getdata(frame);
//                root.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //  mVideoRecycleAdapter.notifyDataSetChanged();
//                        frame.refreshComplete();
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public void onRefreshBegin(final PtrFrameLayout frame) {
//
//                getdata();
//                root.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // mVideoRecycleAdapter.setDatas(mVideoArray);
//                        frame.refreshComplete();
//                    }
//                }, 1000);
//            }
//        });
    }

    //点赞
    public void likesPost(ResHomeData.TopTeachersBean teacher, int position) {
        postLikeTeacher(teacher, position);

    }

    //教师详情
    public void teacherDetailWebView(ResHomeData.TopTeachersBean teacher) {
        ToastTools.showShort(this.getContext(), "位置" + teacher.getNick());
        Intent intent = new Intent();
        intent.putExtra("title", teacher.getNick());
        intent.putExtra("url", mCompanyInfoBean.getUrl());
        intent.setClass(mContext, ShowInfoActivity.class);
        startActivity(intent);
        activityAnimation(RIGHT_IN);
    }

    /**
     * @param type 1 获取新数据  2 点赞数据
     */
    public void getdata(final int type) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        final String cmdPara = new ReqHomeBean("14", SysConfig.uid, SysConfig.token).ToJsonString();
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
                        resHomeData = new Gson().fromJson(t, ResHomeData.class);
                        switch (type) {
                            case -1:
                                setPostData();
                                break;
                            default:
                                resHomeData.getTopTeachers().get(type);
                                showTeacherAdapter.replace(resHomeData.getTopTeachers().get(type), type);
                                break;
                        }

                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    window.closeWindow();
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


    private void setPostData() {
        if (resHomeData.getTopNews() != null && resHomeData.getTopNews().size() > 0) {
            ArrayList<ResHomeData.TopNewsBean> imgList = (ArrayList<ResHomeData.TopNewsBean>) resHomeData.getTopNews();

            if ((null != imgList) && (imgList.size() > 0)) {
                KindergardenImageTextAdapter adapter = new KindergardenImageTextAdapter(
                        this);
                adapter.setDatas(imgList);
                bannerView.setAdapter(adapter);
                bannerView.setPageMargin(0);
                bannerView.setInterval(1500);
                bannerView.startAutoScroll();
            }
        }
        if (resHomeData.getTopTeachers() != null && resHomeData.getTopTeachers().size() > 0) {
            showTeacherAdapter = new TeacherShowAdapter(this, resHomeData.getTopTeachers());
            rvTeachShow.setAdapter(showTeacherAdapter);
            showTeacherAdapter.notifyDataSetChanged();
        }
        if (resHomeData.getCompanyInfo() != null && resHomeData.getCompanyInfo().size() > 0) {
            mCompanyInfoBean = resHomeData.getCompanyInfo().get(0);
            tv_Address.setText("地址：     " + mCompanyInfoBean.getAddress());
            tv_tellPhoe.setText("电话：     " + mCompanyInfoBean.getTel());
            tv_intro_content.setText(mCompanyInfoBean.getSummary());
            tvTitle.setText(mCompanyInfoBean.getCompany());
        }
    }


    public void postLikeTeacher(final ResHomeData.TopTeachersBean teacher, final int position) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsLikeTeacherBean("15", SysConfig.uid, SysConfig.token, teacher.getUser_id(), "user").ToJsonString();
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
                        getdata(position);
                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();
                    window.closeWindow();
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
        swipeRefreshLayout.setLoadmoreEnable(false);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.refresh_view, null);
        final TextView textView = (TextView) view.findViewById(R.id.title);
        swipeRefreshLayout.setFooterView(view);
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoading() {
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

            }
        });
    }

}

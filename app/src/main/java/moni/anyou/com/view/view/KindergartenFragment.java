package moni.anyou.com.view.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.Banner;
import moni.anyou.com.view.bean.RecycleViewBean;
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.KindergardenImageTextAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.RecyclerViewAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.TeacherShowAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.picshow.NewsPicDetaiActivity;
import moni.anyou.com.view.widget.banner.AutoScrollViewPager;


public class KindergartenFragment extends BaseFragment {
    private AutoScrollViewPager bannerView;
    private View mView;
    private LinearLayout mTextLayout;
    private RecyclerView rvImages;
    private RecyclerView rvTeachShow;
    private RecyclerViewAdapter myAdpter;
    private TeacherShowAdapter showTeacherAdapter;
    PtrClassicFrameLayout root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_kindergarten, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        initTitle(mView);
        ivBack.setVisibility(View.GONE);
        root = (PtrClassicFrameLayout) mView.findViewById(R.id.root);
        bannerView = (AutoScrollViewPager) mView.findViewById(R.id.bannerView);
        mTextLayout = (LinearLayout) mView.findViewById(R.id.layout_index_points);
        rvImages = (RecyclerView) mView.findViewById(R.id.rc_image);
        rvTeachShow = (RecyclerView) mView.findViewById(R.id.rv_teach_show);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);

        String[] titileArray = {"巴基斯坦疑似发生针对中企恐袭事件", "台媒：蔡英文明年将过境美国 与特朗普幕僚会面", "·外媒:IS计划在特朗普就职日发动大规模恐袭",
                "北京一货运航班起飞后起火 65架飞机紧急避让", "2016年城市服务业高薪榜出炉 按摩师月薪最高", "湖北一煤矿发生瓦斯突出事故 11人被困井下", "的哥察觉乘客有杀气 机智抓捕杀父逆子"};

        String[] imageArray = {"http://img0.imgtn.bdimg.com/it/u=106235241,3970903275&fm=21&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2768400507,729312347&fm=21&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=902535720,3690912714&fm=21&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=3788092477,1980763064&fm=21&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1789681754,2625846600&fm=21&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=2635520879,3977805749&fm=21&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3005530274,2015303717&fm=21&gp=0.jpg"};
        ArrayList<Banner> banners = new ArrayList<Banner>();
        for (int i = 0; i < 7; i++) {
            Banner temp = new Banner();
            temp.bannerUrl = imageArray[i];
            temp.linkUrl = "";
            temp.title = titileArray[i];
            temp.sizeMark = i + 1 + "/7";
            banners.add(temp);
        }
        setCycleImg(banners);
        initRefresh();
        initShowShool();
        initShowTeacherPic();


    }


    // 下载轮播图
    public void setCycleImg(final List<Banner> imgList) {
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


    private void initShowTeacherPic() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTeachShow.setLayoutManager(linearLayoutManager);
        String[] appArrays = {"http://awb.img.xmtbang.com/img/uploadnew/201510/23/c12aaf66288841d08f911dd32fbc32b1.jpg",
                "http://awb.img.xmtbang.com/img/uploadnew/201510/24/375c566f37d547f2a4f70a40f8fa442a.jpg",
                "http://v1.qzone.cc/avatar/201508/25/10/32/55dbd3c6b091b869.jpg%21200x200.jpg",
                "http://t0.qlogo.cn/mbloghead/86b301cf0566cce45ebc/180",};
        ArrayList<RecycleViewBean> teacherBeens = new ArrayList<RecycleViewBean>();
        for (int i = 0; i < 4; i++) {
            RecycleViewBean temps = new RecycleViewBean();
            temps.Url = appArrays[i];
            temps.teachearName = "小小华" + i;
            temps.start = "" + (i + 1) * 22;
            teacherBeens.add(temps);
        }
        showTeacherAdapter = new TeacherShowAdapter(this, teacherBeens);
        rvTeachShow.setAdapter(showTeacherAdapter);
        showTeacherAdapter.notifyDataSetChanged();
    }

    private void initShowShool() {
        String[] appArrays = {"http://pic35.nipic.com/20131114/3420027_125936007395_2.jpg",
                "http://pic35.nipic.com/20131113/3420027_175250054388_2.jpg",
                "http://pic35.nipic.com/20131113/3420027_180140002331_2.jpg",
                "http://pic35.nipic.com/20131114/3420027_125936007395_2.jpg",
                "http://pic35.nipic.com/20131113/3420027_180140002331_2.jpg",
                "http://pic35.nipic.com/20131113/3420027_175250054388_2.jpg",
                "http://pic35.nipic.com/20131114/3420027_125936007395_2.jpg"};
        ArrayList<RecycleViewBean> recycleViewBeens = new ArrayList<RecycleViewBean>();
        for (int i = 0; i < 6; i++) {
            RecycleViewBean temps = new RecycleViewBean();
            temps.Url = appArrays[i];
            recycleViewBeens.add(temps);
        }
        myAdpter = new RecyclerViewAdapter(this, recycleViewBeens);
        rvImages.setAdapter(myAdpter);
        myAdpter.notifyDataSetChanged();
        myAdpter.setmOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, RecycleViewBean data) {
                startActivity(new Intent(mContext, NewsPicDetaiActivity.class));
                activityAnimation(RIGHT_IN);
            }
        });
    }

    private void initRefresh() {
        root.setMode(PtrFrameLayout.Mode.REFRESH);
        root.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {

                root.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  mVideoRecycleAdapter.notifyDataSetChanged();
                        frame.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                // mVideoArray.clear();


                root.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // mVideoRecycleAdapter.setDatas(mVideoArray);
                        frame.refreshComplete();
                    }
                }, 1000);
            }
        });
    }
}

package moni.anyou.com.view.view.living;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
import moni.anyou.com.view.view.living.adapter.VideoRecycleAdapter;


public class LivingChildFragment extends BaseFragment {

    private View mView;
    private StoreHouseHeader header;
    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView rvBabyVideo;
    private VideoRecycleAdapter mVideoRecycleAdapter;
    ArrayList<VideoBean> mVideoArray;
    private boolean isVisible=false;
    public LivingChildFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_living_left, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();

        header = new StoreHouseHeader(mBaseActivity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        header.initWithString("yiquanziben");
        ptrFrame = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_list_view_frame);
        ptrFrame.setHeaderView(header);
        /* 设置回调 */
        ptrFrame.addPtrUIHandler(header);
        rvBabyVideo= (RecyclerView) mView.findViewById(R.id.rv_baby_video);

       // GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBabyVideo.setLayoutManager(linearLayoutManager);
//       rvBabyVideo.setOrientation(LinearLayoutManager.HORIZONTAL);
       // rvBabyVideo.setLayoutManager(gridLayoutManager);
        mVideoArray = new ArrayList<>();
        mVideoRecycleAdapter = new VideoRecycleAdapter(this,mVideoArray);
        rvBabyVideo.setAdapter(mVideoRecycleAdapter);
    }
    String[] appArrays;
    @Override
    public void setData() {
        super.setData();
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }
        });
       appArrays = new String[]{"http://pic35.nipic.com/20131114/3420027_125936007395_2.jpg",
               "http://pic35.nipic.com/20131113/3420027_175250054388_2.jpg",
               "http://pic35.nipic.com/20131113/3420027_180140002331_2.jpg",
               "http://pic35.nipic.com/20131114/3420027_125936007395_2.jpg",
               "http://pic35.nipic.com/20131113/3420027_180140002331_2.jpg",
               "http://pic35.nipic.com/20131113/3420027_175250054388_2.jpg",
               "http://pic35.nipic.com/20131114/3420027_125936007395_2.jpg"};

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
                for (int i=0;i<7;i++) {
                    mVideoArray.add(new VideoBean(appArrays[i], "" + 22 * i + 1, "终极" + i + "班"));
                }
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoRecycleAdapter.notifyDataSetChanged();
                        frame.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
               // mVideoArray.clear();
                for (int i=0;i<7;i++) {
                    mVideoArray.add(new VideoBean(appArrays[i], "" + 22 * i + 1, "终极" + i + "班"));
                }

                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoRecycleAdapter.notifyDataSetChanged();
                       // mVideoRecycleAdapter.setDatas(mVideoArray);
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
        ptrFrame.setPullToRefresh(false);
        // default is true
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {

                ptrFrame.autoRefresh();
            }
        }, 100);

    }
}

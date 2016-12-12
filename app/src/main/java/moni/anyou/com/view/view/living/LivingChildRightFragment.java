package moni.anyou.com.view.view.living;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.my.systemset.adapter.SettingItemslAdapter;
import moni.anyou.com.view.widget.NoListview;


public class LivingChildRightFragment extends BaseFragment {

    private View mView;
    private PtrClassicDefaultHeader  header;
    private PtrClassicFrameLayout ptrFrame;
    private ListView listview;
    private ArrayList<HomeItemBean> setItems;
    private SettingItemslAdapter myAdapter;
private  boolean isVisible=false;

    public LivingChildRightFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_living_right, container, false);

        init(mView);
        isCreate=true;
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        listview=(ListView) mView.findViewById(R.id.listview);
        header = new PtrClassicDefaultHeader (mBaseActivity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);

        ptrFrame = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_list_view_frame);
        ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);

    }

    @Override
    public void setData() {
        super.setData();
        setItems = new ArrayList<>();
        setItems.add(new HomeItemBean("清除缓存", "111.8M"));
        for (int i=0;i<23;i++) {
            setItems.add(new HomeItemBean("消息通知"+i, ""));
        }
        myAdapter = new SettingItemslAdapter(mBaseActivity);
        listview.setAdapter(myAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible && isCreate) {
            ptrFrame.autoRefresh();
        }

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
                ToastTools.showShort(mBaseActivity,"更多");
                setItems.add(new HomeItemBean("清除缓存", "111.8M"));
                for (int i=0;i<23;i++) {
                    setItems.add(new HomeItemBean("消息通知"+i, ""));
                }
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.setDatas(setItems);
                        frame.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                ToastTools.showShort(mBaseActivity,"刷新");
                setItems.clear();
                setItems.add(new HomeItemBean("清除缓存", "111.8M"));
                for (int i=0;i<23;i++) {
                    setItems.add(new HomeItemBean("消息通知"+i, ""));
                }
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                         myAdapter.setDatas(setItems);
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
        ptrFrame.setPullToRefresh(true);
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

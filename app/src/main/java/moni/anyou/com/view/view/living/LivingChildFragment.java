package moni.anyou.com.view.view.living;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;
import java.util.List;

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
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.bean.request.ReqLiveBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.living.adapter.VideoRecycleAdapter;
import moni.anyou.com.view.widget.NetProgressWindowDialog;


public class LivingChildFragment extends BaseFragment {

    private static int Fresh = 1;
    private static int LoadMore = 2;
    private NetProgressWindowDialog window;
    private View mView;
    private PtrClassicDefaultHeader  header;
    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView rvBabyVideo;
    private VideoRecycleAdapter mVideoRecycleAdapter;
    List<ResLiveBean.LiveBean> mVideoArray;
    private boolean isVisible=false;

    private int pageSize=12;
    private int  pageNo=1;

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
        window = new NetProgressWindowDialog(mContext);


        ptrFrame = (PtrClassicFrameLayout) mView.findViewById(R.id.rotate_header_list_view_frame);
        header = new PtrClassicDefaultHeader(mBaseActivity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptrFrame.addPtrUIHandler(header);
        rvBabyVideo= (RecyclerView) mView.findViewById(R.id.rv_baby_video);

       // GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBabyVideo.setLayoutManager(linearLayoutManager);
//       rvBabyVideo.setOrientation(LinearLayoutManager.HORIZONTAL);
       // rvBabyVideo.setLayoutManager(gridLayoutManager);
        mVideoArray = new ArrayList<>();
        mVideoRecycleAdapter = new VideoRecycleAdapter(this);
        rvBabyVideo.setAdapter(mVideoRecycleAdapter);
    }
    public  static String[] appArrays;
    @Override
    public void setData() {
        super.setData();
      // JSONArray aa= Tools.getModuleJsonArray("live");
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
                pageNo++;
                getData(LoadMore);
            }
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                pageNo = 1;
                getData(Fresh);


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



    public void getData( final int Type) {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqLiveBean("16", SysConfig.uid, SysConfig.token,""+pageNo,""+pageSize,"babylive").ToJsonString();
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
                        ResLiveBean temp= new Gson().fromJson(t, ResLiveBean.class);
                        int totalCound=temp.getTotalCount();
                        switch (Type) {
                            case 1:
                                mVideoRecycleAdapter.setDatas(temp.getList());
                                break;
                            case 2:
                                mVideoRecycleAdapter.addDatas(temp.getList());
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


}

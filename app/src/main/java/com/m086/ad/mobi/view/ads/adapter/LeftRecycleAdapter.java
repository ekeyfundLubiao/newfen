package com.m086.ad.mobi.view.ads.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;
import com.m086.ad.mobi.bean.response.ResAdsBean;
import com.m086.ad.mobi.bean.response.ResDynamicsBean;
import com.m086.ad.mobi.bean.response.ResLiveBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.ScreenTools;
import com.m086.ad.mobi.view.ads.LeftFragment;
import com.m086.ad.mobi.view.dynamics.adapter.DynamicsItemsAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/21.
 */

public class LeftRecycleAdapter extends RecyclerView.Adapter<LeftRecycleAdapter.MyViewHold> implements View.OnClickListener {


    private BaseFragment mContext;
    private LayoutInflater mInflater;
    private List<ResAdsBean.ListBean> mItems = new ArrayList<>();
    JSONArray mItemsJson = new JSONArray();

    public LeftRecycleAdapter(LeftFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
    }

    public LeftRecycleAdapter(LeftFragment context, List<ResAdsBean.ListBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = items;
    }


    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.adapter_ads_items, parent, false);
            holder = new MyViewHold(mView);
            mView.setTag(holder);
        } else {
            holder = (MyViewHold) mView.getTag();
        }
        mView.setOnClickListener(this);
        return holder;
    }

    public void removeAds(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(MyViewHold mViewHold, final int position) {
        mViewHold.itemView.setTag(position);
        ScreenTools.setViewHeight(mViewHold.ivDelete, ScreenTools.getScreenWidth(mContext.getActivity()) / 5);
        final ResAdsBean.ListBean bean = mItems.get(position);

        String[] picArry = bean.getSmallpic().split(",");
        mContext.setBitmaptoImageView(SysConfig.PicUrl + picArry[0],
                mViewHold.videoIcon,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);


        mViewHold.className.setText("[" + bean.getContent() + "]");
        if (bean.getStatus().equals("1")) {
            mViewHold.aliveNum.setText("生效");
            mViewHold.aliveNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.drawable_btn_ff));
        } else {
            mViewHold.aliveNum.setText("无效");
            mViewHold.aliveNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.drawable_btn_eee));
        }
        mViewHold.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.onItemClick(position, bean);
                }
            }
        });
        mViewHold.ivEffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEffectiveClincklistener != null) {
                    mOnEffectiveClincklistener.onItemClick(position, bean);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (ResAdsBean.ListBean) mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView aliveNum;
        ImageView videoIcon;
        TextView className;
        RelativeLayout llvideo;
        ImageView ivDelete;
        ImageView ivEffic;

        public MyViewHold(View itemView) {
            super(itemView);
            videoIcon = (ImageView) itemView.findViewById(R.id.iv_video);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_del_ads);
            ivEffic = (ImageView) itemView.findViewById(R.id.iv_eff_ads);
            className = (TextView) itemView.findViewById(R.id.tv_classname);
            llvideo = (RelativeLayout) itemView.findViewById(R.id.ll_video_item);
            aliveNum = (TextView) itemView.findViewById(R.id.tv_alivenum);
            setBannerSize(llvideo);
        }
    }

    //设置图片比例
    private void setBannerSize(RelativeLayout llvideo) {
        ViewGroup.LayoutParams lp = llvideo.getLayoutParams();
        lp.height = ScreenTools.getScreenWidth(mContext.getActivity())/5;
        llvideo.setLayoutParams(lp);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ResAdsBean.ListBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void addDatas(List<ResAdsBean.ListBean> result) {

        if (mItems != null && result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void setDatas(List<ResAdsBean.ListBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }


    OnDeleteClickListener mOnDeleteClickListener;
    onEffectiveClincklistener mOnEffectiveClincklistener;

    //define interface
    public static interface OnDeleteClickListener {
        void onItemClick(int position, ResAdsBean.ListBean data);
    }

    //define interface
    public static interface onEffectiveClincklistener {
        void onItemClick(int position, ResAdsBean.ListBean data);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnItemClickListener) {
        this.mOnDeleteClickListener = mOnItemClickListener;
    }

    public void setmOnEffectiveClincklistener(onEffectiveClincklistener mOnEffectiveClincklistener) {
        this.mOnEffectiveClincklistener = mOnEffectiveClincklistener;
    }
}

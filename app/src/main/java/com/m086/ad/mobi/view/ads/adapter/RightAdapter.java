package com.m086.ad.mobi.view.ads.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;
import com.m086.ad.mobi.bean.response.ResLiveBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.view.ads.RightFragment;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Administrator on 2016/11/21.
 */

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.MyViewHold> implements View.OnClickListener {
    private BaseFragment mContext;
    private LayoutInflater mInflater;
    private List<ResLiveBean.LiveBean> mItems = new ArrayList<>();

    public RightAdapter(RightFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
    }

    public RightAdapter(RightFragment context, List<ResLiveBean.LiveBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = items;
    }


    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHold holder = null;
        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_public_living, parent, false);
            holder = new MyViewHold(mView);
            mView.setTag(holder);
        } else {
            holder = (MyViewHold) mView.getTag();
        }
        mView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHold mViewHold, int position) {
        mViewHold.itemView.setTag(position);
        ResLiveBean.LiveBean bean = mItems.get(position);

        mContext.setBitmaptoImageView(SysConfig.PicUrl+bean.pic,
//        mContext.setBitmaptoImageView(bean.pic,
                mViewHold.videoIcon,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);
        mViewHold.tvAlivestatus.setText(bean.onlinenum);
        mViewHold.className.setText("[" + bean.liveName + "]");
//        if (bean.status == 1) {
//            mViewHold.tvAlivestatus.setText(bean.onlinenum);
//            mViewHold.tvAlivestatus.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.drawable_btn_ff));
//        } else {
//            mViewHold.tvAlivestatus.setText(bean.onlinenum);
//            mViewHold.tvAlivestatus.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.drawable_btn_eee));
//        }
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
            mOnItemClickListener.onItemClick(v, (ResLiveBean.LiveBean) mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView tvAlivestatus;
        ImageView videoIcon;
        TextView className;
        LinearLayout llvideo;

        public MyViewHold(View itemView) {
            super(itemView);
            videoIcon = (ImageView) itemView.findViewById(R.id.iv_video);
            tvAlivestatus = (TextView) itemView.findViewById(R.id.tv_alivestatus);
            className = (TextView) itemView.findViewById(R.id.tv_classname);
            llvideo = (LinearLayout) itemView.findViewById(R.id.ll_video_item);

//            mContext.mViewUtil.setViewWidth(llvideo, mContext.mViewUtil.getScreenWidth() / 2);
            // mContext.mViewUtil.setViewHight(videoIcon, mContext.mViewUtil.getScreenWidth() / 4);
            // mContext.mViewUtil.setViewWidth(videoIcon, mContext.mViewUtil.getScreenWidth() / 2);
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ResLiveBean.LiveBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void addDatas(List<ResLiveBean.LiveBean> result) {

        if (mItems != null && result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void setDatas(List<ResLiveBean.LiveBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

}

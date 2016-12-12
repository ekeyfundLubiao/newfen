package moni.anyou.com.view.view.living.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.RecycleViewBean;
import moni.anyou.com.view.bean.VideoBean;
import moni.anyou.com.view.view.KindergartenFragment;
import moni.anyou.com.view.view.living.LivingChildFragment;
import moni.anyou.com.view.view.living.LivingChildRightFragment;

/**
 * Created by Administrator on 2016/11/21.
 */

public class VideoRecycleAdapter extends RecyclerView.Adapter<VideoRecycleAdapter.MyViewHold> implements View.OnClickListener {


    private BaseFragment mContext;
    private LayoutInflater mInflater;
    private List<VideoBean> mItems;

    public VideoRecycleAdapter(LivingChildFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
    }
    public VideoRecycleAdapter(LivingChildFragment context, List<VideoBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = items;
    }


    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.adapter_video_item, parent, false);
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
        VideoBean bean = mItems.get(position);

        mContext.setBitmaptoImageView(bean.Url,
                mViewHold.videoIcon,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);
        mViewHold.aliveNum.setText(bean.aliveNum);
        mViewHold.className.setText(bean.className);
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
            mOnItemClickListener.onItemClick(v, (VideoBean) mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView aliveNum;
        ImageView videoIcon;
        TextView className;
        LinearLayout llvideo;

        public MyViewHold(View itemView) {
            super(itemView);
            videoIcon = (ImageView) itemView.findViewById(R.id.iv_video);
            aliveNum = (TextView) itemView.findViewById(R.id.tv_alivenum);
            className = (TextView) itemView.findViewById(R.id.tv_classname);
            llvideo = (LinearLayout) itemView.findViewById(R.id.ll_video_item);

          //  mContext.mViewUtil.setViewWidth(llvideo, mContext.mViewUtil.getScreenWidth() / 2);
           // mContext.mViewUtil.setViewHight(videoIcon, mContext.mViewUtil.getScreenWidth() / 4);
           // mContext.mViewUtil.setViewWidth(videoIcon, mContext.mViewUtil.getScreenWidth() / 2);
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, VideoBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDatas(List<VideoBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (mItems != null && result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

}

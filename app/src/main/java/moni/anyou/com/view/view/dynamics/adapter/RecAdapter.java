package moni.anyou.com.view.view.dynamics.adapter;


import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.view.DynamicsFragment;
import moni.anyou.com.view.view.dynamics.SendDynamicActivity;

/**
 * Created by Administrator on 2016/11/21.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHold> implements View.OnClickListener {


    private BaseFragment mContext;
    private LayoutInflater mInflater;
    private String mItems[];

    public RecAdapter(DynamicsFragment context, String items[]) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = items;
    }

    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.adapter_dynamics_show_pic, parent, false);
            holder = new MyViewHold(mView);
            mView.setTag(holder);
        } else {
            holder = (MyViewHold) mView.getTag();
        }
        mView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHold mViewHold, final int position) {
        mViewHold.itemView.setTag(position);


        //mViewHold.ivSentPic.setImageBitmap(item.bitmap);
        mContext.setBitmaptoImageView(SysConfig.PicUrl + mItems[position],
                mViewHold.ivSentPic,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);
        mViewHold.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPositionClickListener != null) {
                    onPositionClickListener.onItemClick(position, mItems);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.length;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) mItems[(Integer) v.getTag()]);
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        ImageView ivSentPic;
        LinearLayout llItem;

        public MyViewHold(View itemView) {
            super(itemView);
            llItem = (LinearLayout) itemView.findViewById(R.id.item_id);
            mContext.mViewUtil.setViewWidth(llItem, mContext.mViewUtil.getScreenWidth() / 3);
            ivSentPic = (ImageView) itemView.findViewById(R.id.iv_send);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDatas(String[] result) {

        if (result != null) {
            mItems = result;
        }
        notifyDataSetChanged();
    }


    public OnPositionClickListener onPositionClickListener;

    //define interface
    public static interface OnPositionClickListener {
        void onItemClick(int position, String[] url);
    }

    public void setOnPositionClickListener(OnPositionClickListener onPositionClickListener) {
        this.onPositionClickListener = onPositionClickListener;
    }
}

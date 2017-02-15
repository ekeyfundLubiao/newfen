package moni.anyou.com.view.view.dynamics.adapter;


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
import moni.anyou.com.view.view.DynamicsFragment;
import moni.anyou.com.view.view.dynamics.SendDynamicActivity;

/**
 * Created by Administrator on 2016/11/21.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHold> implements View.OnClickListener {


    private BaseFragment mContext;
    private LayoutInflater mInflater;
    private ArrayList<SentPicBean> mItems;

    public RecAdapter(DynamicsFragment context, ArrayList<SentPicBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = items;
    }

    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.adapter_send_pic, parent, false);
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
        final SentPicBean item = mItems.get(position);


            mViewHold.ivSentPic.setImageBitmap(item.bitmap);
            mContext.setBitmaptoImageView(item.Url,
                    mViewHold.ivSentPic,
                    R.drawable.loading_null_21,
                    R.drawable.loading_null_21,
                    R.drawable.loading_err_21);


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
            mOnItemClickListener.onItemClick(v, (SentPicBean) mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        ImageView ivSentPic;
        LinearLayout llItem;

        public MyViewHold(View itemView) {
            super(itemView);
            llItem = (LinearLayout) itemView.findViewById(R.id.item_id);
            mContext.mViewUtil.setViewWidth(llItem, mContext.mViewUtil.getScreenWidth() /3);
            ivSentPic = (ImageView) itemView.findViewById(R.id.iv_send);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, SentPicBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDatas(List<SentPicBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (mItems != null && result != null) {
            mItems.addAll(result);
        }

    }

}

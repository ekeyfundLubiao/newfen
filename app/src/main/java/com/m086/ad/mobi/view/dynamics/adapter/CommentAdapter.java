package com.m086.ad.mobi.view.dynamics.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;
import com.m086.ad.mobi.bean.response.ResDynamicsBean;
import com.m086.ad.mobi.tool.TextTool;
import com.m086.ad.mobi.view.DynamicsFragment;

/**
 * Created by Administrator on 2016/11/21.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHold> implements View.OnClickListener {


    private BaseFragment mContext;
    private LayoutInflater mInflater;
    private ArrayList<ResDynamicsBean.CommentListBean> mItems;

    public CommentAdapter(DynamicsFragment context, List<ResDynamicsBean.CommentListBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = (ArrayList<ResDynamicsBean.CommentListBean>) items;
    }

    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_comment, parent, false);
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
        StringBuilder tempsb = new StringBuilder();
        tempsb.append(mItems.get(position).getNick()).append(" : ").append(mItems.get(position).getContent());
        int pontvalue = tempsb.toString().indexOf(" : ");
        TextTool.forCommentText(tempsb.toString(), mViewHold.tvContenct, pontvalue, mContext.mContext);
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
            mOnItemClickListener.onItemClick(v, mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView tvContenct;

        public MyViewHold(View itemView) {
            super(itemView);
            tvContenct = (TextView) itemView.findViewById(R.id.tv_contant);


        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ResDynamicsBean.CommentListBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDatas(ArrayList<ResDynamicsBean.CommentListBean> result) {

        if (result != null) {
            mItems = result;
        }
        notifyDataSetChanged();
    }

}

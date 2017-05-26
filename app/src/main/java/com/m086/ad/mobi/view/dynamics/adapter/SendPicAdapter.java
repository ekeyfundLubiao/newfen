package com.m086.ad.mobi.view.dynamics.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.bean.SentPicBean;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.view.dynamics.SendDynamicActivity;

/**
 * Created by Administrator on 2016/11/21.
 */

public class SendPicAdapter extends RecyclerView.Adapter<SendPicAdapter.MyViewHold> {


    private SendDynamicActivity mContext;
    private LayoutInflater mInflater;
    private ArrayList<SentPicBean> mItems = new ArrayList<SentPicBean>();

    public ArrayList<SentPicBean> getmItems() {
        return mItems;
    }

    public SendPicAdapter(SendDynamicActivity context, SentPicBean items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems.add(items);
    }

    public void addPic(SentPicBean Bean,int position) {
        // mItems.addFirst(Bean);
        mItems.add(position, Bean);
        notifyDataSetChanged();

    }

    public void remove() {
        mItems.remove(getItemCount() - 1);
        notifyDataSetChanged();
    }

    View mView;
    MyViewHold holder = null;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, final int viewType) {


        mView = mInflater.inflate(R.layout.adapter_send_pic, parent, false);
        holder = new MyViewHold(mView);


        return holder;
    }

    @Override
    public void onBindViewHolder( final  MyViewHold mViewHold, final int position) {
        mViewHold.itemView.setTag(position);
        final SentPicBean item = mItems.get(position);
        if ("".endsWith(item.filePathName.trim())) {
            mContext.setBitmaptoImageView11("drawable://" + R.mipmap.add, mViewHold.ivSentPic);
            mViewHold.ivDelete.setVisibility(View.GONE);
        } else {
            // mViewHold.ivSentPic.setImageBitmap(item.bitmap);
            mViewHold.ivDelete.setVisibility(View.VISIBLE);
            mViewHold.ivSentPic.setBackgroundResource(R.color.white);
//            mContext.setBitmaptoImageView11("file://" + Environment.getExternalStorageDirectory() + LocalConstant.Local_Photo_Path + "/crop/" + item.filePathName, mViewHold.ivSentPic);
            mContext.setBitmaptoImageView11("file://" + item.filePathName, mViewHold.ivSentPic);
          //  mContext.setBitmaptoImageView11("file://" + mContext.getCacheDir()+"/luban_disk_cache/"+item.newFileNameMap, mViewHold.ivSentPic);
           // Glide.with(mContext).load(mContext.getCacheDir()+"/luban_disk_cache/"+item.newFileNameMap).centerCrop().into(mViewHold.ivSentPic);

        }

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int  position =(Integer)mViewHold.itemView.getTag();
                try {
                    ToastTools.showShort(mContext, "Size:" + mItems.size());
                    mOnItemClickListener.onItemClick(v, (SentPicBean) mItems.get(position), position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewHold.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  position =(Integer)mViewHold.itemView.getTag();
                ToastTools.showShort(mContext, ":" + position);
                mItems.remove(position);
                notifyDataSetChanged();
                mContext.notifyEnd();
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


    class MyViewHold extends RecyclerView.ViewHolder {
        ImageView ivSentPic;
        RelativeLayout llItem;
        ImageView ivDelete;

        public MyViewHold(View itemView) {
            super(itemView);
            llItem = (RelativeLayout) itemView.findViewById(R.id.item_id);
            mContext.mViewUtil.setViewWidth(llItem, mContext.mViewUtil.getScreenWidth() / 4);
            ivSentPic = (ImageView) itemView.findViewById(R.id.iv_send);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, SentPicBean data, int positon);
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

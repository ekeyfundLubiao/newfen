package com.m086.ad.mobi.view.my.invitefamily.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.m086.ad.mobi.R;
import com.m086.ad.mobi.bean.SelectFamily;
import com.m086.ad.mobi.bean.response.ResFamilyNumer;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.DensityTools;
import com.m086.ad.mobi.view.my.invitefamily.FamilyNumbersActivity;

/**
 * Created by Administrator on 2016/11/21.
 */

public class FamilyNumberAdapter extends RecyclerView.Adapter<FamilyNumberAdapter.MyViewHold> implements View.OnClickListener {


    private FamilyNumbersActivity mContext;
    private LayoutInflater mInflater;
    private List<ResFamilyNumer.RelationBean> mItems;

    public FamilyNumberAdapter(FamilyNumbersActivity context, List<ResFamilyNumer.RelationBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems = items;
    }

    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;
        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_rc_familynumber, parent, false);
            holder = new MyViewHold(mView);
            mView.setTag(holder);
        } else {
            holder = (MyViewHold) mView.getTag();
        }
        mView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHold mViewHold, final int position) {
        mViewHold.itemView.setTag(position);
        ResFamilyNumer.RelationBean bean = mItems.get(position);

        mContext.setBitmaptoImageView(SysConfig.PicUrl + bean.getIcon(),
                mViewHold.ivHeadIcon,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);
        mViewHold.ivMark.setVisibility(View.GONE);
        // String temp=Tools.getRole(bean.role);

        //设置颜色
        if (bean.getStatus() ==-1) {
            mViewHold.tvRelation.setText("[未邀请]");
            mViewHold.tvRelation.setTextColor(mContext.getResources().getColor(R.color.color_invite_text));
            mViewHold.ivMark.setVisibility(View.VISIBLE);
        } else {
            if (bean.status == 1) {
                mViewHold.tvRelation.setText("[已邀请]");
                mViewHold.tvRelation.setTextColor(mContext.getResources().getColor(R.color.color_99999));
                mViewHold.ivMark.setVisibility(View.GONE);
            } else {
                mViewHold.tvRelation.setText("[邀请中]");
                mViewHold.tvRelation.setTextColor(mContext.getResources().getColor(R.color.color_99999));
                mViewHold.ivMark.setVisibility(View.GONE);
            }


        }

        if (bean.boolDelete && bean.getStatus() == 1||bean.boolDelete && bean.getStatus() == 0) {
            mViewHold.ivDelete.setVisibility(View.VISIBLE);
        } else {
            mViewHold.ivDelete.setVisibility(View.GONE);
        }
        if (bean.getStatus() == 1 && !bean.boolDelete||bean.boolDelete && bean.getStatus() == 0) {
            mViewHold.ivDelete.setVisibility(View.GONE);
        }
        if (bean.boolDelete && bean.getStatus() == 1||bean.boolDelete && bean.getStatus() == 0) {
            mViewHold.ivDelete.setVisibility(View.VISIBLE);
        } else {
            mViewHold.ivDelete.setVisibility(View.GONE);
        }

        if (bean.getUser_id().equals(SysConfig.uid)) {
            mViewHold.ivDelete.setVisibility(View.GONE);
            mViewHold.tvRelation.setText("[ " + "自己" + " ]");
        }
        mViewHold.tvPhoneNum.setText(bean.getMobile());
        mViewHold.tvName.setText(bean.role);
        mViewHold.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) mViewHold.itemView.getTag();
                ResFamilyNumer.RelationBean temp = mItems.get(position);
                if (mViewHold.ivDelete.getVisibility() == View.VISIBLE) {
                    temp.status = -1;
                    temp.boolDelete = false;
                    mContext.removeFamoilyNumbers(new SelectFamily(position, temp));
                }
                if (mViewHold.ivMark.getVisibility() == View.VISIBLE) {
                    mContext.addFamoilyNumbers(new SelectFamily(position, temp), position);
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
            mOnItemClickListener.onItemClick(v, (ResFamilyNumer.RelationBean) mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView tvPhoneNum;
        CircleImageView ivHeadIcon;
        TextView tvName;
        TextView tvRelation;
        ImageView ivMark;
        ImageView ivDelete;
        RelativeLayout llItem;

        public MyViewHold(View itemView) {
            super(itemView);
            ivHeadIcon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            ivMark = (ImageView) itemView.findViewById(R.id.iv_mark);
            llItem = (RelativeLayout) itemView.findViewById(R.id.item_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhoneNum = (TextView) itemView.findViewById(R.id.tv_phonenum);
            tvRelation = (TextView) itemView.findViewById(R.id.tv_relation);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            mContext.mViewUtil.setViewWidth(llItem, (mContext.mViewUtil.getScreenWidth() - DensityTools.dp2px(mContext, 36)) / 2);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ResFamilyNumer.RelationBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDatas(List<ResFamilyNumer.RelationBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (mItems != null && result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

}
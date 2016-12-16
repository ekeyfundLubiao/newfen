package moni.anyou.com.view.view.my.invitefamily.adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.RecycleViewBean;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SelectFamily;
import moni.anyou.com.view.tool.DensityTools;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.KindergartenFragment;
import moni.anyou.com.view.view.my.invitefamily.FamilyNumbersActivity;

/**
 * Created by Administrator on 2016/11/21.
 */

public class FamilyNumberAdapter extends RecyclerView.Adapter<FamilyNumberAdapter.MyViewHold>implements View.OnClickListener {


    private FamilyNumbersActivity mContext;
    private LayoutInflater mInflater;
    private List<RelationBean> mItems;
    public FamilyNumberAdapter(FamilyNumbersActivity context, List<RelationBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems=items;
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
    public void onBindViewHolder( final MyViewHold mViewHold, final int position) {
        mViewHold.itemView.setTag(position);
        RelationBean bean = mItems.get(position);

        mContext.setBitmaptoImageView(bean.url,
                mViewHold.ivHeadIcon,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);
        mViewHold.ivMark.setVisibility(View.GONE);
        mViewHold.tvRelation.setText("["+bean.relation+"]");
        if (bean.mark == 0) {
            mViewHold.tvRelation.setTextColor(Color.RED);
            mViewHold.ivMark.setVisibility(View.VISIBLE);
        } else {
            mViewHold.tvRelation.setTextColor(mContext.getResources().getColor(R.color.color_99999));
            mViewHold.ivMark.setVisibility(View.GONE);
        }
        if (bean.boolDelete && bean.mark == 1) {
            mViewHold.ivDelete.setVisibility(View.VISIBLE);
        } else {
            mViewHold.ivDelete.setVisibility(View.GONE);
        }
        if (bean.mark == 1&&!bean.boolDelete) {
            mViewHold.ivDelete.setVisibility(View.GONE);
        }
        mViewHold.tvPhoneNum.setText(bean.phoneNym);
        mViewHold.tvName.setText(bean.name);
        mViewHold.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) mViewHold.itemView.getTag();

                RelationBean temp=mItems.get(position);
                temp.mark=0;
                temp.boolDelete = false;
                mContext.removeFamoilyNumbers(new SelectFamily(position,temp));
                ToastTools.showShort(mContext,"position"+position);
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
            mOnItemClickListener.onItemClick(v,(RelationBean)mItems.get((Integer)v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView tvPhoneNum;
        ImageView ivHeadIcon;
        TextView tvName;
        TextView tvRelation;
        ImageView ivMark;
        ImageView ivDelete;
        RelativeLayout llItem;

        public MyViewHold(View itemView) {
            super(itemView);
            ivHeadIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            ivMark=(ImageView)itemView.findViewById(R.id.iv_mark);
            llItem=(RelativeLayout) itemView.findViewById(R.id.item_id);
            tvName=(TextView) itemView.findViewById(R.id.tv_name);
            tvPhoneNum=(TextView) itemView.findViewById(R.id.tv_phonenum);
            tvRelation=(TextView)itemView.findViewById(R.id.tv_relation);
            ivDelete=(ImageView)itemView.findViewById(R.id.iv_delete);
            mContext.mViewUtil.setViewWidth(llItem, (mContext.mViewUtil.getScreenWidth()-DensityTools.dp2px(mContext,36)) / 2);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, RelationBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setDatas(List<RelationBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (mItems != null && result != null) {
            mItems.addAll(result);
        }

    }

}

package com.m086.ad.mobi.view.kindergarten.adapter.adpater;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.m086.ad.mobi.R;
import com.m086.ad.mobi.bean.response.ResHomeData;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.view.KindergartenFragment;

/**
 * Created by Administrator on 2016/11/21.
 */

public class TeacherShowAdapter extends RecyclerView.Adapter<TeacherShowAdapter.MyViewHold> implements View.OnClickListener {


    private KindergartenFragment mContext;
    private LayoutInflater mInflater;
    private List<ResHomeData.TopTeachersBean> mItems;

    public TeacherShowAdapter(KindergartenFragment context,List<ResHomeData.TopTeachersBean> mItems) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        this.mItems = mItems;
    }

    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_show_teacher, parent, false);
            holder = new MyViewHold(mView);
            mView.setTag(holder);
        } else {
            holder = (MyViewHold) mView.getTag();
        }
//        mView.setOnClickListener(this);
        return holder;
    }


    public void replace(ResHomeData.TopTeachersBean text, int position) {
        mItems.remove(position);
        mItems.add(position, text);
        notifyItemChanged(position);
    }
    @Override
    public void onBindViewHolder(final MyViewHold mViewHold, final int position) {
        mViewHold.itemView.setTag(position);
        final ResHomeData.TopTeachersBean item = mItems.get(position);
        mViewHold.tvStar.setText(item.getLikes());
        mViewHold.tvTeacherName.setText(item.getNick());
        mContext.setBitmaptoImageView(SysConfig.PicUrl+item.getIcon(),
                mViewHold.ivTeacherIcon,
                R.drawable.loading_null_21,
                R.drawable.loading_null_21,
                R.drawable.loading_err_21);

        mViewHold.ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.likesPost(mItems.get((Integer) mViewHold.itemView.getTag()),position);

            }
        });
        mViewHold.ivTeacherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.teacherDetailWebView(mItems.get(position));
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
            mOnItemClickListener.onItemClick(v, (ResHomeData.TopTeachersBean) mItems.get((Integer) v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView tvTeacherName;
        CircleImageView ivTeacherIcon;
        TextView tvStar;
        ImageView ivStart;
        LinearLayout llItem;

        public MyViewHold(View itemView) {
            super(itemView);
            ivTeacherIcon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            tvStar = (TextView) itemView.findViewById(R.id.tv_star);
            llItem = (LinearLayout) itemView.findViewById(R.id.item_id);
            mContext.mViewUtil.setViewWidth(llItem, mContext.mViewUtil.getScreenWidth() / 3);
            tvTeacherName = (TextView) itemView.findViewById(R.id.tv_teachername);
            ivStart = (ImageView) itemView.findViewById(R.id.iv_start);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ResHomeData.TopTeachersBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDatas(List<ResHomeData.TopTeachersBean> result) {
        if (mItems != null && mItems.size() >0) {
            mItems.clear();
        }
        if (result != null && result.size()>0) {
            mItems.addAll(result);
        }

    }

}

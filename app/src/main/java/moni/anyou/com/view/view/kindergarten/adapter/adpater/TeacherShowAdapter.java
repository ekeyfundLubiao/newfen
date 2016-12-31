package moni.anyou.com.view.view.kindergarten.adapter.adpater;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.bean.RecycleViewBean;
import moni.anyou.com.view.view.KindergartenFragment;

/**
 * Created by Administrator on 2016/11/21.
 */

public class TeacherShowAdapter extends RecyclerView.Adapter<TeacherShowAdapter.MyViewHold>implements View.OnClickListener {


    private KindergartenFragment mContext;
    private LayoutInflater mInflater;
    private List<RecycleViewBean> mItems;
    public TeacherShowAdapter(KindergartenFragment context, List<RecycleViewBean> items) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
        mItems=items;
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
        mView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHold mViewHold, int position) {
        mViewHold.itemView.setTag(position);
        RecycleViewBean item = mItems.get(position);
        mViewHold.tvStar.setText(item.start);
        mViewHold.tvTeacherName.setText(item.teachearName);
        mContext.setBitmaptoImageView(item.Url,
                mViewHold.ivTeacherIcon,
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
            mOnItemClickListener.onItemClick(v,(RecycleViewBean)mItems.get((Integer)v.getTag()));
        }
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        TextView tvTeacherName;
        CircleImageView ivTeacherIcon;
        TextView tvStar;
        LinearLayout llItem;

        public MyViewHold(View itemView) {
            super(itemView);
            ivTeacherIcon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            tvStar = (TextView) itemView.findViewById(R.id.tv_star);
            llItem=(LinearLayout) itemView.findViewById(R.id.item_id);
            mContext.mViewUtil.setViewWidth(llItem, mContext.mViewUtil.getScreenWidth() / 3);
            tvTeacherName = (TextView) itemView.findViewById(R.id.tv_teachername);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, RecycleViewBean data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setDatas(List<RecycleViewBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }
        if (mItems != null && result != null) {
            mItems.addAll(result);
        }

    }

}

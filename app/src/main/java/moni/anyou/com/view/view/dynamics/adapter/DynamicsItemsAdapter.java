package moni.anyou.com.view.view.dynamics.adapter;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.bean.response.ResDynamicsBean;

import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.DynamicsFragment;
import moni.anyou.com.view.view.dynamics.detail.image.ImagePagerActivity;
import moni.anyou.com.view.widget.pikerview.Utils.TextUtil;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;
import moni.anyou.com.view.widget.recycleview.NoSMRecycleView;

/**
 * Created by Administrator on 2016/11/21.
 */

public class DynamicsItemsAdapter extends RecyclerView.Adapter<DynamicsItemsAdapter.MyViewHold> {


    private DynamicsFragment mContext;
    private LayoutInflater mInflater;
    private ArrayList<ResDynamicsBean.ListBean> mItems = new ArrayList<>();

    public DynamicsItemsAdapter(DynamicsFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
    }

    public DynamicsItemsAdapter(DynamicsFragment context, ArrayList<ResDynamicsBean.ListBean> mItems) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);
    }

    @Override
    public void onBindViewHolder(final MyViewHold holder, final int position) {
        mView.setTag(position);
        final ResDynamicsBean.ListBean temps = mItems.get(position);
        if (!"".equals(temps.pic)) {
            RecAdapter tempRecAdapter = new RecAdapter(mContext,temps.pic.split(","));
            holder.rc_icon.setAdapter(tempRecAdapter);
            tempRecAdapter.setOnPositionClickListener(new RecAdapter.OnPositionClickListener() {
                @Override
                public void onItemClick(int position, String[] url) {
                    imageBrower(position, url);
                }
            });
        }
        mContext.setBitmaptoImageView11(SysConfig.PicUrl + temps.icon, holder.iv_headicon);
        holder.tv_sentTime.setText(Tools.main(temps.addtime));
        holder.tvnickname.setText(temps.nick);
        holder.tvRoleName.setText("（" + temps.rolename + "）");
        holder.tv_dynamicsContant.setText(temps.content);
        holder.tv_lots.setText(temps.likeuser);
        if (!TextUtil.isEmpty(temps.likeuser)) {
            holder.tv_lots.setText(Tools.getLikeNikeNameStr(temps.likeuser));
            holder.ll_mark.setVisibility(View.VISIBLE);
            holder.v_line.setVisibility(View.VISIBLE);
        } else {
            holder.ll_mark.setVisibility(View.GONE);
            holder.v_line.setVisibility(View.GONE);
        }
        holder.ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mContext.marklike(position, getItem(position));
                IPraiseClickListener.onItemClick(position, temps);
            }
        });
        if (SysConfig.uid.equals(temps.userid)) {
            holder.iv_delete_dynamics.setVisibility(View.VISIBLE);
        } else {
            holder.iv_delete_dynamics.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext.mBaseActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rc_contonts.setLayoutManager(linearLayoutManager);
        CommentAdapter adapter = new CommentAdapter(mContext, temps.commentList);
        holder.rc_contonts.setAdapter(adapter);
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mComentClickListener != null) {
                    mComentClickListener.onItemClick(position, temps);
                }
            }
        });

        holder.iv_delete_dynamics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.onItemClick(position, temps);
                }

            }
        });


    }

    View mView;

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        DynamicsItemsAdapter.MyViewHold holder = null;
        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_dynamics, parent, false);
            holder = new DynamicsItemsAdapter.MyViewHold(mView);
            mView.setTag(holder);
        } else {
            holder = (DynamicsItemsAdapter.MyViewHold) mView.getTag();
        }
        return holder;
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }


    public void setDatas(List<ResDynamicsBean.ListBean> result) {
        if (result != null && mItems != null) {
            mItems.clear();
        }

        if (mItems != null && result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();

    }

    public void AddDatas(List<ResDynamicsBean.ListBean> result) {
        if (mItems != null && result != null) {
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }


    private ResDynamicsBean.ListBean getItem(int position) {
        return mItems.get(position);
    }

    public void removeDynamics(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        CircleImageView iv_headicon;
        TextView tvnickname;
        TextView tv_dynamicsContant;
        NoSMRecycleView rc_icon;
        TextView tv_sentTime;
        ImageView ivZan;
        ImageView ivShare;
        TextView tv_lots;
        RelativeLayout ll_mark;
        TextView iv_delete_dynamics;
        NoSMRecycleView rc_contonts;
        TextView tvRoleName;
        View v_line;

        public MyViewHold(View itemView) {
            super(itemView);

            iv_headicon = (CircleImageView) itemView.findViewById(R.id.iv_headicon);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
            ivZan = (ImageView) itemView.findViewById(R.id.iv_zan);
            rc_icon = (NoSMRecycleView) itemView.findViewById(R.id.rc_icon);
            tv_sentTime = (TextView) itemView.findViewById(R.id.tv_sentTime);
            tvnickname = (TextView) itemView.findViewById(R.id.tv_nickName);
            tv_dynamicsContant = (TextView) itemView.findViewById(R.id.tv_dynamicsContant);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext.getContext(), 3);
            rc_icon.addItemDecoration(new DividerItemDecoration(mContext.getContext(), LinearLayoutManager.HORIZONTAL));
            rc_icon.addItemDecoration(new DividerItemDecoration(mContext.getContext(), LinearLayoutManager.VERTICAL));
            rc_icon.setLayoutManager(gridLayoutManager);
            tv_lots = (TextView) itemView.findViewById(R.id.tv_lots);
            iv_delete_dynamics = (TextView) itemView.findViewById(R.id.iv_delete_dynamics);
            ll_mark = (RelativeLayout) itemView.findViewById(R.id.ll_mark);
            tvRoleName = (TextView) itemView.findViewById(R.id.tv_roleName);
            rc_contonts = (NoSMRecycleView) itemView.findViewById(R.id.rc_contonts);
            v_line = itemView.findViewById(R.id.v_line);
            linearLayoutManager = new LinearLayoutManager(mContext.mContext,
                    LinearLayoutManager.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }

            };
        }
    }


    private ComentClickListener mComentClickListener = null;
    private OnDeleteClickListener mOnDeleteClickListener = null;
    private IPraiseClickListener IPraiseClickListener = null;

    //define interface
    public static interface OnDeleteClickListener {
        void onItemClick(int position, ResDynamicsBean.ListBean data);
    }

    //define interface
    public static interface ComentClickListener {
        void onItemClick(int position, ResDynamicsBean.ListBean data);
    }

    public static interface IPraiseClickListener {
        void onItemClick(int position, ResDynamicsBean.ListBean data);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnItemClickListener) {
        this.mOnDeleteClickListener = mOnItemClickListener;
    }

    public void setComentClickListener(ComentClickListener comentClickListener) {
        this.mComentClickListener = comentClickListener;
    }

    public void setIPraiseClickListener(IPraiseClickListener iPraiseClickListener) {
        this.IPraiseClickListener = iPraiseClickListener;
    }

    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(mContext.mBaseActivity, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }


    LinearLayoutManager linearLayoutManager ;

}

package moni.anyou.com.view.view.dynamics.adapter;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.DynamicsTempItems;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.DynamicsFragment;
import moni.anyou.com.view.view.dynamics.SendDynamicActivity;
import moni.anyou.com.view.widget.pikerview.Utils.TextUtil;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;

/**
 * Created by Administrator on 2016/11/21.
 */

public class DynamicsItemAdapter extends BaseAdapter {


    private DynamicsFragment mContext;
    private LayoutInflater mInflater;
    private ArrayList<ResDynamicsBean.ListBean> mItems = new ArrayList<>();

    public DynamicsItemAdapter(DynamicsFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);

    }

    @Override
    public int getCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public ResDynamicsBean.ListBean getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View mView, ViewGroup viewGroup) {
        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_dynamics, viewGroup, false);
            holder = new MyViewHold();
            holder.iv_headicon = (CircleImageView) mView.findViewById(R.id.iv_headicon);
            holder.ivShare = (ImageView) mView.findViewById(R.id.iv_share);
            holder.ivZan = (ImageView) mView.findViewById(R.id.iv_zan);
            holder.rc_icon = (RecyclerView) mView.findViewById(R.id.rc_icon);
            holder.tv_sentTime = (TextView) mView.findViewById(R.id.tv_sentTime);
            holder.tvnickname = (TextView) mView.findViewById(R.id.tv_nickName);
            holder.tv_dynamicsContant = (TextView) mView.findViewById(R.id.tv_dynamicsContant);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext.getContext(), 3);
            holder.rc_icon.addItemDecoration(new DividerItemDecoration(mContext.getContext(), LinearLayoutManager.HORIZONTAL));
            holder.rc_icon.addItemDecoration(new DividerItemDecoration(mContext.getContext(), LinearLayoutManager.VERTICAL));
            holder.rc_icon.setLayoutManager(gridLayoutManager);
            holder.tv_lots = (TextView) mView.findViewById(R.id.tv_lots);
            holder.iv_delete_dynamics = (TextView) mView.findViewById(R.id.iv_delete_dynamics);
            holder.ll_mark = (LinearLayout) mView.findViewById(R.id.ll_mark);
            mView.setTag(holder);

        } else {
            holder = (MyViewHold) mView.getTag();
        }
        mView.setTag(position);
        ResDynamicsBean.ListBean temps = getItem(position);

        RecAdapter tempRecAdapter = new RecAdapter(mContext, temps.pic.split(","));

        holder.rc_icon.setAdapter(tempRecAdapter);
        mContext.setBitmaptoImageView11(SysConfig.PicUrl + temps.icon, holder.iv_headicon);
        holder.tv_sentTime.setText(Tools.main(temps.addtime));
        holder.tvnickname.setText(temps.rolename);
        holder.tv_dynamicsContant.setText(temps.content);
        holder.tv_lots.setText(temps.likeuser);
        if (!TextUtil.isEmpty(temps.likeuser)) {
            holder.tv_lots.setText(Tools.getLikeNikeNameStr(temps.likeuser));
            holder.ll_mark.setVisibility(View.VISIBLE);
        } else {
            holder.ll_mark.setVisibility(View.GONE);
        }
        holder.ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mContext.marklike(position, getItem(position));
            }
        });
        if (SysConfig.uid.equals(temps.userid)) {
            holder.iv_delete_dynamics.setVisibility(View.VISIBLE);
        } else {
            holder.iv_delete_dynamics.setVisibility(View.GONE);
        }
        addOnClick(holder, mView);
        return mView;
    }

    static class MyViewHold {
        CircleImageView iv_headicon;
        TextView tvnickname;
        TextView tv_dynamicsContant;
        RecyclerView rc_icon;
        TextView tv_sentTime;
        ImageView ivZan;
        ImageView ivShare;
        TextView tv_lots;
        LinearLayout ll_mark;
        TextView iv_delete_dynamics;

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

    private void addOnClick(final MyViewHold holder, final View mView) {

        holder.iv_delete_dynamics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) mView.getTag();
                mContext.postDeleteDynamics(getItem(position), position);
                //Log.d("TAG", "onClick: " + (Integer) mView.getTag());
            }
        });
    }

    public void removeDynamics(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    public void updateDynamics(int position, ResDynamicsBean.ListBean bean) {
        mItems.remove(position);
        mItems.add(position, bean);
        notifyDataSetChanged();
    }
}

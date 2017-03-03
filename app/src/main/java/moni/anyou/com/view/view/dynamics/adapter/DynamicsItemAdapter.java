package moni.anyou.com.view.view.dynamics.adapter;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.DynamicsTempItems;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
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

public class DynamicsItemAdapter extends BaseAdapter implements View.OnClickListener {


    private DynamicsFragment mContext;
    private LayoutInflater mInflater;
    private ArrayList<ResDynamicsBean.ListBean> mItems=new ArrayList<>();

    public DynamicsItemAdapter(DynamicsFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext.mBaseActivity);

    }


    @Override
    public void onClick(View view) {

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
    public View getView( final int position, View mView, ViewGroup viewGroup) {
        MyViewHold holder = null;

        if (holder == null) {
            mView = mInflater.inflate(R.layout.item_dynamics, viewGroup, false);
            holder = new MyViewHold();
            holder.iv_headicon = (ImageView) mView.findViewById(R.id.iv_headicon);
            holder.ivShare= (ImageView) mView.findViewById(R.id.iv_share);
            holder.ivZan= (ImageView) mView.findViewById(R.id.iv_zan);
            holder.rc_icon=(RecyclerView) mView.findViewById(R.id.rc_icon);
            holder.tv_sentTime = (TextView) mView.findViewById(R.id.tv_sentTime);
            holder.tvnickname = (TextView) mView.findViewById(R.id.tv_nickName);
            holder.tv_dynamicsContant = (TextView) mView.findViewById(R.id.tv_dynamicsContant);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext.getContext(),3);
            holder.rc_icon.addItemDecoration(new DividerItemDecoration(mContext.getContext(), LinearLayoutManager.HORIZONTAL));
            holder.rc_icon.addItemDecoration(new DividerItemDecoration(mContext.getContext(), LinearLayoutManager.VERTICAL));
            holder.rc_icon.setLayoutManager(gridLayoutManager);
            holder.tv_lots = (TextView) mView.findViewById(R.id.tv_lots);
            mView.setTag(holder);

        } else {
            holder = (MyViewHold) mView.getTag();
        }
        ResDynamicsBean.ListBean temps = getItem(position);

        RecAdapter tempRecAdapter = new RecAdapter(mContext, temps.getPic().split(","));

        holder.rc_icon.setAdapter(tempRecAdapter);
        mContext.setBitmaptoImageView11(SysConfig.PicUrl+temps.getPic(),holder.iv_headicon);
        holder.tv_sentTime.setText(Tools.main(temps.getAddtime()));
        holder.tvnickname.setText(temps.getNick());
        holder.tv_dynamicsContant.setText(temps.getContent());
        holder.tv_lots.setText(temps.getLikeuser());
        if (!TextUtil.isEmpty(temps.getLikeuser())) {
          holder.tv_lots.setText(Tools.getLikeNikeNameStr(temps.getLikeuser()));
        }
        holder.ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.marklike(position,getItem(position));
            }
        });
        return mView;
    }

    class MyViewHold {
        ImageView iv_headicon;
        TextView tvnickname;
        TextView tv_dynamicsContant;
        RecyclerView rc_icon;
        TextView tv_sentTime;
        ImageView ivZan;
        ImageView ivShare;
        TextView tv_lots;

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
}

package moni.anyou.com.view.view.dynamics.adapter;


import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.RecycleViewBean;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.contacts.LocalConstant;
import moni.anyou.com.view.view.KindergartenFragment;
import moni.anyou.com.view.view.dynamics.SendDynamicActivity;

/**
 * Created by Administrator on 2016/11/21.
 */

public class SendPicAdapter extends RecyclerView.Adapter<SendPicAdapter.MyViewHold> {


    private BaseActivity mContext;
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

    public void addPic(SentPicBean Bean) {
       // mItems.addFirst(Bean);
        mItems.add(0, Bean);
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
    public void onBindViewHolder(MyViewHold mViewHold, final int position) {
        final SentPicBean item = mItems.get(position);
        if ("".endsWith(item.filePathName.trim())) {
            mViewHold.ivSentPic.setBackgroundResource(R.mipmap.add);
            mViewHold.ivDelete.setVisibility(View.GONE);
        } else {
            // mViewHold.ivSentPic.setImageBitmap(item.bitmap);
            mViewHold.ivDelete.setVisibility(View.VISIBLE);
            mContext.setBitmaptoImageView11("file://" + Environment.getExternalStorageDirectory() + LocalConstant.Local_Photo_Path + "/crop/" + item.filePathName, mViewHold.ivSentPic);
        }

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, (SentPicBean) mItems.get(position), position);
            }
        });
        mViewHold.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastTools.showShort(mContext,":"+position);
                mItems.remove(position);
                notifyDataSetChanged();
                //notifyItemRemoved(position);
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

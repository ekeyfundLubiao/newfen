package moni.anyou.com.view.view.my.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.response.ResNoticeData;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.MyFragment;
import moni.anyou.com.view.view.my.SystemsNoticeActivity;
import moni.anyou.com.view.view.my.invitefamily.FamilyNumbersActivity;

public class NoticeItemslAdapter extends RecyclerView.Adapter<NoticeItemslAdapter.MyViewHolder> {

    /**
     * Item 点击事件监听的回调方法
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private SystemsNoticeActivity mContext;
    private LayoutInflater mInflater;
    private List<ResNoticeData.ListBean> list = new ArrayList<ResNoticeData.ListBean>();

    public NoticeItemslAdapter(SystemsNoticeActivity context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context.mBaseActivity);
    }

    public void setDatas(List<ResNoticeData.ListBean> result) {
        if (result != null && list != null) {
            list.clear();
        }
        if (list != null && result != null) {
            list.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<ResNoticeData.ListBean> result) {

        if (list != null && result != null) {
            list.addAll(result);
        }
        notifyDataSetChanged();
    }



    public ResNoticeData.ListBean getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        }
        return null;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_notice_living, parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder( final MyViewHolder holder, int position) {
        holder.tv_noticeContent.setText(list.get(position).getContents());
        holder.tv_noticeTime.setText(list.get(position).getAddtime());

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_noticeTime;
        public TextView tv_noticeContent;

        public MyViewHolder(View view) {
            super(view);
            tv_noticeTime = (TextView) view.findViewById(R.id.tv_noticeTime);
            tv_noticeContent = (TextView) view.findViewById(R.id.tv_noticeContent);
        }
    }


}
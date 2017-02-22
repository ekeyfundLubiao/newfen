package moni.anyou.com.view.view.my.adapter;

import android.content.Intent;
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

public class NoticeItemslAdapter extends BaseAdapter {
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
    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public ResNoticeData.ListBean getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = new ViewHold();
        if (convertView == null) {
            convertView = View.inflate(mContext.mBaseActivity, R.layout.item_notice_living, null);
            viewHold.tv_noticeContent = (TextView) convertView.findViewById(R.id.tv_noticeContent);
            viewHold.tv_noticeTime = (TextView) convertView.findViewById(R.id.tv_noticeTime);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        final ResNoticeData.ListBean temp = list.get(position);

        viewHold.tv_noticeContent.setText(temp.getContents());
        viewHold.tv_noticeTime.setText(temp.getAddtime());

        return convertView;
    }

    class ViewHold {

        private TextView tv_noticeTime;
        private TextView tv_noticeContent;

    }


}

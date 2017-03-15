package moni.anyou.com.view.view.my.systemset.adapter;

import android.content.Context;
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
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.view.MyFragment;
import moni.anyou.com.view.view.my.PersonInfoSettingActivity;
import moni.anyou.com.view.view.my.invitefamily.InviteFamilyActivity;
import moni.anyou.com.view.view.my.systemset.SystemSettingActivity;

public class SettingItemslAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<HomeItemBean> list = new ArrayList<HomeItemBean>();

    public SettingItemslAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public SettingItemslAdapter(Context context, List<HomeItemBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setDatas(List<HomeItemBean> result) {
        if (result != null && list != null) {
            list.clear();
        }
        if (list != null && result != null) {
            list.addAll(result);
        }
        this.notifyDataSetChanged();
    }
    public void replace(int position,HomeItemBean result) {
        list.remove(position);
        list.add(position,result);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public HomeItemBean getItem(int position) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = new ViewHold();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_setting_items, null);
            viewHold.title = (TextView) convertView.findViewById(R.id.tv_home_title);
            viewHold.homeItem = (RelativeLayout) convertView.findViewById(R.id.rl_set_item);
            viewHold.tValue = (TextView) convertView.findViewById(R.id.tv_value);
            viewHold.right_arr = (ImageView) convertView.findViewById(R.id.right_arr);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.right_arr.setVisibility(View.INVISIBLE);
        final HomeItemBean temp = list.get(position);
        viewHold.title.setText(temp.title);
        viewHold.tValue.setText(temp.value);
        if (temp.iSHowArr) {
            viewHold.right_arr.setVisibility(View.VISIBLE);
        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SystemSettingActivity.jumpTo(position, getItem(position));
//            }
//        });

        return convertView;
    }

    class ViewHold {
        private TextView tValue;
        private TextView title;
        private ImageView right_arr;
        RelativeLayout homeItem;
    }
}

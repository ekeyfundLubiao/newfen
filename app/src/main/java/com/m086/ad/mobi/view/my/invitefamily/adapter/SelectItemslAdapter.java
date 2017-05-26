package com.m086.ad.mobi.view.my.invitefamily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.view.my.invitefamily.SelectActivity;

public class SelectItemslAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SelectActivity.Contact>  list = new ArrayList<SelectActivity.Contact>();

    public SelectItemslAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public SelectItemslAdapter(Context context, ArrayList<SelectActivity.Contact> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setDatas(List<SelectActivity.Contact> result) {
        if (result != null && list != null) {
            list.clear();
        }
        if (list != null && result != null) {
            list.addAll(result);
        }
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
    public SelectActivity.Contact getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_contact_person, null);
            viewHold.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHold.value = (TextView) convertView.findViewById(R.id.tv_value);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        final SelectActivity.Contact temp = list.get(position);
        viewHold.title.setText(temp.name);
        viewHold.value.setText(temp.number);
        return convertView;
    }

    class ViewHold {

        private TextView title;
        private TextView value;

    }
}

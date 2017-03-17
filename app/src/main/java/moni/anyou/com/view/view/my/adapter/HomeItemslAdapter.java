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
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.view.MyFragment;
import moni.anyou.com.view.view.my.SystemsNoticeActivity;
import moni.anyou.com.view.view.my.invitefamily.FamilyNumbersActivity;
import moni.anyou.com.view.view.my.invitefamily.InviteFamilyActivity;
import moni.anyou.com.view.view.my.systemset.SystemSettingActivity;

public class HomeItemslAdapter extends BaseAdapter {
    private MyFragment mContext;
    private LayoutInflater mInflater;
    private List<HomeItemBean> list = new ArrayList<HomeItemBean>();

    public HomeItemslAdapter(MyFragment context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context.mBaseActivity);
    }

    public void setDatas(List<HomeItemBean> result) {
        if (result != null && list != null) {
            list.clear();
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = new ViewHold();
        if (convertView == null) {
            convertView = View.inflate(mContext.mBaseActivity, R.layout.adapter_home_base_items, null);
            viewHold.homeIcom = (ImageView) convertView.findViewById(R.id.iv_home_icon);
            viewHold.title = (TextView) convertView.findViewById(R.id.tv_home_title);
            viewHold.value = (TextView) convertView.findViewById(R.id.tv_home_value);
            viewHold.homeItem = (RelativeLayout) convertView.findViewById(R.id.rl_home_item);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        final HomeItemBean temp = list.get(position);
        viewHold.homeIcom.setBackgroundResource(temp.IconRes);
        viewHold.title.setText(temp.title);
        viewHold.value.setText(temp.value);
        viewHold.homeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                switch (temp.IconRes) {
                    case R.mipmap.home_icon_changegarde:
                        //切换园
                        ToastTools.showShort(mContext.getContext(),"切换graden");
                        break;
                    case R.mipmap.jiating:
                        //邀请家人
                        intent.setClass(mContext.mBaseActivity, FamilyNumbersActivity.class);
                        mContext.startActivity(intent);
                        mContext.activityAnimation(mContext.mBaseActivity.RIGHT_IN);
                        break;
                    case R.mipmap.home_icon_new:
                        //公告
                        break;
                    case R.mipmap.home_icon_user_help:
                        //使用帮住
                        intent.setClass(mContext.mBaseActivity, FamilyNumbersActivity.class);
                        mContext.startActivity(intent);
                        mContext.activityAnimation(mContext.mBaseActivity.RIGHT_IN);
                        break;
                    case R.mipmap.home_icon_suggestion:
                        //意见反馈
                        break;
                    case R.mipmap.home_icon_pay:
                        break;
                    case R.mipmap.home_icon_integralmall:
                        break;
                    case R.mipmap.gonggao:
                        intent.setClass(mContext.mBaseActivity, SystemsNoticeActivity.class);
                        mContext.startActivity(intent);
                        mContext.activityAnimation(mContext.mBaseActivity.RIGHT_IN);
                        break;
                    case R.mipmap.home_icon_addressbook:
                        ToastTools.showShort(mContext.getContext(),"通讯录");
                        break;

                }
            }
        });
        return convertView;
    }

    class ViewHold {
        private ImageView homeIcom;
        private TextView title;
        private TextView value;
        RelativeLayout homeItem;
    }


}

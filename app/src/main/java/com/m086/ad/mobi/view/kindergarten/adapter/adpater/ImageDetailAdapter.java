package com.m086.ad.mobi.view.kindergarten.adapter.adpater;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.bean.Banner;
import com.m086.ad.mobi.view.kindergarten.adapter.picshow.NewsPicDetaiActivity;

public class ImageDetailAdapter extends PagerAdapter {
    private NewsPicDetaiActivity mContext;
    private static final int FAKE_BANNER_SIZE = 1000000;
    private List<Banner> list = new ArrayList<Banner>();

    public ImageDetailAdapter(NewsPicDetaiActivity context) {
        this.mContext = context;
    }

    public void setDatas(List<Banner> result) {
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
        return FAKE_BANNER_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % list.size();
        final View imageLayout = LayoutInflater.from(mContext.mBaseActivity)
                .inflate(R.layout.adapter_imagedetail_page, null);
        RelativeLayout mainLayout = (RelativeLayout) imageLayout
                .findViewById(R.id.banner_main);
        ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.index_image);

        final Banner banner = list.get(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        mContext.setBitmaptoImageView(banner.bannerUrl, imageView, R.drawable.loading_11, R.drawable.loading_err_11, R.drawable.loading_null_11);

        container.addView(imageLayout);
        return imageLayout;
    }


}

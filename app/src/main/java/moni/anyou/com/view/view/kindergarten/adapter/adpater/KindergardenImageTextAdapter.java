package moni.anyou.com.view.view.kindergarten.adapter.adpater;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.bean.Banner;
import moni.anyou.com.view.bean.response.ResHomeData;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.view.KindergartenFragment;

public class KindergardenImageTextAdapter extends PagerAdapter {
	private KindergartenFragment mFragment;
	private static final int FAKE_BANNER_SIZE = 1000000;
	private List<ResHomeData.TopNewsBean> list = new ArrayList<ResHomeData.TopNewsBean>();

	public KindergardenImageTextAdapter(KindergartenFragment fragment) {
		this.mFragment = fragment;
	}

	public void setDatas(List<ResHomeData.TopNewsBean> result) {
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
		final View imageLayout = LayoutInflater.from(mFragment.mBaseActivity)
				.inflate(R.layout.adapter_index_page_item, null);
		RelativeLayout mainLayout = (RelativeLayout) imageLayout
				.findViewById(R.id.banner_main);
		ImageView imageView = (ImageView) imageLayout
				.findViewById(R.id.index_image);
		TextView tvNewsTitle=(TextView)imageLayout.findViewById(R.id.tv_news_title);
		TextView tvSizeMark=(TextView)imageLayout.findViewById(R.id.tv_size_mark);
		final ResHomeData.TopNewsBean banner = list.get(position);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);

		mFragment.setBitmaptoImageView21(SysConfig.PicUrl+banner.getPic(),imageView);
		tvSizeMark.setText(position+1+"/"+list.size());
		tvNewsTitle.setText(banner.getTitle());
		mainLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (mFragment.isCanClick) {
//					if (VerificationTools.isNull(banner.linkCategory)) {
//						toWeb(banner);
//					} else {
//						switch (banner.linkCategory.trim()) {
//						case "H5":
//							toWeb(banner);
//							break;
//						case "Mall":
//							((IndexActivity) mFragment.getActivity())
//									.checkPosition(2);
//							break;
//						default:
//							break;
//						}
//					}
//					mFragment.isCanClick = false;
//				}
			}
		});
		container.addView(imageLayout);
		return imageLayout;
	}

//	private void toWeb(Banner banner) {
//		if (!TextUtils.isEmpty(banner.linkUrl)) {
//			Intent intent = new Intent(mFragment.mContext,
//					WebViewActivity.class);
//			WebBean bean = new WebBean();
//			bean.title = banner.title;
//			bean.url = banner.linkUrl;
//			bean.type = BaseActivity.RIGHT_OUT;
//			intent.putExtra(WebViewActivity.BEAN_KEY, bean);
//			mFragment.mContext.startActivity(intent);
//			mFragment.mBaseActivity.activityAnimation(BaseActivity.RIGHT_IN);
//		}
//	}
}

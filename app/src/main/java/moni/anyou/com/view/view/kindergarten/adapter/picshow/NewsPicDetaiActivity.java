package moni.anyou.com.view.view.kindergarten.adapter.picshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.Banner;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.ImageDetailAdapter;
import moni.anyou.com.view.view.kindergarten.adapter.adpater.KindergardenImageTextAdapter;
import moni.anyou.com.view.widget.banner.AutoScrollViewPager;

public class NewsPicDetaiActivity extends BaseActivity implements View.OnClickListener {
    private AutoScrollViewPager bannerNewsPics;
    private TextView tvTitle;
    private ImageView ivBack;
    private ImageDetailAdapter mImgdetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pic_detai);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        bannerNewsPics=(AutoScrollViewPager) findViewById(R.id.banner_news_pics);
        tvTitle = (TextView) findViewById(R.id.page_title);
        ivBack = (ImageView) findViewById(R.id.iv_left);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                activityAnimation(RIGHT_OUT);
            }
        });
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
    }

    @Override
    public void setData() {
        super.setData();
        String[] imageArray={"http://img0.imgtn.bdimg.com/it/u=106235241,3970903275&fm=21&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2768400507,729312347&fm=21&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=902535720,3690912714&fm=21&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=3788092477,1980763064&fm=21&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1789681754,2625846600&fm=21&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=2635520879,3977805749&fm=21&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3005530274,2015303717&fm=21&gp=0.jpg"};
        ArrayList<Banner> banners =new ArrayList<Banner>();
        for (int i=0;i<7;i++) {
            Banner temp= new Banner();
            temp.bannerUrl=imageArray[i];
            temp.linkUrl="";
            banners.add(temp);
        }

        mImgdetailAdapter = new ImageDetailAdapter(this);
        mImgdetailAdapter.setDatas(banners);
        bannerNewsPics.setAdapter(mImgdetailAdapter);
        bannerNewsPics.setPageMargin(0);
        bannerNewsPics.setInterval(1500);
        //bannerNewsPics.startAutoScroll();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(LEFT_OUT);
    }
}

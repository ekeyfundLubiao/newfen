package moni.anyou.com.view.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;
import moni.anyou.com.view.bean.DynamicsTempItems;
import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.view.dynamics.adapter.DynamicsItemAdapter;


public class DynamicsFragment extends BaseFragment {

    private View mView;
    private CircleImageView cvHeadIcon;
    private ListView lvDynamics;
    private DynamicsItemAdapter dynamicsItemAdapter;
    private ArrayList<DynamicsTempItems> mItems;
    public DynamicsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamics, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        initTitle(mView);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("动态");
        lvDynamics= (ListView) mView.findViewById(R.id.lv_dynamics);
        cvHeadIcon = (CircleImageView)mView.findViewById(R.id.civ_headIcon);
        dynamicsItemAdapter = new DynamicsItemAdapter(this);
        lvDynamics.setAdapter(dynamicsItemAdapter);
        mItems = new ArrayList<>();
    }


    String[] pic = {"http://img3.imgtn.bdimg.com/it/u=2364754357,1896189482&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2040109377,1412473547&fm=21&gp=0.jpg",
            "http://qq1234.org/uploads/allimg/140926/3_140926144058_3.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2752436590,1904914861&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2364754357,1896189482&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2040109377,1412473547&fm=21&gp=0.jpg"
    };
    @Override
    public void setData() {
        super.setData();
        for (int i=0,size=6;i<size;i++) {
            ArrayList<SentPicBean> pics = new ArrayList<>();
            for (int j=0,sizes=i;j<sizes;j++) {
                pics.add(new SentPicBean(pic[j]));
            }
            if (i>5) {
                mItems.add(new DynamicsTempItems(pics, pic[i/2], "大陆" + i, "天天养乐都~~~~~~~~~~~~~~~~~~~", 1 + "" + i + "之前", "洪，成，你，那，里"));

            }else {
                mItems.add(new DynamicsTempItems(pics, pic[i], "大陆" + i, "天天养乐都~~~~~~~~~~~~~~~~~~~", 1 + "" + i + "之前", "洪，成，你，那，里"));
            }}
        dynamicsItemAdapter.setDatas(mItems);
    }
}

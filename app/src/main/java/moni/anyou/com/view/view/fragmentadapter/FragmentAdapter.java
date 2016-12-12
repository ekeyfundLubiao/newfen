package moni.anyou.com.view.view.fragmentadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);  
        this.fragmentList = fragmentList;
    }
    
    public FragmentAdapter(FragmentManager fm)
    {
    	super(fm); 
    }
    @Override  
    public Fragment getItem(int position) {
        return fragmentList.get(position);  
    }  
    @Override  
    public int getCount() {  
        return fragmentList.size();  
    } 
    
    public void setList(List<Fragment> list)
    {
    	if(null!=list)
    	{
    		fragmentList.clear();
    		fragmentList.addAll(list);
    	}
    }
}  

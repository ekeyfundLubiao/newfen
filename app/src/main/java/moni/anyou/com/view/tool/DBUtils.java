package moni.anyou.com.view.tool;
import java.util.ArrayList;
import java.util.List;
import org.kymjs.aframe.database.KJDB;


import android.content.Context;

import moni.anyou.com.view.bean.DAO_DATA_Info;

public class DBUtils {

	public KJDB kjdb;
    private Context context;

	
    public void drop()
    {
    	kjdb.dropDb();
    }

	public DBUtils(Context context) {
		super();
		this.context =context;
		 initData();
	}

	protected void initData() {
		kjdb = KJDB.create(context);

//		kjdb.save(javaBean);
	}
	@SuppressWarnings("unused")
	public  void add(DAO_DATA_Info dao) {
		kjdb.save(dao);
	}
	//按条件查询
	public List<DAO_DATA_Info> queryAll_DAO_DATA_Info(String str) {
		List<DAO_DATA_Info> datas = kjdb.findAllByWhere(DAO_DATA_Info.class, str);
		if(datas==null)datas=new ArrayList<DAO_DATA_Info>();
		return datas;
	}

	//修改
	public void update_DAO_DATA_Info(DAO_DATA_Info entity) {
	  	kjdb.update(entity);
	}
	public void delete_DAO_DATA_Info() {
		 // 可以直接这样删掉，这里我们用sql语句的方法

		kjdb.deleteByWhere(DAO_DATA_Info.class, "key=1");
	}

}

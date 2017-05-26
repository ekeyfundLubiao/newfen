package com.m086.ad.mobi.tool;
import java.util.ArrayList;
import java.util.List;
import org.kymjs.aframe.database.KJDB;

import android.content.Context;

import com.m086.ad.mobi.bean.DAO_DATA_Info;

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
	//��������
	public List<DAO_DATA_Info> queryAll_DAO_DATA_Info(String str) {
		List<DAO_DATA_Info> datas = kjdb.findAllByWhere(DAO_DATA_Info.class, str);
		if(datas==null)datas=new ArrayList<DAO_DATA_Info>();
		return datas;
	}

	//�޸�
	public void update_DAO_DATA_Info(DAO_DATA_Info entity) {
	  	kjdb.update(entity);
	}
	public void delete_DAO_DATA_Info() {
		// ����ֱ������ɾ��������������sql���ķ���
		kjdb.deleteByWhere(DAO_DATA_Info.class, "key=1");
	}

}

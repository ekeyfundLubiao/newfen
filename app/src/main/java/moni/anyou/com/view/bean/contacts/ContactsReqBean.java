package moni.anyou.com.view.bean.contacts;

import android.content.Context;

import java.util.List;

public class ContactsReqBean  {

//	public ContactsReqBean(Context context) throws Exception {
//		super(context);
//		transType = RequestUrlGather.MobileInfo_TransType;
//	}
	public List<Contact> list;
	public static class Contact{
		public String name;
		public String mobile;
	}
}

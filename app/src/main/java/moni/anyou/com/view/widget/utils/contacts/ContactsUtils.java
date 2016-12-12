package moni.anyou.com.view.widget.utils.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;



public class ContactsUtils {

//	public static List<Contact> getContacts(Context context) {
//		List<Contact> myContacts = new ArrayList<Contact>();
//		try {
//			Cursor cursor = context.getContentResolver().query(
//					ContactsContract.Contacts.CONTENT_URI, null, null, null,
//					null);
//			int contactIdIndex = 0;
//			int nameIndex = 0;
//			if (null != cursor) {
//				if (cursor.getCount() > 0) {
//					contactIdIndex = cursor
//							.getColumnIndex(ContactsContract.Contacts._ID);
//					nameIndex = cursor
//							.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//				}
//				while (cursor.moveToNext()) {
//					String contactId = cursor.getString(contactIdIndex);
//					String name = cursor.getString(nameIndex);
//					/*
//					 * 查找该联系人的phone信息
//					 */
//					Cursor phones = context.getContentResolver().query(
//							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//							null,
//							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//									+ "=" + contactId, null, null);
//					int phoneIndex = 0;
//					if (phones.getCount() > 0) {
//						phoneIndex = phones
//								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//					}
//					while (phones.moveToNext()) {
//						String phoneNumber = phones.getString(phoneIndex);
//						Contact contact = new Contact();
//						contact.name = name;
//						contact.mobile = phoneNumber;
//						myContacts.add(contact);
//					}
//				}
//			}
//		} catch (Exception e) {
//
//		}
//		return myContacts;
//	}
}

package com.m086.ad.mobi.view.my.invitefamily;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.view.my.invitefamily.adapter.SelectItemslAdapter;

public class SelectActivity extends BaseActivity {

    private ListView lvSelect;
    SelectItemslAdapter selectItemslAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("通讯录");
        lvSelect = (ListView) findViewById(R.id.lv_select);
        selectItemslAdapter = new SelectItemslAdapter(mContext);
        lvSelect.setAdapter(selectItemslAdapter);
        lvSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastTools.showShort(mContext,selectItemslAdapter.getItem(position).name);
                Intent intent = new Intent();
                intent.putExtra("account", selectItemslAdapter.getItem(position).number);
                setResult(0x1111, intent);
                onBack();
            }
        });
    }

    @Override
    public void setData() {
        super.setData();
        loadContacts();
    }

    ArrayList<Contact> mAllContactsList;
    private void loadContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ContentResolver resolver = getApplicationContext().getContentResolver();
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key"}, null, null, "sort_key COLLATE LOCALIZED ASC");
                    if (phoneCursor == null || phoneCursor.getCount() == 0) {
                        //  setviewEmpty(true);
                        Toast.makeText(getApplicationContext(), "未获得读取联系人权限 或 未获得联系人数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");
                    if (phoneCursor.getCount() > 0) {
                        mAllContactsList = new ArrayList<Contact>();
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                            String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
                            Contact sortModel = new Contact(contactName, phoneNumber, sortKey);
                            mAllContactsList.add(sortModel);
                        }
                    }
                    phoneCursor.close();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (mAllContactsList.size() > 0) {
                                selectItemslAdapter.setDatas(mAllContactsList);
                                // setviewEmpty(false);
                            } else {
                                //  setviewEmpty(true);
                            }

                        }
                    });
                } catch (Exception e) {
                    Log.e("xbc", e.getLocalizedMessage());
                }
            }
        }).start();
    }

    public class Contact {
        public Contact() {

        }

        public Contact(String name, String number, String sortKey) {
            this.name = name;
            this.number = number;
            this.sortKey = sortKey;
            if (number != null) {
                this.simpleNumber = number.replaceAll("\\-|\\s", "");
            }
        }

        public String name;
        public String number;
        public String simpleNumber;
        public String sortKey;


        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((number == null) ? 0 : number.hashCode());
            result = prime * result + ((sortKey == null) ? 0 : sortKey.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Contact other = (Contact) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (number == null) {
                if (other.number != null)
                    return false;
            } else if (!number.equals(other.number))
                return false;
            if (sortKey == null) {
                if (other.sortKey != null)
                    return false;
            } else if (!sortKey.equals(other.sortKey))
                return false;
            return true;
        }
    }
}

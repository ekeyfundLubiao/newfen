package moni.anyou.com.view.tool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.view.account.LoginActivity;


public class Tools {

	public static String replace(String strSource, String strFrom, String strTo) {

		if (strFrom == null || strFrom.equals(""))
			return strSource;
		String strDest = "";

		int intFromLen = strFrom.length();
		int intPos;

		while ((intPos = strSource.indexOf(strFrom)) != -1) {

			strDest = strDest + strSource.substring(0, intPos);

			strDest = strDest + strTo;

			strSource = strSource.substring(intPos + intFromLen);
		}

		strDest = strDest + strSource;

		return strDest;
	}

	public static String substring(String origin, String begin, String end, int has) {
		int first = origin.indexOf(begin);
		int last = origin.indexOf(end);
		String result = "";
		if (first > -1 && last > -1) {
			if (first < last) {
				if (has == 1) {
					result = substring(origin, first, (last - first) + end.length());
				} else {
					result = substring(origin, first + begin.length(), last - first - begin.length());
				}
			} else {
				origin = origin.substring(last + end.length());
				result = substring(origin, begin, end, has);
			}
		} else {
			return "";
		}
		return result;
	}

	public static int findIndex(String origin, int first, String end) {
		int lastIndex = -1;
		String temp = origin;
		int k = 1;
		while (temp.indexOf(end) > -1) {
			// System.out.println(lastIndex);
			// System.out.println(temp);
			lastIndex = lastIndex + temp.indexOf(end);
			if (lastIndex > first) {
				return lastIndex;
			} else {
				temp = temp.substring(lastIndex + end.length());
			}
		}
		return lastIndex;
	}

	public static String substring(String origin, int begin, int len) {
		if (origin == null)
			return origin;
		int sBegin = begin < 0 ? 0 : begin;
		if (len < 1 || sBegin > origin.length())
			return "";
		if (len + sBegin > origin.length())
			return origin.substring(sBegin);
		char c[] = origin.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = sBegin; i < sBegin + len; i++)
			sb.append(c[i]);

		return sb.toString();
	}

	public static String readTextFile(File f) throws IOException {

		StringBuffer buf = new StringBuffer();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
		// BufferedReader in = new BufferedReader(new InputStreamReader(new
		// FileInputStream(f)));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			buf.append(inputLine);
			buf.append('\n');
		}
		in.close();
		return buf.toString();
	}

	public static String parseLoginMsg(JSONObject msgJson) {
		try {
			JSONArray array = null;

			String result = msgJson.get("result").toString();

			if (Integer.parseInt(result) > 0) {
				SysConfig.token = msgJson.get("token").toString();

				SysConfig.lastupdatetime = Long.parseLong(msgJson.get("time").toString());
				array = msgJson.getJSONArray("userinfo");
				if (array.length() > 0) {
					SysConfig.userInfoJson = (JSONObject) array.get(0);
				}
				SysConfig.uid = SysConfig.userInfoJson.getString("user_id").toString();
//				SysConfig.username = SysConfig.userInfoJson.getString("username");
//				JSONArray oneKeyInfoArray=msgJson.getJSONArray("onekeyInfo");
//
//				SysConfig.onkeyset=(JSONObject) oneKeyInfoArray.get(0);
//                SysConfig.configInfo= (JSONObject)msgJson.getJSONArray("configInfo").get(0);

				return "1";

			} else {
				return "";
			}
		} catch (Exception ex) {
			Log.e("TAG", "parseLoginMsg: "+ex.getMessage() );
			return "";
		}
	}

	public static String[] split(String strin, char c, int it) {
		ArrayList arraylist = new ArrayList();
		char[] chark = strin.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = it; i < chark.length; i++) {
			if (chark[i] == c) {
				arraylist.add(sb.toString());
				sb = new StringBuffer();
			} else {
				sb.append(chark[i]);
				if (i == chark.length - 1) {
					arraylist.add(sb.toString());
				}
			}
		}
		int k = arraylist.size();
		String as[] = new String[k];
		return (String[]) arraylist.subList(0, k).toArray(as);
	}

	/*
	 *
	 */
	public static String postMsg(String serverUrl, String cmd) {
		System.out.println(cmd);
		String sTotalString = "";
		String sCurrentLine = "";
		java.io.InputStream l_urlStream = null;
		URL l_url = null;
		HttpURLConnection l_connection = null;

		try {
			l_url = new URL(serverUrl);
			l_connection = (HttpURLConnection) l_url.openConnection();
			l_connection.setRequestMethod("POST");//
			l_connection.setConnectTimeout(5000);//
			// conn.setReadTimeout(2000);
			l_connection.setDoOutput(true);//

			byte[] bypes = cmd.getBytes();
			l_connection.getOutputStream().write(bypes);//
			l_urlStream = l_connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(
					new InputStreamReader(l_urlStream, "utf-8"));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine;
			}
			System.out.println(sTotalString);
		} catch (Exception ex) {
			Log.d("order cmd", ex.toString());
			return "{\"result\":\"-1\"}";
		} finally {
			try {
				l_urlStream.close();
				l_connection = null;
				l_url = null;
			} catch (Exception ex) {

			}
		}

		return sTotalString;

	}

	//
	public static String getCmdString(String[][] keyword) {
		String result = "{";
		for (int i = 0; i < keyword.length; i++) {
			if (i == 0) {
				result += "\"" + keyword[i][0] + "\":\"" + keyword[i][1] + "\"";
			} else {
				result += ",\"" + keyword[i][0] + "\":\"" + keyword[i][1] + "\"";
			}
		}
		result += "}";
		return result;
	}

	public static String[] split(String strin, String c) {
		if (strin == null) {
			return null;
		}
		ArrayList arraylist = new ArrayList();
		int begin = 0;
		int end = 0;
		while ((begin = strin.indexOf(c, end)) != -1) {
			String s2 = strin.substring(end, begin);
			if (s2.trim().length() > 0) { //
				arraylist.add(strin.substring(end, begin));
			}
			end = begin + c.length();
		}
		if (end != strin.length()) {
			arraylist.add(strin.substring(end));
		}
		int k = arraylist.size();
		String as[] = new String[k];
		return (String[]) arraylist.subList(0, k).toArray(as);
	}

	public static String getKindTitle(String module, String kindIdList) {
		String result = "";
		String[] str = Tools.split(kindIdList, ",");
		int k = 0;
		try {
			if (module.equals("language")) {
				JSONArray arr = SysConfig.dataJson.getJSONArray("LanguageList");
				for (int j = 0; j < str.length; j++) {
					for (int i = 0; i < arr.length(); i++) {
						if (((JSONObject) arr.get(i)).getString("keyword").equals(str[j])) {
							if (k == 0) {
								result += ((JSONObject) arr.get(i)).getString("title");
							} else {
								result += "," + ((JSONObject) arr.get(i)).getString("title");
							}
							k++;
							break;
						}
					}
				}
			} else {
				JSONArray arr = SysConfig.dataJson.getJSONArray("KindList");
				for (int j = 0; j < str.length; j++) {
					for (int i = 0; i < arr.length(); i++) {
						if (((JSONObject) arr.get(i)).getString("id").equals(str[j])) {
							if (k == 0) {
								result += ((JSONObject) arr.get(i)).getString("title");
							} else {
								result += "," + ((JSONObject) arr.get(i)).getString("title");
							}
							k++;
							break;
						}
					}
				}
			}
		} catch (Exception ex) {

		}
		return result;
	}

	public static JSONArray getModuleJsonArray(String module) {

		JSONArray jsonArr = new JSONArray();
		try {
			for (int i = 0; i < SysConfig.dataJson.getJSONArray("KindList").length(); i++) {
				JSONObject json = (JSONObject) SysConfig.dataJson.getJSONArray("KindList").get(i);
				if ((json.getString("module").equals(module))) {
					jsonArr.put(json);

				}
			}
		} catch (Exception ex) {

		}
		return jsonArr;
	}

	// 得到父类下的所有直接子类
	public static JSONArray getCategoryJsonArray(String module, String pid) {
		JSONArray jsonArr = new JSONArray();

		try {
			JSONArray arr = SysConfig.dataJson.getJSONArray("CategoryList");
			;
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = (JSONObject) arr.get(i);
				if (json.getString("module").equals(module) && json.getString("pid").equals(pid)) {
					jsonArr.put(json);
				}
			}
		} catch (Exception ex) {

		}
		return jsonArr;
	}

	public static JSONObject getModuleJsonObjct(JSONArray arr, String id) {
		try {
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = (JSONObject) arr.get(i);
				if ((json.getString("id").equals(id))) {
					return json;

				}
			}
		} catch (Exception ex) {

		}
		return null;
	}

	// 得到多级数据
	public static void getCategoryTitleList(JSONArray arr, String language, String id, StringBuffer sb) {
		try {
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = (JSONObject) arr.get(i);
				if ((json.getString("id").equals(id))) {
					String pid = json.getString("pid");
					sb.append(json.getString("title") + ",");
					if (!pid.equals("0")) {
						getCategoryTitleList(arr, language, pid, sb);
					} else {
						break;
					}
				}
			}
		} catch (Exception ex) {

		}

	}

	/*
	 * 得到相对于本周的第几周的的某一天日期 本周 nextWeek = 0 someday:1到7，代表周一，周二等等
	 */
	public static String getMondayOfThisWeek(int nextWeek, int someday) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + someday + nextWeek * 7);
		return df.format(c.getTime());
	}

	// 得到某一天是否锁定
	public static boolean getIsLockOfDay(String currday, JSONArray scheduleList) {
		int result = 0;
		for (int i = 0; i < scheduleList.length(); i++) {
			try {
				JSONObject json = (JSONObject) scheduleList.get(i);
				if (json.getString("currday").equals(currday)) {
					if (!json.getString("timelist").equals("")) {
						return true;
					}
				}
			} catch (Exception ex) {

			}
		}
		return false;
	}

	// 得到某一天的预约时间统计
	public static int getHourCount(String currday, JSONArray appointArr) {
		int result = 0;
		for (int i = 0; i < appointArr.length(); i++) {

			try {
				JSONObject json = (JSONObject) appointArr.get(i);
				if (json.getString("currday").equals(currday)) {
					if (!json.getString("timelist").equals("")) {
						String[] temp = Tools.split(json.getString("timelist"), ",");
						result += temp.length;
					}
				}
			} catch (Exception ex) {

			}
		}
		return result;
	}

	private static String fileName = ""; //
	private static String path = Environment.getExternalStorageDirectory().getPath(); // Don't

	public static String saveFile(Bitmap bm) throws IOException {
		Calendar now = Calendar.getInstance();
		fileName = SysConfig.uid + "_" + now.get(Calendar.YEAR) + "_" + now.get(Calendar.MONTH) + 1 + "_"
				+ now.get(Calendar.DAY_OF_MONTH) + "_" + now.get(Calendar.HOUR_OF_DAY) + "_" + now.get(Calendar.MINUTE)
				+ "_" + now.get(Calendar.SECOND) + ".jpg";
		File myCaptureFile = new File(path + "/" + SysConfig.File_DIR + "/" + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
		return fileName;
	}

	public static String getNowtime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm.ss     ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static void jumpToLogin(Context cox) {

		cox.startActivity(new Intent(cox, LoginActivity.class));

	}

    public static void showToast(Context cox, String tips){
        Toast.makeText(cox,tips,Toast.LENGTH_SHORT).show();
    }

}

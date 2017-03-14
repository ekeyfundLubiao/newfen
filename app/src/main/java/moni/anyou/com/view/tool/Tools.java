package moni.anyou.com.view.tool;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import moni.anyou.com.view.bean.DataClassBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.widget.pikerview.Utils.TextUtil;

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

    public static String substring(String origin, String begin, String end,
                                   int has) {
        int first = origin.indexOf(begin);
        int last = origin.indexOf(end);
        String result = "";
        if (first > -1 && last > -1) {
            if (first < last) {
                if (has == 1) {
                    result = substring(origin, first,
                            (last - first) + end.length());
                } else {
                    result = substring(origin, first + begin.length(), last
                            - first - begin.length());
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

        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), "UTF-8"));
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

                SysConfig.lastupdatetime = msgJson.get("time").toString();
                array = msgJson.getJSONArray("userinfo");
                if (array.length() > 0) {
                    SysConfig.userInfoJson = (JSONObject) array.get(0);
                }
                SysConfig.uid = SysConfig.userInfoJson.getString("user_id").toString();

                return "1";

            } else {
                return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    public static void eixtData() {

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
                result += ",\"" + keyword[i][0] + "\":\"" + keyword[i][1]
                        + "\"";
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

    //取得分类数据
    public static JSONArray getModuleJsonArray(String module) {

        JSONArray jsonArr = new JSONArray();
        try {
            for (int i = 0; i < SysConfig.dataJson.getJSONArray("ClassList")
                    .length(); i++) {
                JSONObject json = (JSONObject) SysConfig.dataJson.getJSONArray("ClassList").get(i);
                if ((json.getString("module").equals(module))) {
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

    public static boolean getTimeCompare(String currday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date time1 = null;
        Date time2 = null;
        try {
            time1 = sdf.parse(currday);//
            time2 = sdf.parse(sdf.format(new Date()));
            if (time1.getTime() < time2.getTime()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {

            return false;
        }

    }

    private static String fileName = ""; //
    private static String path = Environment.getExternalStorageDirectory()
            .getPath(); // Don't

    public static String saveFile(Bitmap bm) throws IOException {
        Calendar now = Calendar.getInstance();
        fileName = Md5.MD5Encode(SysConfig.uid + "_" + now.get(Calendar.YEAR)
                + "_" + now.get(Calendar.MONTH) + 1 + "_"
                + now.get(Calendar.DAY_OF_MONTH) + "_"
                + now.get(Calendar.HOUR_OF_DAY) + "_"
                + now.get(Calendar.MINUTE) + "_" + now.get(Calendar.SECOND))
                + ".jpg";
        File myCaptureFile = new File(path + "/" + SysConfig.File_DIR + "/"
                + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return fileName;
    }

    public static String getNowtime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm.ss     ");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getForTime(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "MM-dd  HH:mm");
        Date tmpDate = (new SimpleDateFormat("MM-dd  HH:mm")).parse(data, new ParsePosition(0));

        String str = formatter.format(tmpDate);
        return str;
    }

    public static String transferJson(String str) {
        return str.replaceAll("\"", "'").replaceAll("'“", "'")
                .replaceAll("'”", "'").replaceAll("\r\n", "<br/>")
                .replaceAll("\r", "<br/>").replaceAll("CHAR(10)", "<br/>")
                .replaceAll("\n", "<br/>").replaceAll("\t", "");
    }

    public static String transferJsonToString(String str) {
        return str.replaceAll("&lt;br/&gt;", "\n");
    }


    public static ArrayList<DataClassBean> getBaseRelatenumberdatas() {

        try {
            return new Gson().fromJson(getModuleJsonArray("relative").toString(), new TypeToken<List<DataClassBean>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRole(String roleId) {
        ArrayList<DataClassBean> temp = getBaseRelatenumberdatas();
        String tempStr;
        int size = temp.size();
        for (int i = 0; i < size; i++) {

            if ((roleId.equals(temp.get(i).getClassID()))) {
                return temp.get(i).getClassName();

            }
        }
        return null;
    }

    public static String getRoleId(String role) {
        ArrayList<DataClassBean> temp = getBaseRelatenumberdatas();
        String tempStr;
        int size = temp.size();
        for (int i = 0; i < size; i++) {

            if ((role.equals(temp.get(i).getClassName()))) {
                return temp.get(i).getClassID();

            }
        }
        return "亲戚";
    }

    public static String getRoledefaultIcon(String roleId) {
        ArrayList<DataClassBean> temp = getBaseRelatenumberdatas();
        String tempStr;
        int size = temp.size();
        for (int i = 0; i < size; i++) {

            if ((roleId.equals(temp.get(i).getClassID()))) {
                return temp.get(i).getPic();

            }
        }
        return "icon";
    }

    public static ArrayList<KeyVaule> getLikeNikeName(String like) {
        ArrayList<KeyVaule> likelist = new ArrayList<>();
        String[] likeArray = like.split("\\|");
        int size = likeArray.length;

        for (int i = 0; i < size; i++) {
            String[] tempStr = likeArray[i].split(":::");
            KeyVaule tempBean = new KeyVaule();
            tempBean.nickName = tempStr[1];
            tempBean.userId = tempStr[0];
            likelist.add(tempBean);
        }
        return likelist;
    }

    public static String getLikeNikeNameStr(String like) {
        ArrayList<KeyVaule> likelist = getLikeNikeName(like);
        int size = likelist.size();
        like = "";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                like = like + likelist.get(i).nickName + ",";
            } else {
                like = like + likelist.get(i).nickName;
            }

        }
        return like;
    }

    public static class KeyVaule {
        public boolean islikeMark;
        public String userId;
        public String nickName;

    }

    public static String main(String timeStr) {

        if (!TextUtil.isEmpty(timeStr)) {

            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss.S");
            try {
                Date someDate = formatter.parse(timeStr);
                Date curDate = new Date(System.currentTimeMillis());
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(curDate);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(someDate);
                boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
                boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
                if (isSameDay(curDate, someDate)) {
                    int distanceTime = cal1.get(Calendar.HOUR) - cal2.get(Calendar.HOUR);
                    return distanceTime + "小时前";
                } else {
                    if (isSameMonth) {
                        int distanceDay = cal1.get(Calendar.DAY_OF_MONTH) - cal2.get(Calendar.DAY_OF_MONTH);
                        return distanceDay + "天之前";
                    } else if (isSameYear) {
                        int distanceMontn = cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
                        return distanceMontn + "月之前";
                    } else {
                        return timeStr;
                    }

                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean isSameDay(Date nowdate, Date somedate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(nowdate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(somedate);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;

    }
}

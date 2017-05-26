package com.m086.ad.mobi.tool;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationTools {
	/**
	 * 手机号验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		if (isNull(str)) {
			return false;
		}
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isNull(String str) {
		if ((null != str) && (!("").equals(str))) {
			return false;
		}
		return true;
	}

	/**
	 * 判断网络字符串是否为空
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isHttpNull(String str) {
		if ((null != str) && (!("").equals(str)) && (!("null").equals(str))) {
			return false;
		}
		return true;
	}

	/**
	 * 密码长度是否对的8--16位
	 *
	 * @param str
	 * @return 隐藏后的phonenum
	 */
	public static boolean pswIsLegitimate(String psw) {
		if ((psw.length() > 5) && (psw.length() < 17)) {
			return true;
		}
		return false;
	}

	/**
	 * 隐藏手机号码中间4位
	 *
	 * @param str
	 * @return 隐藏后的phonenum
	 */
	public static String getEncryptionPhoneNumber(String phoneNum) {
		if (!(!isNull(phoneNum) && isMobile(phoneNum))) {
			return "";
		}
		return phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * 判断是否为邮箱
	 *
	 * @param str
	 *
	 */
	public static Boolean isEmail(String str) {
		Boolean isEmail = false;
		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (str.matches(expr)) {
			isEmail = true;
		}
		return isEmail;
	}

	/**
	 * 千位符
	 *
	 * @param str
	 *
	 */
	public static String addSeparator(String str) {
		if (isHttpNull(str)) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#,###.####");
		try {
			return df.format(Double.parseDouble(str));
		} catch (Exception e) {
			return "0";
		}
	}

	public static String doubleAddSeparator(String str) {
		if (isHttpNull(str)) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
		try {
			return df.format(Double.parseDouble(str));
		} catch (Exception e) {
			return "0.00";
		}
	}

	public static String getTwoDouble(double number) {
		DecimalFormat df = new DecimalFormat("######0.00");
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(number);
	}


	/**
	 * 获得积分
	 *
	 * @param money
	 * @param time
	 */
	public static int getIntegral(double money,double time)
	{
		return (int) (money*time/12);
	}

	/**
	 * 收益
	 *
	 * @param money
	 * @param rate
	 * @param type
	 */
	public static double getEstimate(double money, double rate, int timeNum,
									 String type, String repaymentType) {
		String estimate = "0.00";
		try {
			switch (repaymentType) {
				// 按月付息到期还本
				case "MONTHINTEREST":
					if (type.equals("月")) {
						estimate = getTwoDouble(Double
								.parseDouble(getTwoDouble(money * rate
										/ (12.00 * 100)))
								* timeNum);
					} else if (type.equals("天")) {
						estimate = getTwoDouble(Double
								.parseDouble(getTwoDouble(money * rate
										/ (360.00 * 100)))
								* timeNum);
					} else {
						estimate = "0.00";
					}
					break;
				// 一次性还本付息
				case "RTCAPITALINTEREST":
					if (type.equals("月")) {
						estimate = getTwoDouble(money * rate * timeNum
								/ (12.00 * 100));
					} else if (type.equals("天")) {
						estimate = getTwoDouble(money * rate * timeNum
								/ (360.00 * 100));
					} else {
						estimate = "0.00";
					}
					break;
				default:
					break;
			}
		} catch (Exception e) {
			estimate = "0.00";
		}
		return Double.parseDouble(estimate);
	}

	/**
	 * 获取加密的手机号
	 *
	 * @param money
	 * @param rate
	 * @param type
	 */
	public static String getEncryptionPhone(String phone) {
		if (!isNull(phone) && phone.length() > 6) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < phone.length(); i++) {
				char c = phone.charAt(i);
				if (i >= 3 && i <= 6)
					sb.append('*');
				else
					sb.append(c);
			}
			return sb.toString();
		} else {
			return phone;
		}
	}

	/**
	 * 判断是否为邮编
	 *
	 * @param zipCode
	 */
	public static boolean isZipCode(String zipCode) {
		String reg = "[1-9]\\d{5}";
		return Pattern.matches(reg, zipCode);
	}
}

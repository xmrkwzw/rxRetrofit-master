/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xmjj.jujianglibrary.util;

import android.annotation.SuppressLint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 描述：字符串处理类.
 */
public final class StringUtil {

	final static String tag = "StringTool";

	/**
	 * 截取字符串
	 *
	 * @param str
	 * @param i   要截取到第几位
	 * @return
	 */
	public static String cutString(String str, int i) {
		if (str.length() > i) {
			return str.substring(0, i);
		}
		return str;
	}

	/**
	 * 描述：将null转化为“”.
	 *
	 * @param str 指定的字符串
	 * @return 字符串的String类型
	 */
	public static String parseEmpty(String str) {
		if (str == null || "null".equals(str.trim())) {
			str = "";
		}
		return str.trim();
	}

	/**
	 * 描述：判断一个字符串是否为null或空值.
	 *
	 * @param str 指定的字符串
	 * @return true or false
	 */
	public static boolean isEmpty(String str) {
		Boolean isEmpty = str == null || str.trim().length() == 0;
		return isEmpty;
	}

	/**
	 * 描述：获取字符串的长度.
	 *
	 * @param str 指定的字符串
	 * @return 字符串的长度（中文字符计2个）
	 */
	public static int strLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			//获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				//获取一个字符
				String temp = str.substring(i, i + 1);
				//判断是否为中文字符
				if (temp.matches(chinese)) {
					//中文字符长度为2
					valueLength += 2;
				} else {
					//其他字符长度为1
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}

	/**
	 * 描述：手机号格式验证.
	 *
	 * @param str 指定的手机号码字符串
	 * @return 是否为手机号码格式:是为true，否则false
	 */
	public static Boolean isMobileNo(String str) {
		Boolean isMobileNo = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(str);
			isMobileNo = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMobileNo;
	}


	/**
	 * 描述：是否只是字母和数字.
	 *
	 * @param str 指定的字符串
	 * @return 是否只是字母和数字:是为true，否则false
	 */
	public static Boolean isNumberLetter(String str) {
		Boolean isNoLetter = false;
		String expr = "^[A-Za-z0-9]+$";
		if (str.matches(expr)) {
			isNoLetter = true;
		}
		return isNoLetter;
	}

	/**
	 * 描述：是否只是数字.
	 *
	 * @param str 指定的字符串
	 * @return 是否只是数字:是为true，否则false
	 */
	public static boolean isNumberic(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 描述：是否是邮箱.
	 *
	 * @param str 指定的字符串
	 * @return 是否是邮箱:是为true，否则false
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
	 * 描述：是否只是中文.
	 *
	 * @param str 指定的字符串
	 * @return 是否是中文:是为true，否则false
	 */
	public static Boolean isChinese(String str) {
		Boolean isChinese = true;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			//获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				//获取一个字符
				String temp = str.substring(i, i + 1);
				//判断是否为中文字符
				if (temp.matches(chinese)) {
				} else {
					isChinese = false;
				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：是否包含中文.
	 *
	 * @param str 指定的字符串
	 * @return 是否包含中文:是为true，否则false
	 */
	public static Boolean isContainChinese(String str) {
		Boolean isChinese = false;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			//获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				//获取一个字符
				String temp = str.substring(i, i + 1);
				//判断是否为中文字符
				if (temp.matches(chinese)) {
					isChinese = true;
				} else {

				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：从输入流中获得String.
	 *
	 * @param is 输入流
	 * @return 获得的String
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			//最后一个\n删除
			if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
				sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


	/**
	 * 描述：不足2个字符的在前面补“0”.
	 *
	 * @param str 指定的字符串
	 * @return 至少2个字符的字符串
	 */
	public static String strFormat2(String str) {
		try {
			if (str.length() <= 1) {
				str = "0" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}


	/**
	 * 描述：截取字符串到指定字节长度.
	 *
	 * @param str    文本
	 * @param length 字节长度
	 * @param dot    省略符号
	 * @return 截取后的字符串
	 */
	public static String cutString(String str, int length, String dot) {
		int strBLen = strlen(str, "GBK");
		if (strBLen <= length) {
			return str;
		}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = str.toCharArray();
		for (char c : ch) {
			sb.append(c);
			if (c > 256) {
				temp += 2;
			} else {
				temp += 1;
			}
			if (temp >= length) {
				if (dot != null) {
					sb.append(dot);
				}
				break;
			}
		}
		return sb.toString();
	}


	/**
	 * 描述：获取字节长度.
	 *
	 * @param str     文本
	 * @param charset 字符集（GBK）
	 * @return the int
	 */
	public static int strlen(String str, String charset) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		int length = 0;
		try {
			length = str.getBytes(charset).length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}

	/**
	 * 获取大小的描述.
	 *
	 * @param size 字节个数
	 * @return 大小的描述
	 */
	public static String getSizeDesc(long size) {
		String suffix = "B";
		if (size >= 1024) {
			suffix = "K";
			size = size >> 10;
			if (size >= 1024) {
				suffix = "M";
				//size /= 1024;
				size = size >> 10;
				if (size >= 1024) {
					suffix = "G";
					size = size >> 10;
					//size /= 1024;
				}
			}
		}
		return size + suffix;
	}

	/**
	 * 描述：ip地址转换为10进制数.
	 *
	 * @param ip the ip
	 * @return the long
	 */
	public static long ip2int(String ip) {
		ip = ip.replace(".", ",");
		String[] items = ip.split(",");
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
	}

	/**
	 * 返回当前系统时间
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDataTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 毫秒值转换为mm:ss
	 *
	 * @param ms
	 * @author kymjs
	 */
	public static String timeFormat(int ms) {
		StringBuilder time = new StringBuilder();
		time.delete(0, time.length());
		ms /= 1000;
		int s = ms % 60;
		int min = ms / 60;
		if (min < 10) {
			time.append(0);
		}
		time.append(min).append(":");
		if (s < 10) {
			time.append(0);
		}
		time.append(s);
		return time.toString();
	}

	/**
	 * 字符串转整数
	 *
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整
	 *
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null) {
			return 0;
		}
		return toInt(obj.toString(), 0);
	}

	/**
	 * String转long
	 *
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * String转double
	 *
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static double toDouble(String obj) {
		try {
			return Double.parseDouble(obj);
		} catch (Exception e) {
		}
		return 0D;
	}

	/**
	 * 字符串转布尔
	 *
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/***
	 * 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，
	 * 使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。
	 * 半角转为全角的代码如下，只需调用即可
	 *
	 * @param text
	 * @return
	 */
	public static String ToDBC(String text) {
		char[] c = text.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375) {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号。利用正则表达式将所有特殊字符过滤，
	 * 或利用replaceAll（）将中文标号替换为英文标号。则转化之后，则可解决排版混乱问题。
	 *
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");//替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}


	/**
	 * 把一个字符串中的大写转为小写，小写转换为大写
	 *
	 * @param str
	 * @param isChangeUpper true：转大写 false:转小写
	 * @return
	 */
	public static String exChangeUpLow(String str, boolean isChangeUpper) {

		if (str.equals("")) {
			return "";
		}


		StringBuffer sb = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);

				if (isChangeUpper) {

					if (Character.isLowerCase(c)) {
						sb.append(Character.toUpperCase(c));   //大写
					} else {
						sb.append(c);
					}

				} else {

					if (Character.isUpperCase(c)) {
						sb.append(Character.toLowerCase(c));  //小写
					} else {
						sb.append(c);
					}

				}

			}
		}

		return sb.toString();
	}


	// 得到发布时间和当前时间的比较值
	public static String getFormatTime(long publishTime) {
		long _currentTime = System.currentTimeMillis();
		long _disMinute = (_currentTime - publishTime) / 1000 / 60;// 得到分钟差值
		System.out.println(_disMinute);
		long _disHour;
		long _disMinutes;
		String result;
		if (_disMinute > (60 * 24)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			result = sdf.format(publishTime);
		} else if (_disMinute > 60) {
			_disHour = _disMinute / 60;
			_disMinutes = _disMinute % 60;
			result = _disHour + "小时" + _disMinutes + "分钟前";
		} else if (_disMinute > 0) {
			result = _disMinute + "分钟前";
		} else {
			result = "刚刚";
		}
		return result;
	}

	public static String getDownLoadProgress(long total, long current, boolean showAll) {
		float m = 1024 * 1024;
		float _total = (float) total / m;
		float _current = (float) current / m;
		DecimalFormat decimalFormat = new DecimalFormat("0.0");
		StringBuilder sb = new StringBuilder("正在下载 ");

		if (showAll) {

			sb.append(decimalFormat.format(_current)).append("M").append("/").append(decimalFormat.format(_total)).append("M");
		} else {
			sb.append(decimalFormat.format(_total)).append("M");
		}
		return sb.toString();
	}


	final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");

	/**
	 * 获取链接的后缀名
	 *
	 * @return
	 */
	public static String parseSuffix(String url) {

		Matcher matcher = pattern.matcher(url);

		String[] spUrl = url.toString().split("/");
		int len = spUrl.length;
		String endUrl = spUrl[len - 1];

		if (matcher.find()) {
			String[] spEndUrl = endUrl.split("\\?");
			return spEndUrl[0].split("\\.")[1];
		}
		return endUrl.split("\\.")[0];
	}

	public static boolean compareFix(String localName, String serverName) {

		String a = localName.split("\\.")[0];
		int aa = Integer.parseInt(a.split("_")[0]);
		int ab = Integer.parseInt(a.split("_")[1]);

		int ba = Integer.parseInt(serverName.split("_")[0]);
		int bb = Integer.parseInt(serverName.split("_")[1]);

		if (ba > aa) {
			return true;
		} else if (bb > ab) {
			return true;
		} else {
			return false;
		}

	}

	/*去除前后对于的引号*/
	public static String reBuildJson(String json) {
		if (json.startsWith("\"") && json.endsWith("\"")) {
			return json.substring(1, json.length() - 1);
		}

		return json;
	}

	/*unicode解码*/
	public static String decodeUnicode(String str) {
		str = reBuildJson(str);
		Charset set = Charset.forName("UTF-16");
		Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
		Matcher m = p.matcher(str);
		int start = 0;
		int start2 = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(start)) {
			start2 = m.start();
			if (start2 > start) {
				String seg = str.substring(start, start2);
				sb.append(seg);
			}
			String code = m.group(1);
			int i = Integer.valueOf(code, 16);
			byte[] bb = new byte[4];
			bb[0] = (byte) ((i >> 8) & 0xFF);
			bb[1] = (byte) (i & 0xFF);
			ByteBuffer b = ByteBuffer.wrap(bb);
			sb.append(String.valueOf(set.decode(b)).trim());
			start = m.end();
		}
		start2 = str.length();
		if (start2 > start) {
			String seg = str.substring(start, start2);
			sb.append(seg);
		}
		return sb.toString();
	}

	/*判断是否是ip*/
	public static boolean isIP(String str) {

		// 匹配 1
		// String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
		// 匹配 2
		String regex = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";

		// 匹配1 和匹配2均可实现Ip判断的效果
		Pattern pattern = Pattern.compile(regex);

		return pattern.matcher(str).matches();

	}

	/*判断是否是域名*/
	public static boolean isUrl(String str) {
		String regex = "(http://|ftp://|https://){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";
		// 匹配1 和匹配2均可实现Ip判断的效果

		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(str).matches();
	}

	/**
	 * 读取baseurl
	 *
	 * @param url
	 * @return
	 */
	public static String getBasUrl(String url) {
		String head = "";
		int index = url.indexOf("://");
		if (index != -1) {
			head = url.substring(0, index + 3);
			url = url.substring(index + 3);
		}
		index = url.indexOf("/");
		if (index != -1) {
			url = url.substring(0, index + 1);
		}
		return head + url;
	}

}

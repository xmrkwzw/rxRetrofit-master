package com.xmjj.rxretrofit_master.contact;

import java.io.File;

/**
 * 功能描述：常量类
 * Created by huangxy on 2016/12/6.
 */

public class Constant {
	public static final boolean isDebug = false;

	public static final String MD5KEY = "ijinbu";

	public static final boolean DEBUG = true;

	public static final String PACKAGE_NAME = "com.xmjj.rxretrofit_master";


	public static final class MainPage {
		public static final int NET_INDEX = 0;
		public static final int FILE_INDEX = 1;
		public static final int DBFLOW_INDEX = 2;
		public static final int RXBUS_INDEX = 3;
		public static final int GLIDE_INDEX = 4;
		public static final int CAMERA2_INDEX = 5;
		public static final int OTHER_INDEX = 6;
		public static final int SKIN_INDEX = 7;
		public static final int ABOUT_INDEX = 8;
		public static final int BLOG_INDEX = 8;


	}


	// 文件信息
	public static final class FileInfo {

		/**
		 * 文件根路径 *
		 */
		public static final String BASE_FILE_PATH = "banPai" + File.separator;
		//SharedPreferences文件名

		public static final String SHARE_FILE_NAME = "SharedFile";


		//緩存路徑
		public static final int TYPE_CACHE = 0;


		//文件类型:file

		public static final int TYPE_FILE = 1;


		/**
		 * 应用缓存图片保存路径
		 */
		public static final String SAVE_CACHE_PATH = BASE_FILE_PATH + "cache" + File.separator;


		/**
		 * 文件保存路径
		 */
		public static final String SAVE_FILE_PATH = BASE_FILE_PATH + "file" + File.separator;


	}

	public static final class SharePerferencedKey {
		public static final String token = "token";//保存登陆获取的token信息
		public static final String LOCATION = "location";
		public static final String CARD_ID = "cardId";
		public static final String FIRST_INSTALL = "first_install";//判断是否第一次安装
	}


}

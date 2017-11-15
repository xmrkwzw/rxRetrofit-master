package com.xmjj.rxretrofit_master.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	private static String TAG = "FileUtils";
	private static String FILE_WRITING_ENCODING = "UTF-8";
	private static String FILE_READING_ENCODING = "UTF-8";
	public static void openFile(Context context, String path) {
		try {
			openFile(context, new File(path));
		} catch (Exception e) {
			Toast.makeText(context, "没有支持该文件格式的应用，或文件不存在", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static void openFile(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(Uri.fromFile(file), type);
		// 跳转
		context.startActivity(intent);

	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	public static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	public static final String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

	/**
	 * 去除本地路径file://前缀
	 * 
	 * @param path
	 * @return
	 */
	public static String removeLocalPathPrefix(String path) {
		if (TextUtils.isEmpty(path)) {
			return path;
		}
		String absolutePath = null;
		if (path.startsWith("file://")) {
			absolutePath = path.replace("file://", "");
		} else {
			absolutePath = path;
		}
		return absolutePath;
	}

	/**
	 * 获取完整本地路径
	 * 
	 * @param path
	 * @return
	 */
	public static String getCompleteLocalPath(String path) {
		if (TextUtils.isEmpty(path)) {
			return path;
		}
		if (path.startsWith("file://") || path.startsWith("http")) {
			return path;
		} else if (path.startsWith("/")) {
			return "file://" + path;
		} else {
			return "file://" + "/" + path;
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(String path) throws Exception {
		return getFileSize(new File(path));
	}

	/**
	 * 获取文件大小
	 *
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file != null && file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		}
		return size;
	}

	/**
	 * Gets the content:// URI from the given corresponding path to a file
	 *
	 * @param context
	 * @param imageFile
	 * @return content Uri
	 */
	public static Uri getImageContentUri(Context context, File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/images/media");
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}






	public static String readFile(String _sFileName, String _sEncoding) throws Exception {
		StringBuffer buffContent = null;
		String sLine;

		FileInputStream fis = null;
		BufferedReader buffReader = null;
		if (_sEncoding == null || "".equals(_sEncoding)) {
			_sEncoding = FILE_READING_ENCODING;
		}

		try {
			fis = new FileInputStream(_sFileName);
			buffReader = new BufferedReader(new InputStreamReader(fis,
					_sEncoding));
			boolean zFirstLine = "UTF-8".equalsIgnoreCase(_sEncoding);
			while ((sLine = buffReader.readLine()) != null) {
				if (buffContent == null) {
					buffContent = new StringBuffer();
				} else {
					buffContent.append("\n");
				}
				if (zFirstLine) {
					sLine = removeBomHeaderIfExists(sLine);
					zFirstLine = false;
				}
				buffContent.append(sLine);
			}// end while
			return (buffContent == null ? "" : buffContent.toString());
		} catch (FileNotFoundException ex) {
			throw new Exception("要读取的文件没有找到!", ex);
		} catch (IOException ex) {
			throw new Exception("读取文件时错误!", ex);
		} finally {
			// 增加异常时资源的释放
			try {
				if (buffReader != null)
					buffReader.close();
				if (fis != null)
					fis.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static File writeFile(String path, String content, String encoding, boolean isOverride) throws Exception {
		if (TextUtils.isEmpty(encoding)) {
			encoding = FILE_WRITING_ENCODING;
		}
		InputStream is = new ByteArrayInputStream(content.getBytes(encoding));
		return writeFile(is, path, isOverride);
	}

	public static File writeFile(InputStream is, String path, boolean isOverride) throws Exception {
		String sPath = extractFilePath(path);
		if (!pathExists(sPath)) {
			makeDir(sPath, true);
		}

		if (!isOverride && fileExists(path)) {
			if (path.contains(".")) {
				String suffix = path.substring(path.lastIndexOf("."));
				String pre = path.substring(0, path.lastIndexOf("."));
				path = pre + "_" + System.currentTimeMillis() + suffix;
			} else {
				path = path + "_" + System.currentTimeMillis();
			}
		}

		FileOutputStream os = null;
		File file = null;

		try {
			file = new File(path);
			os = new FileOutputStream(file);
			int byteCount = 0;
			byte[] bytes = new byte[1024];

			while ((byteCount = is.read(bytes)) != -1) {
				os.write(bytes, 0, byteCount);
			}
			os.flush();

			return file;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("写文件错误", e);
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 移除字符串中的BOM前缀
	 *
	 * @param _sLine 需要处理的字符串
	 * @return 移除BOM后的字符串.
	 */
	private static String removeBomHeaderIfExists(String _sLine) {
		if (_sLine == null) {
			return null;
		}
		String line = _sLine;
		if (line.length() > 0) {
			char ch = line.charAt(0);
			// 使用while是因为用一些工具看到过某些文件前几个字节都是0xfffe.
			// 0xfeff,0xfffe是字节序的不同处理.JVM中,一般是0xfeff
			while ((ch == 0xfeff || ch == 0xfffe)) {
				line = line.substring(1);
				if (line.length() == 0) {
					break;
				}
				ch = line.charAt(0);
			}
		}
		return line;
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取 路径（包括：Drive+Directroy )
	 *
	 * @param _sFilePathName
	 * @return
	 */
	public static String extractFilePath(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf('/');
		if (nPos < 0) {
			nPos = _sFilePathName.lastIndexOf('\\');
		}

		return (nPos >= 0 ? _sFilePathName.substring(0, nPos + 1) : "");
	}

	/**
	 * 检查指定文件的路径是否存在
	 *
	 * @param _sPathFileName 文件名称(含路径）
	 * @return 若存在，则返回true；否则，返回false
	 */
	public static boolean pathExists(String _sPathFileName) {
		String sPath = extractFilePath(_sPathFileName);
		return fileExists(sPath);
	}

	public static boolean fileExists(String _sPathFileName) {
		File file = new File(_sPathFileName);
		return file.exists();
	}

	/**
	 * 创建目录
	 *
	 * @param _sDir             目录名称
	 * @param _bCreateParentDir 如果父目录不存在，是否创建父目录
	 * @return
	 */
	public static boolean makeDir(String _sDir, boolean _bCreateParentDir) {
		boolean zResult = false;
		File file = new File(_sDir);
		if (_bCreateParentDir)
			zResult = file.mkdirs(); // 如果父目录不存在，则创建所有必需的父目录
		else
			zResult = file.mkdir(); // 如果父目录不存在，不做处理
		if (!zResult)
			zResult = file.exists();
		return zResult;
	}


	public static void moveRawToDir(Context context, String rawName, String dir) {
		try {
			writeFile(context.getAssets().open(rawName), dir, true);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
	}

	/**
	 * 得到手机的缓存目录
	 *
	 * @param context
	 * @return
	 */
	public static File getCacheDir(Context context) {
		Log.i("getCacheDir", "cache sdcard state: " + Environment.getExternalStorageState());
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File cacheDir = context.getExternalCacheDir();
			if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
				Log.i("getCacheDir", "cache dir: " + cacheDir.getAbsolutePath());
				return cacheDir;
			}
		}

		File cacheDir = context.getCacheDir();
		Log.i("getCacheDir", "cache dir: " + cacheDir.getAbsolutePath());

		return cacheDir;
	}

	/**
	 * 得到皮肤目录
	 *
	 * @param context
	 * @return
	 */
	public static File getSkinDir(Context context) {
		File skinDir = new File(getCacheDir(context), "skin");
		if (skinDir.exists()) {
			skinDir.mkdirs();
		}
		return skinDir;
	}

	public static String getSkinDirPath(Context context) {
		return getSkinDir(context).getAbsolutePath();
	}

	public static String getSaveImagePath(Context context) {
		String path = getCacheDir(context).getAbsolutePath();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
		}

		path = path + File.separator + "Pictures";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	public static String generateFileNameByTime() {
		return System.currentTimeMillis() + "";
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf('/');
		return path.substring(index+1);
	}


}

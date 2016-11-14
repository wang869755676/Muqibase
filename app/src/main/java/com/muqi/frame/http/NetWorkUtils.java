package com.muqi.frame.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 
 * @author John
 *  检查网络环境工具
 */
public class NetWorkUtils {
	private static long lastClickTime;

	/**
	 * 检测网络是否存在
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 检测是否是WiFi
	 */
	public static boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI")) {
			return true;
		}
		return false;
	}

	/**
	 * 防止按钮啥的重复点击 多次 造成多次操作
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		// 2秒内重复点击不作处理
		if (time - lastClickTime < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 获取网络类型名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetworkTypeName(Context context) {
		if (context != null) {
			ConnectivityManager connectMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectMgr != null) {
				NetworkInfo info = connectMgr.getActiveNetworkInfo();
				if (info != null) {
					switch (info.getType()) {
					case ConnectivityManager.TYPE_WIFI:
						return "WIFI";
					case ConnectivityManager.TYPE_MOBILE:
						return getNetworkTypeName(info.getSubtype());
					}
				}
			}
		}
		return getNetworkTypeName(TelephonyManager.NETWORK_TYPE_UNKNOWN);
	}

	public static String getNetworkTypeName(int type) {
		switch (type) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return "GPRS";
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return "EDGE";
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return "UMTS";
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return "HSDPA";
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return "HSUPA";
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return "HSPA";
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return "CDMA";
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return "CDMA - EvDo rev. 0";
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return "CDMA - EvDo rev. A";
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return "CDMA - EvDo rev. B";
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return "CDMA - 1xRTT";
		case TelephonyManager.NETWORK_TYPE_LTE:
			return "LTE";
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return "CDMA - eHRPD";
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return "iDEN";
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return "HSPA+";
		default:
			return "UNKNOWN";
		}
	}
}

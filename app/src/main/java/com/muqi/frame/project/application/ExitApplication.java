package com.muqi.frame.project.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 整个应用信息
 * 
 * @author jwh
 * 
 */
public class ExitApplication extends Application {
	/** app版本号 */
	private String versionCode;
	/** 设备编号 */
	private String deviceNo;
	/** 返回 */
	private static ExitApplication back;
	/** Activity列表 */
	private List<Activity> backlist = new ArrayList<Activity>();

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public void clear() {
		this.versionCode = "";
		this.deviceNo = "";
	}

	/**
	 * 在开发应用时都会和Activity打交道，而Application使用的就相对较少了。
	 * Application是用来管理应用程序的全局状态的，比如载入资源文件。
	 * 在应用程序启动的时候Application会首先创建，然后才会根据情况(Intent)启动相应的Activity或者Service。
	 * 此处将在Application中注册未捕获异常处理器。
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		// 异常处理，不需要处理时注释掉这两句即可！
		FatalExceptionHandler crashHandler = FatalExceptionHandler
				.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
		// Context context = getApplicationContext();
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// // 注册crashHandler
		// crashHandler.init(getApplicationContext());
	}

	public static ExitApplication getInstance() {
		if (null == back) {
			back = new ExitApplication();
		}
		return back;
	}

	/**
	 * 添加activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		backlist.add(activity);
	}
	
	/**
	 * 循环关闭activity,不退出程序
	 * 
	 * @param context
	 */
	public void exitActivity(Context context) {
		for (Activity activity : backlist) {
			activity.finish();
		}
	}

	/**
	 * 循环关闭activity,并退出程序
	 * 
	 * @param context
	 */
	public void exit(Context context) {
		for (Activity activity : backlist) {
			activity.finish();
		}
		System.exit(0);
	}
}

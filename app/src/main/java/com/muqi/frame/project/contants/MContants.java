package com.muqi.frame.project.contants;

import android.os.Environment;

import java.io.File;

public class MContants {
	/** 友盟分享 */
	public static final String UM_SHARE = "com.umeng.share";
	/** 友盟登录 */
	public static final String UM_LOGIN = "com.umeng.login";
	/** 友盟分享 --官网*/
	public static final String SHARE_URL = "http://www.hewolian.com";
	/** QQ的App_id */
	public static final String QQ_App_Id = "1105485073";
	/** QQ的App_key */
	public static final String QQ_App_Key = "Iu9xDK4SPy3gjZra";
	/** 微信分享APPid */
	public final static String WX_APP_ID = "wxa6f2cfd1686465a2";
	public final static String WX_APP_KEY = "5b5714aec457601fa095ba599ccdb146";
	//public final static String WX_APP_SIGNKEY = "2364c21b3cbe12cf9574227f9d0ed6f4";
	public final static String WX_PACKAGE_NAME = "com.tencent.mm";

	/** 图片存放路径 **/
	public static final String TAKE_PICTURE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
											+ File.separator + "qtuktuk" + File.separator;
	/** 剪裁图片存放路径 **/
	public static final String TEMP_FILE_PATH = File.separator+"qtuktuk"+ File.separator+"clip_temp.jpg";
											
	/** 登录状态文件 */
	public final static String UserLogin = "userLogin";
	/** 用户名和密码文件 */
	public final static String LoginPwd = "loginpwd";
	/** app模式存储文件 */
	public final static String APPMODLE = "appmodle";
	
	public final static String OFFLINE_FLAG = "offline_flag";

	//public final static String USERINFO_CACHE_FILE = "UserInfos_cache_file";
	

	
	/** Request Code **/
	public static final int LAUNCH_CAMERA_CODE = 0x102;
	public static final int LAUNCH_DCIM_CODE = 0x103;
	public static final int CROP_RESULT_CODE = 0x104;
	/** Request Code **/
	public static final int SELECT_WECHAT_DCIM_CODE = 0x108;
	/** 滚轮 */
	public static int NumberPickerValue = 0;
	
	public static final String PICTURE_VIEW_BUTTON = "picture_add_button";
	/** broadcast action */
	public static final String APP_MODLE_CHANGED = "app_modle_changed";

	/*YourAllCarsActivity 更新listView*/
	public static final String NOTIFY_LIST_REFERENCE = "NOTIFY_LIST_REFERENCE";
	public static final String NOTIFY_LIST_REFRESH = "NOTIFY_LIST_REFRESH";

}

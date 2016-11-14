/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muqi.frame.im.qmain;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chatuidemo.EMHelper;
import com.umeng.analytics.MobclickAgent;


public class EMApplication extends Application {
	public static Context applicationContext;
	private static EMApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	
	public static EMApplication getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
        applicationContext = this;
        instance = this;
        EMHelper.getInstance().init(applicationContext);
		Fresco.initialize(applicationContext);
        
		// 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
		// 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
		// 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
		// 参数间使用半角“,”分隔。
		// 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

		// 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

//		SpeechUtility.createUtility(EMApplication.this, "appid=57708dec");
//		LeakCanary.install(this);

		//SMSSDK.initSDK(this, "17db278e7e3f0", "d2c72bf240eac33a4b166e04a879ed0a");
		MobclickAgent.setScenarioType(applicationContext, MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM);

	}
	
}

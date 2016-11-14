package com.muqi.frame.im.qmain;//package com.muqi.app.im.qmain;
//
//import com.easemob.util.EasyUtils;
//
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//public class NotificationHandler {
//	public static final int NOTIFY_ID = 0x000;
//	private static NotificationHandler nHandler;
//	private static NotificationManager mNotificationManager;
//
//	private NotificationHandler() {
//
//	}
//
//	/**
//	 * Singleton pattern implementation
//	 *
//	 * @return
//	 */
//	public static NotificationHandler getInstance(Context context) {
//		if (nHandler == null) {
//			nHandler = new NotificationHandler();
//			mNotificationManager = (NotificationManager) context
//					.getApplicationContext().getSystemService(
//							Context.NOTIFICATION_SERVICE);
//		}
//		return nHandler;
//	}
//
//	@SuppressWarnings("deprecation")
//	public void createSimpleMsgNotify(Context context, String title,
//			String content, String type) {
//		CharSequence titleText;
//		if (type.equals("class_job")) {
//			titleText = "有新的作业了:" + title;
//		} else if (type.equals("evaluate")) {
//			titleText = "有老师对你进行了课堂评价:" + title;
//		} else {
//			titleText = "有新的通知了:" + title;
//		}
//		//Log.e("===", titleText+"titleText");
//		CharSequence tickerText = content;
//		Notification notification = new Notification(
//				R.drawable.defalut_avatar_nv, tickerText,
//				System.currentTimeMillis());
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
//		// 设置默认声音
//		notification.defaults |= Notification.DEFAULT_SOUND;
//		// 设定震动(需加VIBRATE权限)
//		notification.defaults |= Notification.DEFAULT_VIBRATE;
//		notification.contentView = null;
//		Intent intent = new Intent();
//		PendingIntent contentIntent =null;
//		// app在前台
//		if (EasyUtils.isAppRunningForeground(context)) {
//			if (type.equals("class_job")) {
//				intent.setClass(context, MyHomeWorkActivity.class);
//			}else if(type.equals("evaluate")){
//				intent.setClass(context, ClassRoomCommentStatisticsActivity.class);
//			} else {
//				intent.setClass(context, SystemNoticeActivity.class);
//			}
//			 contentIntent = PendingIntent.getActivity(context, 0,
//						intent, 0);
//		} else {
//			Class sub=null;
//			if (type.equals("class_job")) {
//				sub= MyHomeWorkActivity.class;
//			}else if(type.equals("evaluate")){
//				sub=ClassRoomCommentStatisticsActivity.class;
//			} else {
//				sub=SystemNoticeActivity.class;
//			}
//
//			contentIntent=PendingIntent.getActivities(context, 0, makeIntentStack(context, sub), 0);
//			// 当点击通知时，我们让原有的Activity销毁，重新实例化一个
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		}
//
//		// 定义下拉通知栏时要展现的内容信息
//		notification.setLatestEventInfo(EMApplication.getInstance(), titleText,
//				tickerText, contentIntent);
//		// 通知一下
//		Log.e("===", "通知");
//		mNotificationManager.notify(NOTIFY_ID, notification);
//	}
//
//	@SuppressWarnings("deprecation")
//	public void createNewMessagesNotify(Context context, String title,
//			String content, String type) {
//		CharSequence titleText = title;
//		CharSequence tickerText = content;
//		NotificationManager manager = (NotificationManager) context
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//		// API level 11
//		Notification.Builder builder = new Notification.Builder(context);// 新建Notification.Builder对象
//		Intent intent = new Intent();
//		PendingIntent contentIntent=null;
//		// app在前台
//		if (EasyUtils.isAppRunningForeground(context)) {
//			intent.setClass(context, SystemNoticeActivity.class);
//			contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
//		} else {
//			// 当点击通知时，我们让原有的Activity销毁，重新实例化一个
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		}
//
//		// PendingIntent点击通知后所跳转的页面
//		builder.setContentTitle(titleText);
//		builder.setContentText(tickerText);
//		builder.setSmallIcon(R.drawable.defalut_avatar_nv);
//		builder.setContentIntent(contentIntent);// 执行intent
//
//		Notification notification = builder.getNotification();// 将builder对象转换为普通的notification
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知后通知消失
//		// 设置默认声音
//		notification.defaults |= Notification.DEFAULT_SOUND;
//		// 设定震动(需加VIBRATE权限)
//		notification.defaults |= Notification.DEFAULT_VIBRATE;
//		notification.contentView = null;
//		manager.notify(1, notification);// 运行notification
//	}
//
//	Intent[] makeIntentStack(Context context, Class sub) {
//		Intent[] intents = new Intent[2];
//		intents[0] = Intent.makeRestartActivityTask(new ComponentName(context,
//				MenuActivity.class));
//		intents[1] = new Intent(context, sub);
//		return intents;
//	}
//}

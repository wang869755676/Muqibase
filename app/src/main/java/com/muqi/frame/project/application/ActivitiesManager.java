package com.muqi.frame.project.application;

import android.app.Activity;

import java.util.Stack;

public class ActivitiesManager {
	private Stack<Activity> mStack;
	private static ActivitiesManager activitiesStack;

	private ActivitiesManager() {
		mStack = new Stack<Activity>();
	}

	public static ActivitiesManager getInstance() {
		if (activitiesStack == null) {
			synchronized (ActivitiesManager.class) {
				if (activitiesStack == null) {
					activitiesStack = new ActivitiesManager();
				}
			}
		}
		return activitiesStack;
	}

	public void push(Activity activity) {
		if (activity == null) {

		}
		mStack.push(activity);
	}

	/**
	 * 所有activity出栈
	 * 
	 * @param isExit
	 *            - 是否是退出操作
	 */
	public void popAll(boolean isExit) {
		while (!mStack.isEmpty()) {
			Activity activity = mStack.pop();
			if (isExit && activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	public void pop(Activity activity) {
		if (activity != null) {
			mStack.remove(activity);
		}
	}

	/**
	 * 获取栈顶（当前）Activity
	 * 
	 * @return
	 */
	public Activity getCurrentActivity() {
		Activity activity = mStack.peek();
		if (activity == null) {
		}
		return activity;
	}
}

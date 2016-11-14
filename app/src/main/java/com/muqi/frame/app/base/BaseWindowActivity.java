package com.muqi.frame.app.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.muqi.frame.project.application.ActivitiesStack;


public class BaseWindowActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 默认为竖屏

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//		    Window window = getWindow();
//		    // Translucent status bar
//		    window.setFlags(
//		        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//		        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		    // Translucent navigation bar
//		    window.setFlags(
//		        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//		        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//			Window window = getWindow();
//			window.setStatusBarColor(getResources().getColor(R.color.title_bg_color));
		}
		ActivitiesStack.getInstance().push(this);
	}
	
	@Override
	protected void onDestroy() {
		ActivitiesStack.getInstance().pop(this);
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}

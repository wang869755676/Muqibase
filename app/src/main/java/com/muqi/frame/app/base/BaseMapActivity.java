package com.muqi.frame.app.base;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;

public abstract class BaseMapActivity extends BaseFragmentActivity {
	public MyLocationListenner myListener = new MyLocationListenner();
	public LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";
	private int span = 1000*60*2;// 定位间隔*秒
	// 当前经纬度
	public double mLantitude;
	public double mLongtitude;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
	}
	
	@Override
	protected void onInit() {
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);
		option.setCoorType(tempcoor);
		option.setScanSpan(span);
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	protected void onData() {
		onInitViews();
	}
	
	/**
	 * 初始化
	 */
	protected abstract void onInitViews();
	
	protected abstract void onLocationValue();
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			mLantitude = location.getLatitude();
			mLongtitude = location.getLongitude();
			onLocationValue();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocationClient.stop();
		super.onDestroy();
	}
}

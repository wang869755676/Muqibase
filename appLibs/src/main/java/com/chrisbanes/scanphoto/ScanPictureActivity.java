package com.chrisbanes.scanphoto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.applibs.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.utils.ShowToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ScanPictureActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	public static final String EXTRA_ORIGIN_IMAGE_URLS = "image_original_urls";
	
	private HackyViewPager mPager;
	private int pagerPosition;
	private TextView indicator;
	private ArrayList<String> urls;
	private ArrayList<String> originalUrls;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_picture_scan);
		mPager = (HackyViewPager) findViewById(R.id.pager);
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
		originalUrls = getIntent().getStringArrayListExtra(EXTRA_ORIGIN_IMAGE_URLS);
		
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls, originalUrls);
		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.indicator);
		
		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}
		});
		
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		mPager.setCurrentItem(pagerPosition);
		
		findViewById(R.id.download_pic_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(originalUrls!=null){
					loadingOriginalPhoto(originalUrls.get(mPager.getCurrentItem()));
				}else{
					loadingOriginalPhoto(urls.get(mPager.getCurrentItem()));
				}
			}
		});
	}
	
	protected void loadingOriginalPhoto(String downloadUrl) {
		ImageView mImageView = new ImageView(this);
		ImageLoader.getInstance().displayImage(downloadUrl, mImageView, null, new SimpleImageLoadingListener() {
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if(loadedImage!=null){
					saveToGallary(loadedImage);
				}else{
					ShowToast.showShort(ScanPictureActivity.this, "下载出错了,请稍后再试");
				} 
			}
		},null);
	}
	
	@SuppressLint("SdCardPath")
	private void saveToGallary(Bitmap originalBitmap) {
		// 首先保存图片
	    File appDir = new File(Environment.getExternalStorageDirectory(), "我行我速");
	    if (!appDir.exists()) {
	        appDir.mkdir();
	    }
	    String fileName = System.currentTimeMillis() + ".jpg";
	    File file = new File(appDir, fileName);
	    try {
	        FileOutputStream fos = new FileOutputStream(file);
	        originalBitmap.compress(CompressFormat.JPEG,100 , fos);
	        fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 其次把文件插入到系统图库
	    try {
	        MediaStore.Images.Media.insertImage(this.getContentResolver(),
	                file.getAbsolutePath(), fileName, null);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    ShowToast.showShort(this,"图片已保存到系统相册:"+fileName);
	    // 最后通知图库更新
	    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("/sdcard/myapps/image.jpg"))));
	}
	
	@Override
	public void finish() {
		super.finish();
		//overridePendingTransition(0, R.anim.activity_zoom_out);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}
	
	private class ImagePagerAdapter extends FragmentStatePagerAdapter {
		public ArrayList<String> fileList;
		public ArrayList<String> originList;

		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
			super(fm);
			this.fileList = fileList;
		}
		
		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList, ArrayList<String> originList) {
			super(fm);
			this.fileList = fileList;
			this.originList = originList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			ImageDetailFragment f;
			if(originList != null){
				String origin = originList.get(position);
				f = ImageDetailFragment.newInstance(url,origin);
			}else{
				f = ImageDetailFragment.newInstance(url);
			}
			return f;
		}
		
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
}

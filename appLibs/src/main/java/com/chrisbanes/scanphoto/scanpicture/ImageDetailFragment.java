package com.chrisbanes.scanphoto.scanpicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applibs.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.easeui.widget.photoview.PhotoViewAttacher;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.widget.RoundProgressBar;

/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
	private String mImageUrl;
	private String originalImageUrl;
	private ImageView mImageView;
	private RoundProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	private TextView checkButton;
	private ImageLoaderCacheUtils cacheUtils;

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();
		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);
		return f; 
	}
	
	public static ImageDetailFragment newInstance(String imageUrl, String originUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();
		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		args.putString("origin", originUrl);
		f.setArguments(args);
		return f;
	}
	
	public void configImageLoader(Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				//.showStubImage(R.drawable.load_err_big_bg)        //设置图片下载期间显示的图片
				//.showImageForEmptyUri(R.drawable.load_err_big_bg) //设置图片Uri为空或是错误的时候显示的图片
				//.showImageOnFail(R.drawable.load_err_big_bg)      //设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)                           //设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)                             //设置下载的图片是否缓存在SD卡中
				.bitmapConfig(Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();                                     //创建配置过得DisplayImageOption对象
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
		.defaultDisplayImageOptions(options)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config);
		
		if(originalImageUrl!=null){
			cacheUtils = new ImageLoaderCacheUtils(context);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url"): null;
		originalImageUrl = getArguments() != null ? getArguments().getString("origin"): null;
		configImageLoader(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);
		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});
		progressBar = (RoundProgressBar) v.findViewById(R.id.loading);
		if(originalImageUrl != null){
			if(!cacheUtils.hasOverCache(originalImageUrl)){
				checkButton = (TextView) v.findViewById(R.id.check_original_button);
				checkButton.setVisibility(View.VISIBLE);
				checkButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						loadOriginalImageView(originalImageUrl);
					}
				});
			}
		}
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(originalImageUrl!=null && cacheUtils.hasOverCache(originalImageUrl)){
			if(originalImageUrl.startsWith("/")){
				Glide.with(getContext()).load(originalImageUrl).into(mImageView);
			}else {
				loadGenalImageView(originalImageUrl);
			}
		}else{
			if(mImageUrl.startsWith("/")){
				Glide.with(getContext()).load(mImageUrl).into(mImageView);
			}else {
				loadGenalImageView(mImageUrl);
			}
		}

	}
	
	private void loadGenalImageView(String imageUrl){

		ImageLoader.getInstance().displayImage(imageUrl, mImageView, null, new SimpleImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(0);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					message = "图片太大无法显示";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
				}
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressBar.setVisibility(View.GONE);
				mAttacher.update();
			}
		},
		new ImageLoadingProgressListener(){

			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				int progress = current*100/total;
				progressBar.setProgress(progress);
			}});
	}
	
	private void loadOriginalImageView(String imageUrl){
		
		ImageLoader.getInstance().loadImage(imageUrl, null, null, new SimpleImageLoadingListener(){
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(0);
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					message = "图片太大无法显示";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
				}
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				mImageView.setImageBitmap(loadedImage);
				mAttacher.update();
				checkButton.setVisibility(View.GONE);
				cacheUtils.saveImageUrlToSharedPreferences(originalImageUrl);
			}
		},new ImageLoadingProgressListener() {
			
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				int progress = current*100/total;
				progressBar.setProgress(progress);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		if(mImageView != null)
		ImageLoader.getInstance().cancelDisplayTask(mImageView);
		super.onDestroy();
	}
	
}

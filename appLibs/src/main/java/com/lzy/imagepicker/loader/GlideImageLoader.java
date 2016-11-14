package com.lzy.imagepicker.loader;

import android.app.Activity;
import android.widget.ImageView;

import com.applibs.R;
import com.bumptech.glide.Glide;


public class GlideImageLoader implements ImageLoader {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)    //配置上下文
                .load("file://" + path)         //设置图片路径
                .error(R.drawable.default_image)  //设置错误图片
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    	
    }
}

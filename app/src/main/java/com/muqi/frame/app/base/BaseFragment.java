package com.muqi.frame.app.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.muqi.frame.project.contants.MContants;
import com.muqi.frame.project.utils.SharePreferenceUtil;


/**
 * 
 */
public abstract class BaseFragment extends Fragment {
    protected BaseFragmentActivity mBaseActivity = null;
    protected Activity mActivity = null;
    public SharePreferenceUtil mSpUtil;
    private ProgressBar progressbar;


    @SuppressWarnings("deprecation")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseFragmentActivity){
            this.mBaseActivity = (BaseFragmentActivity) activity;
        }
        this.mActivity = activity;
        mSpUtil = new SharePreferenceUtil(mActivity, MContants.UserLogin);

    }


    
    /**
	 * 展示progressbar
	 */
	public void showLoading() {
		showLoading(0, 0);
	}

	/**
	 * 展示progressbar
	 * 
	 * @param topSpace
	 *            progressbar与顶部的距离
	 * @param bottomSpace
	 *            progressbar与底部的距离
	 */
	public void showLoading(int topSpace, int bottomSpace) {
		if (progressbar != null) {
			if (!progressbar.isShown()) {
				FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) progressbar
						.getLayoutParams();
				if (layoutParams.topMargin != topSpace
						|| layoutParams.bottomMargin != bottomSpace) {
					layoutParams.topMargin = topSpace;
					layoutParams.bottomMargin = bottomSpace;
					progressbar.setLayoutParams(layoutParams);
				}
				progressbar.setVisibility(View.VISIBLE);
			}
		} else {
			progressbar = new ProgressBar(mActivity);
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(150, 150);
			layoutParams.topMargin = topSpace;
			layoutParams.bottomMargin = bottomSpace;
			layoutParams.gravity = Gravity.CENTER;
			((FrameLayout) mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT))
					.addView(progressbar, layoutParams);
		}
	}

	/**
	 * 隐藏progressbar
	 */
	public void hideLoading() {
		if (progressbar != null) {
			progressbar.setVisibility(View.GONE);
		}
	}
	
	/**
	 * Activity跳转
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls) {
		Intent intent = new Intent(mActivity, cls);
		startActivity(intent);
	}

}

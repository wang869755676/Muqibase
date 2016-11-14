package com.muqi.frame.app.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.hyphenate.chatuidemo.R;
import com.muqi.frame.project.contants.MContants;
import com.muqi.frame.project.utils.SharePreferenceUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.Locale;

import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends BaseWindowActivity {
    protected Context mContext;
    private ProgressBar progressbar;
    /**
     * user登录配置
     */
    public SharePreferenceUtil mSpUtil;

    protected SVProgressHUD mSVProgressHUD;
    private LanguageReceiver languageReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        mSpUtil = new SharePreferenceUtil(mContext, MContants.UserLogin);

        changeAppLanguage();
        languageReceiver = new LanguageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("change_language");
        registerReceiver(languageReceiver, intentFilter);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }

    private void init() {
        onInit();
        onData();
    }

    /**
     * 初始化控件
     */
    protected abstract void onInit();

    /**
     * 初始化数据
     */
    protected abstract void onData();


    /**
     *  简单的显示一个进度条
     */
    public void showLoading(){
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD(this);
        }
        mSVProgressHUD.show();


    }
    /**
     * 进度条+文字提醒
     *
     * @param message
     */
    public void showProgressWithState(String message) {
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD(this);
        }
        mSVProgressHUD.showWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Gradient);
    }

    /**
     * success
     */
    public void showSuccessWithState() {
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD(this);
        }
        mSVProgressHUD.showSuccessWithStatus(getString(R.string.congratulations_submission_success));
    }

    /**
     * 进度条+数字进度
     *
     * @param progress
     */
    public void showSvWithProgress(int progress) {
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD(this);
            mSVProgressHUD.getProgressBar().setProgress(0);
            mSVProgressHUD.showWithProgress(getString(R.string.progress) + 0 + "%", SVProgressHUD.SVProgressHUDMaskType.Gradient);
        }
        if (mSVProgressHUD.getProgressBar().getMax() != mSVProgressHUD.getProgressBar().getProgress()) {
            mSVProgressHUD.getProgressBar().setProgress(progress);
            mSVProgressHUD.setText(getString(R.string.progress) + progress + "%");
        }
    }

    /**
     * err
     */
    public void showErrorWithState() {
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD(this);
        }
        mSVProgressHUD.showErrorWithStatus(getString(R.string.sorry_the_network_exception));
    }

    public void setMaxPicsSvWithProgress(int maxNo, String prgMsg) {
        if (mSVProgressHUD == null) {
            mSVProgressHUD = new SVProgressHUD(this);
            mSVProgressHUD.getProgressBar().setProgress(0);
            mSVProgressHUD.showWithProgress(prgMsg + 0 + "/" + maxNo, SVProgressHUD.SVProgressHUDMaskType.Gradient);
        }
    }

    public void hidingSVloading() {
        if (mSVProgressHUD != null && mSVProgressHUD.isShowing()) {
            mSVProgressHUD.dismissImmediately();
        }
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Activity跳转
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * 关闭系统的软键盘
     */
    public void dismissSoftKeyboard() {
        View view = this.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * view为接受软键盘输入的视图
     *
     * @param view
     */
    public void getSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(languageReceiver);
        super.onDestroy();
    }

    public void changeAppLanguage() {
        Locale myLocale = null;
        switch (mSpUtil.getLanguage()) {
            case 1:
                myLocale = Locale.US;
                break;
            case 2:
                myLocale = Locale.CHINA;
                break;
        }
        // 本地语言设置
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public class LanguageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("change_language")) {
                changeAppLanguage();
                recreate();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }
}


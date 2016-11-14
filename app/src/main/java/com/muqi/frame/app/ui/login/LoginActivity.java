package com.muqi.frame.app.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.EMHelper;
import com.hyphenate.chatuidemo.R;
import com.muqi.frame.app.base.BaseFragmentActivity;
import com.muqi.frame.app.bean.User;
import com.muqi.frame.http.NetWorkUtils;
import com.muqi.frame.im.qmain.EMApplication;
import com.muqi.frame.project.application.ExitApplication;
import com.muqi.frame.project.contants.MContants;
import com.muqi.frame.project.contants.NetWorkApi;
import com.muqi.frame.project.utils.SharePreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import com.utils.ShowToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 这个Activity显示的是登录页面
 */
public class LoginActivity extends BaseFragmentActivity implements OnClickListener {
    /**
     * 用户账号输入框
     */
    private EditText username;
    /**
     * 用户密码输入框
     */
    private EditText password = null;
    /**
     * 用户账号
     */
    private String loginName;
    /**
     * 用户密码
     */
    private String loginPassword;


    private TextView registerTv;//注册账号
    private TextView forgetTv;//忘记密码
    private Button btn_login;
    private SharePreferenceUtil loginSpUtil;
    public static LoginActivity instance;


    private TextView countryCodeTv;
    private String countryCodeStr="86";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
    }

    /**
     * 对布局的控件的初始化以及监听事件的绑定操作
     */
    @Override
    protected void onInit() {
        instance = this;
        username = (EditText) findViewById(R.id.login_phonenumber);
        password = (EditText) findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.login_btn);

        registerTv = (TextView) findViewById(R.id.login_register);
        forgetTv = (TextView) findViewById(R.id.login_forgetpassword);

        countryCodeTv = (TextView) findViewById(R.id.code);


        countryCodeTv.setOnClickListener(this);
        btn_login.setOnClickListener(this);//登录的点击事件
        registerTv.setOnClickListener(this);//注册点击事件
        forgetTv.setOnClickListener(this);//忘记密码点击事件
    }

    @Override
    protected void onData() {
        /** 检查是否保存了密码 ,有则取数据 */
        loginSpUtil = new SharePreferenceUtil(this, MContants.LoginPwd);
        if (!TextUtils.isEmpty(loginSpUtil.getUserName()) && !TextUtils.isEmpty(loginSpUtil.getPassword())) {
            username.setText(loginSpUtil.getUserName());
            username.setSelection(loginSpUtil.getUserName().length());
            password.setText(loginSpUtil.getPassword());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //登陆按钮
                MobclickAgent.onEvent(mContext, "login_login");
                loginName = username.getText().toString().trim();
                username.setSelection(loginName.length());
                loginPassword = password.getText().toString().trim();
                loginSpUtil.setUserName(loginName);
                loginSpUtil.setPassword(loginPassword);
                if (checkInput()) {
                    userLogin(loginName, loginPassword);
                }
                break;
            case R.id.login_register:
                //注册页面  将国家的代号传递过去
                MobclickAgent.onEvent(mContext, "login_register");
                /*Intent registIntent = new Intent(this,RegisterActivity.class);
                registIntent.putExtra("countryCode",countryCodeTv.getText().toString().substring(1));
                startActivity(registIntent);*/
                break;
            case R.id.login_forgetpassword:
               /* MobclickAgent.onEvent(mContext, "login_forget");
                startActivity(ForgetActivity.class);*/
                break;
            case R.id.code:
                //选择国家
                MobclickAgent.onEvent(mContext, "login_chooseArea");
              /*  CountryPage countryPage =new CountryPage();
                FakeActivity fakeActivity = new FakeActivity(){
                    @Override
                    public void onResult(HashMap<String, Object> data) {
                        super.onResult(data);
                        if (data != null) {
                            int page = (Integer) data.get("page");
                            if (page == 1) {
                                // 国家列表返回
                                String currentId = (String) data.get("id");
                                String[] country = SMSSDK.getCountry(currentId);
                                if (country != null) {
                                    countryCodeStr = country[1];
                                    countryCodeTv.setText("+" + countryCodeStr);//国家区号展示的Tv
//                                    tvCountry.setText(country[0]);  国家的名字
                                }
                            }
                        }
                    }
                };
                countryPage.showForResult(this,null,fakeActivity);*/
                break;
            default:
                break;
        }
    }



    /**
     * 调用sdk登陆方法登陆聊天服务器
     */
    private void loginEMChat(final User userInfos) {
        showProgressWithState(getString(R.string.logging_in));
        EMClient.getInstance().login(userInfos.getIm_user_name(), "qtuktuk#!#!2016", new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // 登陆成功，保存用户名
                        EMHelper.getInstance().setCurrentUserName(userInfos.getIm_user_name());
                        // 注册群组和联系人监听
                        EMHelper.getInstance().registerGroupAndContactListener();
                        // 第一次登录或者之前logout后再登录，加载所有本地群和回话
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                        boolean updatenick = EMClient.getInstance().updateCurrentUserNick(EMApplication.currentUserNick.trim());
                        if (!updatenick) {
                            Log.e("LoginActivity", "update current user nick fail");
                        }

                        EMHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                        EMHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userInfos.getAvatar_thumb200());
                        EMHelper.getInstance().getUserProfileManager().setCurrentUserNick(mSpUtil.getTukname());
                        showSuccessWithState();
                      /*  if (MenuActivity.instance == null) {
                            startActivity(MenuActivity.class);
                        } else {
                            Intent intent = new Intent();
                            //intent.setAction(TabMine.BroadAction);
                            sendBroadcast(intent);
                        }*/
                        hidingSVloading();
                        LoginActivity.this.finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        showErrorWithState();
                        ShowToast.showShort(mContext, "EM_Login_Err:" + message);
                    }
                });
            }
        });
    }

    /**
     * 登录接口
     */
    private void userLogin(String mobile, String password) {
        if (NetWorkUtils.isNetworkAvailable(this)) {
            btn_login.setEnabled(false);
          //  final Request request = new Request.Builder().cacheControl(cache).url(requestUrl).build();
            OkHttpUtils.getInstance()
                    .post()
                    .addParams("mobile",countryCodeStr+" "+mobile)
                    .addParams("password",password)
                    .url(NetWorkApi.LOGIN_APP_API)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {

                            Log.e("===",response.body().string());
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(Object response, int id) {

                        }
                    });

        } else {

        }
    }

    /**
     * 登录的方法 登录之前将用户的个人信息都写到本地了的说
     *
     * @param userInfos
     */
    protected void loginHxConfigs(User userInfos) {
        if (userInfos.getIm_user_name() == null || TextUtils.isEmpty(userInfos.getIm_user_name())) {
            //ShowToast.showLong(this, getString(R.string.if_the_account_information_is_abnormal_please_contact_the_administrator));
        } else {
            mSpUtil.setToken(userInfos.getToken());
            mSpUtil.setImUserName(userInfos.getIm_user_name());
           // mSpUtil.setLanguage(userInfos.getLanguage_id());
            //Log.e("===",mSpUtil.getLanguage()+"语言");
            mSpUtil.setUserId(userInfos.getId());
            if (mSpUtil.getLanguage() == 1) {
                mSpUtil.setTukname(userInfos.getLast_name() + userInfos.getFirst_name());
            } else {
                mSpUtil.setTukname(userInfos.getFirst_name() + userInfos.getLast_name());
            }
            //将用户的信息保存到了本地data/data的文件中去 通过工具类可以直接去写
           // CustomerUtil.saveUserInfo(this, userInfos);
            loginEMChat(userInfos);
            loginSpUtil.setUserName(loginName);
            loginSpUtil.setPassword(loginPassword);
        }
    }

    /**
     * 登录失败后的回调函数
     */
    public void notLogin(String msg) {
        ShowToast.showShort(this, msg);
        btn_login.setEnabled(true);
    }

    /**
     * 检测用户名和密码
     *
     * @return
     */
    private boolean checkInput() {
        if (TextUtils.isEmpty(loginName)) {
            username.setError(getString(R.string.login_username_empty));
        } else if (TextUtils.isEmpty(loginPassword)) {
            password.setError(getString(R.string.login_password_empty));
        } else {
            return true;
        }
        return false;
    }


    long mExitTime = 0;

    /**
     * 按两次退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {// 两次点击间隔小于2秒退出
                this.finish();
            } else {
                ExitApplication.getInstance().exit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

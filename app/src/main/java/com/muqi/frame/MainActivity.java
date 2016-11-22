package com.muqi.frame;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.chatuidemo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.view)
    View view;
    private ConvenientBanner banner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // conventeneBanner 广告轮播
        initBanner();
        initGlide();
        initMaterAnimation();


    }

    /**
     * 处理materdesigen 的一些动画
     */
    private void initMaterAnimation() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banner.setcurrentitem(1);
/*
  要求api最低为21 暂时不可用
                Animator animator = ViewAnimationUtils.createCircularReveal(MainActivity.this.view, 0, 0, 0, (float) Math.hypot(MainActivity.this.view.getWidth(),MainActivity.this.view.getHeight()));
                animator.setDuration(1000);
                animator.start();
*/

            }
        });
       // ActivityOptions.

    }

    /**
     * 处理图片的工具
     */
    private void initGlide() {
        // 对得到的图片进行处理  可以结合第三方的库
        // Glide.with(this).load("").transform()

        // 监听glide进行下载监听
        Glide.with(this).load("").into(new Target<GlideDrawable>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public void onLoadStarted(Drawable drawable) {

            }

            @Override
            public void onLoadFailed(Exception e, Drawable drawable) {

            }

            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

            }

            @Override
            public void onLoadCleared(Drawable drawable) {

            }

            @Override
            public void getSize(SizeReadyCallback sizeReadyCallback) {

            }

            @Override
            public void setRequest(Request request) {

            }

            @Override
            public Request getRequest() {
                return null;
            }
        });
    }

    private void initBanner() {
        banner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        loadTestDatas();
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

        //设置翻页的效果，不需要翻页效果可用不设

        //    convenientBanner.setManualPageable(false);//设置不能手动影响

        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + CubeOutTransformer.class.getSimpleName());
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            banner.getViewPager().setPageTransformer(true, transforemer);

           /* //部分3D特效需要调整滑动速度
            if(transforemerName.equals("StackTransformer")){
                convenientBanner.setScrollDuration(1200);
            }*/

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        //banner.setOnPageChangeListener()
        //  banner.getCurrentItem();
        // banner.setcurrentitem();


    }

    /*
   加入测试Views
   * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.mipmap.class));


    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // banner.startTurning(5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // banner.stopTurning();
    }
}

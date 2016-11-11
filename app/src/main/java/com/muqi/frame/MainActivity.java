package com.muqi.frame;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hyphenate.chatuidemo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.applibs.R.id.convenientBanner;


public class MainActivity extends AppCompatActivity {


    private ConvenientBanner banner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // conventeneBanner 广告轮播
        initBanner();

    }

    private void initBanner() {
        banner= (ConvenientBanner) findViewById(R.id.convenientBanner);
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
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + AccordionTransformer.class.getSimpleName());
            ABaseTransformer transforemer= (ABaseTransformer)cls.newInstance();
            banner.getViewPager().setPageTransformer(true,transforemer);

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
        banner.startTurning(5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopTurning();
    }
}

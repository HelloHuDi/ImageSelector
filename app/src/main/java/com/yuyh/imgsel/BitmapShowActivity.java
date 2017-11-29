package com.yuyh.imgsel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;

/**
 * 满足UI全屏查看
 */
public class BitmapShowActivity extends AppCompatActivity {

    GalleryViewPager mViewPager;
    private static final int REQUEST_LIST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bitmap_show);
        mViewPager = (GalleryViewPager) findViewById(R.id.view_pager);
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        ISListConfig config = new ISListConfig.Builder()
                .multiSelect(true)
                // 是否记住上次选中记录
                .rememberSelected(false)
                .maxNum(50)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5")).build();

        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            showImage(data.getStringArrayListExtra("result"));
        }
    }

    private void showImage(ArrayList<String> stringArrayListExtra) {
        mViewPager.setAdapter(new GalleryAdapter(getSupportFragmentManager(),stringArrayListExtra));
        mViewPager.setPageMargin(30);// 设置页面间距
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);// 设置起始位置
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    class GalleryAdapter extends FragmentPagerAdapter {

        private ArrayList<String>urlList;

        GalleryAdapter(FragmentManager fm, ArrayList<String> urlList) {
            super(fm);
            this.urlList=urlList;
        }

        @Override
        public Fragment getItem(int position) {
            return ItemFragment.create(urlList.get(position));
        }

        @Override
        public int getCount() {
            return urlList.size();
        }
    }

}

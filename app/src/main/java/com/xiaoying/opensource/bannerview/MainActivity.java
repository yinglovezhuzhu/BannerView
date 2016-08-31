package com.xiaoying.opensource.bannerview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xiaoying.opensource.bannerview.bean.Banner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private BannerView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        mBannerView = (BannerView) findViewById(R.id.bv_banner);

        final List<Banner> banners = new ArrayList<Banner>();
        banners.add(new Banner(R.mipmap.img_home_baner1));
        banners.add(new Banner(R.mipmap.img_home_baner2));
        banners.add(new Banner(R.mipmap.img_home_baner3));
        banners.add(new Banner(R.mipmap.img_home_baner4));
        banners.add(new Banner(R.mipmap.img_home_baner5));
        mBannerView.addData(getSupportFragmentManager(), banners, true);
    }
}

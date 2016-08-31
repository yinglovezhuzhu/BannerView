package com.xiaoying.opensource.bannerview;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xiaoying.opensource.bannerview.adapter.BannerAdapter;
import com.xiaoying.opensource.bannerview.bean.Banner;

import java.util.List;


/**
 * Banner view
 * Created by yinglovezhuzhu@gmail.com on 2015/12/1.
 */
public class BannerView extends LinearLayout implements ViewPager.OnPageChangeListener {

    public static final float BANNER_VERTICAL_RATE = (float) 1 / 2;
    public static final float BANNER_HORIZONTAL_RATE = (float) 1 / 3;
    public static final long BANNER_AUTO_SCROLL_DELAY = 1000 * 6;

    private final BannerHandler mHandler = new BannerHandler(Looper.getMainLooper());

    private ViewPager mViewPager;
    private PageControlBar mPagerControlBar;
    private BannerAdapter mBannerAdapter;

    public BannerView(Context context) {
        super(context);
        initView(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateViewByConfiguration(newConfig);
    }

    //--------------- OnPageChangeListener START -------------------------------
    private int mCurrentPosition = -1;
    private boolean mPageChanged = false;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if(positionOffsetPixels != 0) {
//            stopAutoScroll();
//        }
    }

    @Override
    public void onPageSelected(int position) {
        mPageChanged = mCurrentPosition != position;
        mCurrentPosition = position;
        if(mBannerAdapter.getRealDataCount() < mBannerAdapter.getCount()) {
            if(0 == position) {
                mPagerControlBar.setLastPage();
            } else if(mBannerAdapter.getCount() - 1 == position) {
                mPagerControlBar.setFirstPage();
            } else {
                mPagerControlBar.setCurrentPage(mViewPager.getCurrentItem() - 1);
            }
        } else {
            mPagerControlBar.setCurrentPage(mViewPager.getCurrentItem());
        }
        startAutoScroll();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(ViewPager.SCROLL_STATE_IDLE == state) {
            if(mPageChanged && mBannerAdapter.getRealDataCount() < mBannerAdapter.getCount()) {
                if(0 == mCurrentPosition) {
                    mViewPager.setCurrentItem(mBannerAdapter.getCount() - 2, false);
                } else if(mBannerAdapter.getCount() - 1 == mCurrentPosition) {
                    mViewPager.setCurrentItem(1, false);
                }
            }
            mPageChanged = false;
            startAutoScroll();
        } else {
            stopAutoScroll();
        }
    }

    //--------------- OnPageChangeListener END -------------------------------

    public void setFragmentManager(FragmentManager fm) {
        if(null == mBannerAdapter && null != fm) {
            mBannerAdapter = new BannerAdapter(fm);
            mViewPager.setAdapter(mBannerAdapter);
        }
    }

    /**
     * 添加Banner数据
     * @param fm
     * @param data
     * @param notifyDataSetChanged
     */
    public void addData(FragmentManager fm, List<Banner> data, boolean notifyDataSetChanged) {
        if(null == data || data.isEmpty()) {
            return;
        }
        if(null == mBannerAdapter) {
            mBannerAdapter = new BannerAdapter(fm);
            mViewPager.setAdapter(mBannerAdapter);
        }
        stopAutoScroll();
        mBannerAdapter.addAll(data, notifyDataSetChanged);
        mPagerControlBar.setPageCount(mBannerAdapter.getRealDataCount());
        if(mBannerAdapter.getRealDataCount() > 0) {
            mViewPager.setCurrentItem(1);
        }
        startAutoScroll();
    }

    /**
     * 清除Banner
     * @param notifyDataSetChanged
     */
    public void clear(boolean notifyDataSetChanged) {
        if(null == mBannerAdapter) {
            return;
        }
        mBannerAdapter.clear(notifyDataSetChanged);
    }

    /**
     * 设置当前页<br/><br/>
     * 为了实现循环滑动，在数据的最前端和最后端分别添加了最后一个数据和第一个数据，用这个方法设置当前显示，
     * 会自动转换成原来数据的下标。
     * @param item
     * @param smoothScroll
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        if(mBannerAdapter.getRealDataCount() < 1) {
            return;
        }
        try {
            if(mBannerAdapter.getRealDataCount() < mBannerAdapter.getCount()) {
                if(item < 1) {
                    mViewPager.setCurrentItem(1, smoothScroll);
                } else if(item > mBannerAdapter.getCount() - 2) {
                    mViewPager.setCurrentItem(mBannerAdapter.getCount() - 2, smoothScroll);
                } else {
                    mViewPager.setCurrentItem(item + 1, smoothScroll);
                }
            } else {
                mViewPager.setCurrentItem(item, smoothScroll);
            }
        } catch (Exception e) {
            Log.e("BannerView", "setCurrentItem error", e);
        }
    }

    /**
     * 显示/隐藏Banner
     * @param visibility
     */
    public void setVisibility(int visibility) {
        mViewPager.setVisibility(visibility);
        mPagerControlBar.setVisibility(visibility);
    }

    /**
     * 开始自动切换
     */
    private void startAutoScroll() {
        if(!mHandler.hasMessages(BannerHandler.MSG_NEXT_PAGE)) {
            mHandler.sendEmptyMessageDelayed(BannerHandler.MSG_NEXT_PAGE, BANNER_AUTO_SCROLL_DELAY);
        }
    }

    /**
     * 停止自动切换
     */
    private void stopAutoScroll() {
        if(mHandler.hasMessages(BannerHandler.MSG_NEXT_PAGE)) {
            mHandler.removeMessages(BannerHandler.MSG_NEXT_PAGE);
        }
    }

    private void initView(Context context, FragmentManager fm) {
        inflate(context, R.layout.layout_banner, this);

        mViewPager = (ViewPager) findViewById(R.id.vp_banner);
        mPagerControlBar = (PageControlBar) findViewById(R.id.pcb_banner_indicator);

        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.banner_page_margin));
        updateViewByConfiguration(getResources().getConfiguration());
        if(null != fm) {
            mBannerAdapter = new BannerAdapter(fm);
            mViewPager.setAdapter(mBannerAdapter);
        }
    }

    /**
     * 根据配置改变视图
     * @param newConfig
     */
    private void updateViewByConfiguration(Configuration newConfig) {
        int dmWidth = getResources().getDisplayMetrics().widthPixels;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mViewPager.getLayoutParams();
        final int width = dmWidth - 4 * mViewPager.getPageMargin() - lp.leftMargin - lp.rightMargin;
        int height = (int) (width * BANNER_VERTICAL_RATE);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                height = (int) (width * BANNER_HORIZONTAL_RATE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                height = (int) (width * BANNER_VERTICAL_RATE);
                break;
            default:
                break;
        }
        if(null == lp) {
            lp = new FrameLayout.LayoutParams(width, height);
        } else {
            lp.width = width;
            lp.height = height;
        }
        mViewPager.setLayoutParams(lp);
    }

    private class BannerHandler extends Handler {

        public static final int MSG_NEXT_PAGE = 0x01;

        public BannerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_NEXT_PAGE:
                    setCurrentItem(mPagerControlBar.getCurrentPage() + 1, true);
//                    sendEmptyMessageDelayed(BannerHandler.MSG_NEXT_PAGE, BANNER_AUTO_SCROLL_DELAY);
                    break;
                default:
                    break;
            }
        }
    }
}

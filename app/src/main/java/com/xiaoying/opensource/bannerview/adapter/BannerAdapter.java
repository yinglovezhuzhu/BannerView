package com.xiaoying.opensource.bannerview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xiaoying.opensource.bannerview.BannerFragment;
import com.xiaoying.opensource.bannerview.bean.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Banner page adapter
 * Created by yinglovezhuzhu@gmail.com on 2015/11/9.
 */
public class BannerAdapter extends FragmentStatePagerAdapter {

    private List<Banner> mData = new ArrayList<Banner>();

    public BannerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<Banner> banners, boolean notifyDataSetChanged) {
        if(null == banners || banners.isEmpty()) {
            return;
        }
        if(mData.size() > 3) {
            mData.remove(0);
            mData.remove(mData.size() - 1);
        }
        mData.addAll(banners);
        if(banners.size() > 1) {
            mData.add(0, mData.get(banners.size() -1).clone());
            mData.add(mData.get(1).clone());
        }
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void clear(boolean notifyDataSetChanged) {
        mData.clear();
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 获取真实的position
     * @return
     */
    public int getRealDataCount() {
        if(mData.size() < 2) {
            return mData.size();
        }
        return mData.size() - 2;
    }

    public Banner getItemData(int position) {
        return mData.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return BannerFragment.newInstance(getItemData(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }
}

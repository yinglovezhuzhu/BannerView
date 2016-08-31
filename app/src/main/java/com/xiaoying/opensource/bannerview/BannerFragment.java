package com.xiaoying.opensource.bannerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xiaoying.opensource.bannerview.bean.Banner;

/**
 * Banner fragment
 * Created by yinglovezhuzhu@gmail.com on 2015/11/9.
 */
public class BannerFragment extends Fragment {

    private ImageView mIvBanner;
    private ProgressBar mPbProgress;

    private Banner mBanner;

    public static BannerFragment newInstance(Banner banner) {
        BannerFragment fragment = new BannerFragment();
        if(null != banner) {
            Bundle args = new Bundle();
            args.putParcelable("data", banner);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(null != args && args.containsKey("data")) {
            mBanner = args.getParcelable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_banner_pager, container, false);
        mIvBanner = (ImageView) contentView.findViewById(R.id.iv_home_banner_img);
//        mPbProgress = (ProgressBar) contentView.findViewById(progressId);
        if(null != mBanner) {
            mIvBanner.setImageResource(mBanner.getResId());
        }
        return contentView;
    }
}

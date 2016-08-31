package com.xiaoying.opensource.bannerview.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页Banner数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/8/30.
 */
public class Banner implements Parcelable {

    private String url; // banner图片url
    private int resId;

    public Banner() {
    }

    public Banner(String url) {
        this.url = url;
    }

    public Banner(int resId) {
        this.resId = resId;
    }

    public Banner(Parcel source) {
        this.url = source.readString();
        this.resId = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(resId);
    }

    public static Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Banner clone() {
        Banner banner = new Banner();
        banner.setUrl(getUrl());
        banner.setResId(getResId());
        return banner;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "url='" + url + '\'' +
                ", resId=" + resId +
                '}';
    }
}

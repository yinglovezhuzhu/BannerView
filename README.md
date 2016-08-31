# BannerView
广告Banner例子，可以循环滑动，两边能同时显示临近的两个页面的边边

#实现原理
需要实现ViewPager两边能够显示一部分临近的页面的内容，那么首先ViewPager的大小必须必父容器的宽度要小（计算时记得还要加上页面边距）；其次是
ViewPager和父容器都需要添加android:clipChildren="false"属性，值为false表示不被裁剪掉，这样就可以铺满父容器显示，并且大于ViewPager的部分
也会显示出来，不会被裁剪掉。<br>
&lt;FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"<br>
    &nbsp;&nbsp;&nbsp;&nbsp;android:orientation="vertical"<br>
    &nbsp;&nbsp;&nbsp;&nbsp;android:layout_width="match_parent"<br>
    &nbsp;&nbsp;&nbsp;&nbsp;android:layout_height="match_parent"<br>
    &nbsp;&nbsp;&nbsp;&nbsp;android:clipChildren="false"&gt;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&lt;android.support.v4.view.ViewPager<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:id="@+id/vp_banner"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_width="match_parent"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_height="wrap_content"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_gravity="top|center_horizontal"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_marginLeft="@dimen/banner_margin"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_marginRight="@dimen/banner_margin"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_marginBottom="@dimen/banner_margin"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:clipChildren="false"/&gt;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&lt;com.xiaoying.opensource.bannerview.PageControlBar<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:id="@+id/pcb_banner_indicator"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_width="wrap_content"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_height="wrap_content"<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android:layout_gravity="bottom|center_horizontal"/&gt;<br>
&lt;/FrameLayout&gt;<br>

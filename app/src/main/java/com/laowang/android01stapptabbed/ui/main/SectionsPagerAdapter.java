package com.laowang.android01stapptabbed.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.laowang.android01stapptabbed.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    /**
     * 注解表示字符串类的资源，用代码替代了xml配置
     */
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    /**
     * 上下文，一般代表子类【Activity】的对象
     */
    private final Context mContext;

    /**
     * @param context 一般用Activity页面对象作为传参
     * @param fm 片段管理器
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    /**
     * 获取tab的子页面
     * @param position 根据tabLayout页面场景，指的是tab索引
     * @return PlaceholderFragment对象
     */
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // 在此处创建并返回PlaceholderFragment对象，【啥时候 执行它的onCreate()方法呢？？】
        // 【问题又来了】【getItem()在哪里被调用的？？】
        return PlaceholderFragment.newInstance(position + 1);
    }
    /**
     * 获取页面标题
     * @param position 根据tabLayout页面场景，指的是tab索引
     * @return 字符队列
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    /**
     * @return 页签页面个数
     */
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
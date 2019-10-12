package com.prs.coppertino.adapters;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.prs.coppertino.fragments.AlbumFragment;
import com.prs.coppertino.fragments.ArtistsFragment;
import com.prs.coppertino.fragments.SongsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    //private static int NUM_ITEMS = 3;
    private String[] tabTitles = new String[]{"Albums", "Songs","Artists"};

    private Fragment[] fragments;

    FragmentManager manager;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        manager = fm;

        fragments = new Fragment[]{
                AlbumFragment.newInstance(0, "Albums"),
                SongsFragment.newInstance(1, "Songs"),
                ArtistsFragment.newInstance(2,"Artists")
        };
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    // Returns the fragment to display for that page
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    public Fragment getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  manager.findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

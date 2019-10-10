package com.prs.coppertino.adapters;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prs.coppertino.fragments.AlbumFragment;
import com.prs.coppertino.fragments.ArtistsFragment;
import com.prs.coppertino.fragments.SongsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
    private String[] tabTitles = new String[]{"Albums", "Songs","Artists"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return AlbumFragment.newInstance(0, "Albums");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SongsFragment.newInstance(1, "Songs");
            case 2:
                return ArtistsFragment.newInstance(2,"Artists");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        /*SpannableString string = new SpannableString(tabTitles[position]);
        string.setSpan(new RelativeSizeSpan(1.5f),0,tabTitles[position].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        return tabTitles[position];
    }
}

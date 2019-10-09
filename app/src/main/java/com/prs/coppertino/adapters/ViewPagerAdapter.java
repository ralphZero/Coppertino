package com.prs.coppertino.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prs.coppertino.fragments.AlbumFragment;
import com.prs.coppertino.fragments.SongsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

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
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}

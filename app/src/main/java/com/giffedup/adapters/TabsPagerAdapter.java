package com.giffedup.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.giffedup.ui.GifGridFragment;
import com.giffedup.ui.StickersFragment;

import java.util.List;

/**
 * Created by vinay-r on 8/10/15.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    public static final int FRAGMENTS_COUNT = 2;
    private List<String> mTitles;
    public TabsPagerAdapter(FragmentManager fm,List<String> list) {
        super(fm);
        mTitles = list;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return GifGridFragment.newInstance();

            case 1:
                return StickersFragment.newInstance();

            default:
                return GifGridFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}

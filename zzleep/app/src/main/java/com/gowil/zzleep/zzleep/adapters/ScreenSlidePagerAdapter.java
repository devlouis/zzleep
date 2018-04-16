package com.gowil.zzleep.zzleep.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import com.gowil.zzleep.zzleep.AdsPageFragment;

/**
 * Created by chuba on 20/10/2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    List<Integer> items;
    public ScreenSlidePagerAdapter(FragmentManager fm, List<Integer> items) {
        super(fm);
        this.items=items;
    }

    @Override
    public Fragment getItem(int position) {
        return AdsPageFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
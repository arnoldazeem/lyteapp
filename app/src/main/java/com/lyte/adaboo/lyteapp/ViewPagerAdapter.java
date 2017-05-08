package com.lyte.adaboo.lyteapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;


/**
 * Created by nirmal on 12/08/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;
    public static int totalPage = 4;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch (position) {
            case 0:
                f = new ImageOneFragment();
                break;
            case 1:
                f = new ImageTwoFragment();
                break;
            case 2:
                f = new ImageThreeFragment();
                break;
            case 3:
                f = new ImageFourFragment();
                break;
        }        return f;
    }
    @Override
    public int getCount() {
        return totalPage;
    }
}
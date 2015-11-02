package com.thousandsunny.kemis;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hallidafykzir on 10/24/2015.
 */
public class KeluargaPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when MainPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the MainPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public KeluargaPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            TabDataUmum tabDataUmum = new TabDataUmum();
            return tabDataUmum;
        }
        else if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabKategori1 tabKategori1 = new TabKategori1();
            return tabKategori1;
        }
        else {
            TabKategori2 tabKategori2 = new TabKategori2();
            return tabKategori2;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}

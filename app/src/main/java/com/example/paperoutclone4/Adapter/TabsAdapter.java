package com.example.paperoutclone4.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.paperoutclone4.Fragments.EbooksFragment;
import com.example.paperoutclone4.Fragments.EmocksFragment;
import com.example.paperoutclone4.Fragments.MyPDFFragment;


public class TabsAdapter extends FragmentPagerAdapter {

    private final int numOfTabs;

    public TabsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 :
                return new EbooksFragment();
            case 1 :
                return new EmocksFragment();
            case 2 :
                return new MyPDFFragment();
            default:
                return new EbooksFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "E-Books";
            case 1:
                return "E-Mocks";
            case 2:
                return "My PDF";
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

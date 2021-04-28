package com.example.instagramclone.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.instagramclone.Fragments.EmailFragment;
import com.example.instagramclone.Fragments.PhoneFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    int tabs;

    public TabAdapter(Context context, FragmentManager manager, int totalTabs) {
        super(manager);
        this.tabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PhoneFragment();
            case 1:
                return new EmailFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}

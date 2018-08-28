package com.example.anti2110.wantedjob.notice.finance;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FinanceViewPageAdapter extends FragmentPagerAdapter {

    private String[] title = {"전체 공고", "은행 채용", "증권사 채용", "보험사 채용"};

    public FinanceViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FinanceNoticeListFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}

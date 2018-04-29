package com.star_zero.eternalviewpager.sample.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.star_zero.eternalviewpager.EternalPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends EternalPagerAdapter<Integer> {

    private static final int START = 0;
    private static final int END = 10;

    public SimpleAdapter(FragmentManager fragmentManager, Integer initialKey) {
        super(fragmentManager, initialKey);
    }

    @NonNull
    @Override
    public Fragment getItem(Integer key) {
        return SimpleFragment.createInstance(key);
    }

    @Nullable
    @Override
    protected Integer getNextKey(@NonNull Integer last) {
        if (last == END) {
            return null;
        }
        return last + 1;
    }

    @Nullable
    @Override
    protected Integer getPrevKey(@NonNull Integer first) {
        if (first == START) {
            return null;
        }
        return first - 1;
    }

    @Nullable
    @Override
    protected Bundle saveKeysState(@NonNull ArrayList<Integer> keys) {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("keys", keys);
        return bundle;
    }

    @Nullable
    @Override
    protected List<Integer> restoreKeysState(@NonNull Bundle bundle) {
        return bundle.getIntegerArrayList("keys");
    }

}

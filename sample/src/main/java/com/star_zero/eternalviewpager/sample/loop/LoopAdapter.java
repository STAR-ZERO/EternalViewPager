package com.star_zero.eternalviewpager.sample.loop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.star_zero.eternalviewpager.EternalPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoopAdapter extends EternalPagerAdapter<Integer> {

    private static final int START = 0;
    private static final int END = 5;


    public LoopAdapter(FragmentManager fragmentManager, Integer initialKey) {
        super(fragmentManager, initialKey);
    }

    @NonNull
    @Override
    public Fragment getItem(Integer key) {
        return LoopFragment.createInstance(key);
    }

    @Nullable
    @Override
    protected Integer getNextKey(@NonNull Integer last) {
        if (last == END) {
            return START;
        }
        return last + 1;
    }

    @Nullable
    @Override
    protected Integer getPrevKey(@NonNull Integer first) {
        if (first == START) {
            return END;
        }
        return first - 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(Integer key) {
        return String.valueOf(key);
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

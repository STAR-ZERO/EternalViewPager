package com.star_zero.eternalviewpager.sample.date;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.star_zero.eternalviewpager.EternalPagerAdapter;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class DatePagerAdapter extends EternalPagerAdapter<LocalDate> {

    public DatePagerAdapter(FragmentManager fragmentManager, LocalDate initialKey) {
        super(fragmentManager, initialKey);
    }

    @NonNull
    @Override
    public Fragment getItem(LocalDate key) {
        return DatePageFragment.createInstance(key);
    }

    @Nullable
    @Override
    protected LocalDate getNextKey(@NonNull LocalDate last) {
        return last.plusDays(1);
    }

    @Nullable
    @Override
    protected LocalDate getPrevKey(@NonNull LocalDate first) {
        return first.minusDays(1);
    }

    @Nullable
    @Override
    protected Bundle saveKeysState(@NonNull ArrayList<LocalDate> keys) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("keys", keys);
        return bundle;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    protected List<LocalDate> restoreKeysState(@NonNull Bundle bundle) {
        return (ArrayList<LocalDate>) bundle.getSerializable("keys");
    }
}

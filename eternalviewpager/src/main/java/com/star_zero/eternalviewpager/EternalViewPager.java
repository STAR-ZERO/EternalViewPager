package com.star_zero.eternalviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class EternalViewPager extends ViewPager {

    private EternalPagerAdapter adapter;

    public EternalViewPager(@NonNull Context context) {
        this(context, null);
    }

    public EternalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    onPageScrollIdle();
                }
            }
        });
    }

    public void setAdapter(@Nullable EternalPagerAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
        if (adapter != null) {
            createInitialKeys();
        }
    }

    @SuppressWarnings("unchecked")
    public void setSelectedKey(@NonNull Object key) {
        if (adapter != null) {
            adapter.initialize(key);
            createInitialKeys();
        }
    }

    public void moveNext() {
        setCurrentItem(getCurrentItem() + 1);
    }

    public void movePrev() {
        setCurrentItem(getCurrentItem() - 1);
    }

    @Nullable
    public EternalPagerAdapter getAdapter() {
        return adapter;
    }

    @SuppressWarnings("unchecked")
    private void createInitialKeys() {
        int initialPosition = 0;
        int maxPage = (getOffscreenPageLimit() + 1) * 2 + 1;

        while (adapter.getCount() < maxPage) {
            Object perv = adapter.getPrevKey();
            if (perv != null) {
                adapter.addKey(0, perv);
                initialPosition++;
            }
            Object next = adapter.getNextKey();
            if (next != null) {
                adapter.addKey(next);
            }
        }

        adapter.notifyDataSetChanged();
        setCurrentItem(initialPosition);
    }

    private void onPageScrollIdle() {
        if (getCurrentItem() >= adapter.getCount() - (getOffscreenPageLimit() + 1)) {
            updateNextList();
        }
        if (getCurrentItem() <= getOffscreenPageLimit()) {
            updatePrevList();
        }
    }

    @SuppressWarnings("unchecked")
    private void updateNextList() {
        boolean changed = false;
        int start = adapter.getCount() - 1;
        int end = getCurrentItem() + (getOffscreenPageLimit() + 1);
        for (int i = start; i < end; i++) {
            Object obj = adapter.getNextKey();
            if (obj != null) {
                adapter.addKey(obj);
                adapter.removeKey(0);
                changed = true;
            }
        }
        if (changed) {
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unchecked")
    private void updatePrevList() {
        boolean changed = false;
        int start = getCurrentItem();
        int end = getOffscreenPageLimit() + 1;
        for (int i = start; i < end; i++) {
            Object obj = adapter.getPrevKey();
            if (obj != null) {
                adapter.addKey(0, obj);
                adapter.removeKey(adapter.getCount() - 1);
                changed = true;
            }
        }
        if (changed) {
            adapter.notifyDataSetChanged();
        }
    }
}

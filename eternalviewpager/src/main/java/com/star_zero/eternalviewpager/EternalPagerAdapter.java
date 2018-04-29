package com.star_zero.eternalviewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class EternalPagerAdapter<T> extends PagerAdapter {

    private static final String TAG = EternalPagerAdapter.class.getSimpleName();

    private FragmentManager fragmentManager;

    private FragmentTransaction currentTransaction = null;

    private Fragment currentPrimaryItem = null;

    private List<WrappedFragment<T>> wrappedFragments = new ArrayList<>();

    private List<Fragment.SavedState> savedStates = new ArrayList<>();

    private List<WrappedKey<T>> wrappedKeys = new ArrayList<>();

    public EternalPagerAdapter(FragmentManager fragmentManager, T initialKey) {
        super();
        this.fragmentManager = fragmentManager;
        initialize(initialKey);
    }

    void initialize(T key) {
        wrappedKeys.clear();
        wrappedKeys.add(new WrappedKey<>(key));
    }

    void addKey(@NonNull T key) {
        wrappedKeys.add(new WrappedKey<>(key));
    }

    void addKey(int position, @NonNull T key) {
        wrappedKeys.add(position, new WrappedKey<>(key));
    }

    void removeKey(int position) {
        wrappedKeys.remove(position);
    }

    @Nullable
    T getNextKey() {
        T lastKey = Objects.requireNonNull(getKey(getCount() - 1));
        return getNextKey(lastKey);
    }

    @Nullable
    T getPrevKey() {
        T firstKey = Objects.requireNonNull(getKey(0));
        return getPrevKey(firstKey);
    }

    @Nullable
    protected T getKey(int position) {
        if (wrappedKeys.size() > position) {
            return wrappedKeys.get(position).key;
        } else {
            return null;
        }
    }

    @NonNull
    public abstract Fragment getItem(T key);

    @Override
    public int getCount() {
        return wrappedKeys.size();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @SuppressLint("CommitTransaction")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (wrappedFragments.size() > position) {
            WrappedFragment<T> wrappedFragment = wrappedFragments.get(position);
            if (wrappedFragment != null) {
                return wrappedFragment;
            }
        }

        if (currentTransaction == null) {
            currentTransaction = fragmentManager.beginTransaction();
        }

        Fragment fragment = getItem(wrappedKeys.get(position).key);
        if (savedStates.size() > position) {
            Fragment.SavedState savedState = savedStates.get(position);
            if (savedState != null) {
                fragment.setInitialSavedState(savedState);
            }
        }
        while (wrappedFragments.size() <= position) {
            wrappedFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        WrappedFragment<T> wrappedFragment = new WrappedFragment<>(wrappedKeys.get(position), fragment);
        wrappedFragments.set(position, wrappedFragment);

        currentTransaction.add(container.getId(), fragment);

        return wrappedFragment;
    }

    @SuppressLint("CommitTransaction")
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = ((WrappedFragment) object).fragment;

        if (currentTransaction == null) {
            currentTransaction = fragmentManager.beginTransaction();
        }
        while (savedStates.size() <= position) {
            savedStates.add(null);
        }
        savedStates.set(position, fragment.isAdded() ? fragmentManager.saveFragmentInstanceState(fragment) : null);
        wrappedFragments.set(position, null);

        currentTransaction.remove(fragment);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = ((WrappedFragment) object).fragment;
        if (fragment != currentPrimaryItem) {
            if (currentPrimaryItem != null) {
                currentPrimaryItem.setMenuVisibility(false);
                currentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            currentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (currentTransaction != null) {
            currentTransaction.commitNowAllowingStateLoss();
            currentTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((WrappedFragment) object).fragment.getView() == view;
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (savedStates.size() > 0) {
            state = new Bundle();

            ArrayList<T> keys = new ArrayList<>();
            for (WrappedKey<T> wrappedKey : wrappedKeys) {
                keys.add(wrappedKey.key);
            }
            Parcelable keysState = saveKeysState(keys);
            if (keysState == null) {
                return null;
            }
            state.putParcelable("keys", keysState);

            Fragment.SavedState[] fss = new Fragment.SavedState[savedStates.size()];
            savedStates.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < wrappedFragments.size(); i++) {
            WrappedFragment wrappedFragment = wrappedFragments.get(i);
            if (wrappedFragment == null) {
                continue;
            }
            Fragment f = wrappedFragment.fragment;
            if (f != null && f.isAdded()) {
                if (state == null) {
                    state = new Bundle();
                }
                String key = "f" + i;
                fragmentManager.putFragment(state, key, f);
            }
        }
        return state;
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            savedStates.clear();
            wrappedFragments.clear();
            wrappedKeys.clear();

            Parcelable keysBundle = bundle.getParcelable("keys");
            if (keysBundle == null) {
                return;
            }
            List<T> keys = restoreKeysState((Bundle) keysBundle);
            if (keys == null) {
                return;
            }
            for (T key : keys) {
                wrappedKeys.add(new WrappedKey<>(key));
            }

            if (fss != null) {
                for (Parcelable fs : fss) {
                    savedStates.add((Fragment.SavedState) fs);
                }
            }

            Iterable<String> bundleKeys = bundle.keySet();
            for (String bundleKey : bundleKeys) {
                if (bundleKey.startsWith("f")) {
                    int index = Integer.parseInt(bundleKey.substring(1));
                    Fragment f = fragmentManager.getFragment(bundle, bundleKey);
                    if (f != null) {
                        while (wrappedFragments.size() <= index) {
                            wrappedFragments.add(null);
                        }
                        f.setMenuVisibility(false);
                        WrappedKey<T> key = wrappedKeys.get(index);
                        if (key != null) {
                            wrappedFragments.set(index, new WrappedFragment<>(key, f));
                        }
                    } else {
                        Log.w(TAG, "Bad fragment at key " + bundleKey);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        List<WrappedFragment<T>> copyWrappedFragments = new ArrayList<>(wrappedFragments);
        List<Fragment.SavedState> copySavedStates = new ArrayList<>(savedStates);

        wrappedFragments.clear();
        savedStates.clear();

        while (wrappedFragments.size() < wrappedKeys.size()) {
            wrappedFragments.add(null);
            savedStates.add(null);
        }

        for (int i = 0; i < copyWrappedFragments.size(); i++) {
            WrappedFragment<T> wrappedFragment = copyWrappedFragments.get(i);
            if (wrappedFragment == null) {
                continue;
            }

            int index = wrappedKeys.indexOf(wrappedFragment.key);
            if (index < 0) {
                continue;
            }

            wrappedFragments.set(index, copyWrappedFragments.get(i));
            if (copySavedStates.size() > i) {
                savedStates.set(index, copySavedStates.get(i));
            }
        }

        super.notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getItemPosition(@NonNull Object object) {
        WrappedKey<T> key = ((WrappedFragment<T>) object).key;
        int position = wrappedKeys.indexOf(key);
        if (position >= 0) {
            return position;
        } else {
            return PagerAdapter.POSITION_NONE;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getPageTitle(wrappedKeys.get(position).key);
    }

    @Nullable
    public CharSequence getPageTitle(T key) {
        return null;
    }

    @Nullable
    protected abstract T getNextKey(@NonNull T last);

    @Nullable
    protected abstract T getPrevKey(@NonNull T first);

    @Nullable
    protected abstract Bundle saveKeysState(@NonNull ArrayList<T> keys);

    @Nullable
    protected abstract List<T> restoreKeysState(@NonNull Bundle bundle);
}

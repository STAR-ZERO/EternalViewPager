package com.star_zero.eternalviewpager;

import android.support.v4.app.Fragment;

class WrappedFragment<T> {
    WrappedKey<T> key;
    Fragment fragment;

    WrappedFragment(WrappedKey<T> key, Fragment fragment) {
        this.key = key;
        this.fragment = fragment;
    }
}

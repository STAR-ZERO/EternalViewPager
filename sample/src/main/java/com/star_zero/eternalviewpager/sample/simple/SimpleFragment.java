package com.star_zero.eternalviewpager.sample.simple;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.star_zero.eternalviewpager.sample.R;
import com.star_zero.eternalviewpager.sample.databinding.FragmentPageBinding;

public class SimpleFragment extends Fragment {

    private FragmentPageBinding binding;

    public static SimpleFragment createInstance(int num) {
        Bundle args = new Bundle();
        args.putInt("num", num);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int num = getArguments().getInt("num");
        binding.setText(String.valueOf(num));
    }

}

package com.star_zero.eternalviewpager.sample.date;

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

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class DatePageFragment extends Fragment {

    private FragmentPageBinding binding;

    public static DatePageFragment createInstance(LocalDate date) {
        Bundle args = new Bundle();
        args.putSerializable("date", date);
        DatePageFragment fragment = new DatePageFragment();
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

        LocalDate date = (LocalDate) getArguments().getSerializable("date");
        binding.setText(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}

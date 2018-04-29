package com.star_zero.eternalviewpager.sample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.star_zero.eternalviewpager.sample.databinding.ActivityMainBinding;
import com.star_zero.eternalviewpager.sample.date.DateActivity;
import com.star_zero.eternalviewpager.sample.loop.LoopActivity;
import com.star_zero.eternalviewpager.sample.simple.SimpleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setHandler(this);
    }

    public void onClickSimple(View view) {
        Intent intent = new Intent(this, SimpleActivity.class);
        startActivity(intent);
    }

    public void onClickDate(View view) {
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }

    public void onClickLoop(View view) {
        Intent intent = new Intent(this, LoopActivity.class);
        startActivity(intent);
    }
}

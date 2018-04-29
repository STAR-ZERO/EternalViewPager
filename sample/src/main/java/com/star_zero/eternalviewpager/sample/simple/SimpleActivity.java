package com.star_zero.eternalviewpager.sample.simple;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.star_zero.eternalviewpager.sample.R;
import com.star_zero.eternalviewpager.sample.databinding.ActivitySimpleBinding;

public class SimpleActivity extends AppCompatActivity {

    private ActivitySimpleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_simple);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(), 0);
        binding.viewpager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                binding.viewpager.setSelectedKey(0);
                return true;
            case R.id.prev:
                binding.viewpager.movePrev();
                return true;
            case R.id.next:
                binding.viewpager.moveNext();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

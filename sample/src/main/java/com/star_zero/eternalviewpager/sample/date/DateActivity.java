package com.star_zero.eternalviewpager.sample.date;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.star_zero.eternalviewpager.sample.R;
import com.star_zero.eternalviewpager.sample.databinding.ActivityDateBinding;

import org.threeten.bp.LocalDate;

public class DateActivity extends AppCompatActivity {

    private ActivityDateBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatePagerAdapter adapter = new DatePagerAdapter(getSupportFragmentManager(), LocalDate.now());
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
                binding.viewpager.setSelectedKey(LocalDate.now());
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

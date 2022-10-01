package com.example.monbudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class DepensesActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DependanceVPAdapter dependanceVPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depenses);
        setWidgets();
        setListeners();
    }

    private void setWidgets() {
        tabLayout = findViewById(R.id.tab_layout_depences);
        viewPager2 = findViewById(R.id.view_pager);
        dependanceVPAdapter = new DependanceVPAdapter(this);
        viewPager2.setAdapter(dependanceVPAdapter);
    }

    private void setListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}
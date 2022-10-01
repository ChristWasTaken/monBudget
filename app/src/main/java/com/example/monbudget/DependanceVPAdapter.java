package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.monbudget.fragments.FixeFragment;
import com.example.monbudget.fragments.VariableFregment;

public class DependanceVPAdapter extends FragmentStateAdapter {

    public DependanceVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FixeFragment();
            case 1:
                return new VariableFregment();
            default:
                return new FixeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

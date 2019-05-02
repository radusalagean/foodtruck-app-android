package com.example.foodtruckclient.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dashboard.DashboardFragment;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.fragment.FragmentContract;
import com.example.foodtruckclient.generic.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FragmentContract {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getControllerComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        addDefaultFragmentIfNecessary(R.id.fragment_container);
    }

    @Override
    protected @NonNull BaseFragment getDefaultFragment() {
        return DashboardFragment.newInstance();
    }

    @Override
    public void setToolbarTitle(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }
}

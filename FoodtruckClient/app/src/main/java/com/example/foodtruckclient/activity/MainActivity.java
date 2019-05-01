package com.example.foodtruckclient.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dashboard.DashboardFragment;
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
        addFragmentIfNecessary(DashboardFragment.newInstance(), R.id.fragment_container);
    }

    @Override
    public void setToolbarTitle(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }
}

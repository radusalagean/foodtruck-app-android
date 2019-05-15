package com.example.foodtruckclient.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.screens.dashboard.DashboardFragment;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.activity.BaseActivity;
import com.example.foodtruckclient.screens.login.LoginFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addDefaultFragmentIfNecessary();
    }

    @NonNull
    @Override
    protected BaseFragment getDefaultFragment() {
        return DashboardFragment.newInstance();
    }

    @IdRes
    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) {
            getFragmentManagerCompat().beginTransaction()
                    .replace(getFragmentContainerId(), LoginFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

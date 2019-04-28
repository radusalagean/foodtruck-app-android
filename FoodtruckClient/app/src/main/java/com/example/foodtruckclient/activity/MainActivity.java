package com.example.foodtruckclient.activity;

import android.os.Bundle;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    FoodtruckApiService foodtruckApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fab)
    void testCall() {
        // TEST CALL
        foodtruckApiService.getAllFoodtrucks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Foodtruck>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.d("onSubscribe");
                    }

                    @Override
                    public void onNext(List<Foodtruck> foodtrucks) {
                        for (Foodtruck foodtruck : foodtrucks) {
                            Toast.makeText(MainActivity.this, foodtruck.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete");
                    }
                });
    }
}

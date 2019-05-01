package com.example.foodtruckclient.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.fragment.BaseMapFragment;
import com.example.foodtruckclient.generic.fragment.FragmentContract;
import com.example.foodtruckclient.generic.view.OnViewInflatedListener;
import com.example.foodtruckclient.view.MorphableFloatingActionButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DashboardFragment extends BaseMapFragment
        implements DashboardMVP.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.fab)
    MorphableFloatingActionButton fab;

    @Inject
    DashboardMVP.Presenter presenter;

    @Inject
    FragmentContract fragmentContract;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            fab.setAvd(position == 0 ? R.drawable.avd_add_to_location : R.drawable.avd_location_to_add);
            fab.morph();
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private OnViewInflatedListener onViewInflatedListener = new OnViewInflatedListener() {
        @Override
        public void onViewInflated(View view) {
            MapView mapView = view.findViewById(R.id.map_view);
            if (mapView != null) {
                DashboardFragment.this.mapViewManager.takeMapView(mapView);
            }
        }
    };
    private MapView testMapView; // TODO Remove
    private View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (viewPager.getCurrentItem()) {
                case DashboardPagerMapper.POSITION_MAP:
                    if (mapViewManager.getMapView() != null) {
                        testMapView = mapViewManager.getMapView();
                        mapViewManager.dropMapView();
                    } else {
                        mapViewManager.takeMapView(testMapView);
                    }
                    break;
                case DashboardPagerMapper.POSITION_LIST:
                    Toast.makeText(getContext(), "Hello from the list", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public DashboardFragment() {}

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        getControllerComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab.setAvd(R.drawable.ic_my_location_white_24dp);
        fab.setOnClickListener(fabOnClickListener);
        viewPager.setAdapter(new DashboardPagerAdapter(getContext(), onViewInflatedListener));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        fragmentContract.setToolbarTitle(R.string.dashboard_toolbar_title);
        presenter.loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.takeView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.dropView();
    }

    @Override
    public void updateData(List<DashboardFoodtruckViewModel> results) {
        Timber.d("results: %d", results.size());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

    }
}

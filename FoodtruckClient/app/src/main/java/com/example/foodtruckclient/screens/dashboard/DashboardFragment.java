package com.example.foodtruckclient.screens.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.decoration.ListItemDecoration;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.mapmvp.BaseMapFragment;
import com.example.foodtruckclient.generic.view.OnViewInflatedListener;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.view.MorphableFloatingActionButton;
import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DashboardFragment extends BaseMapFragment
        implements DashboardMVP.View, DashboardListListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.fab)
    MorphableFloatingActionButton fab;

    @Inject
    DashboardMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DashboardPagerAdapter pagerAdapter;
    private DashboardListAdapter listAdapter;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case DashboardPagerMapper.POSITION_LIST:
                    fab.morph(R.drawable.avd_location_to_add, R.drawable.ic_add_circle_outline_white_24dp);
                    break;
                case DashboardPagerMapper.POSITION_MAP:
                    fab.morph(R.drawable.avd_add_to_location, R.drawable.ic_my_location_white_24dp);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private OnViewInflatedListener onViewInflatedListener = new OnViewInflatedListener() {
        @Override
        public void onViewInflated(View view) {
            switch (view.getId()) {
                case R.id.layout_dashboard_list:
                    swipeRefreshLayout = view.findViewById(R.id.dashboard_list_swipe_to_refresh_layout);
                    recyclerView = view.findViewById(R.id.dashboard_list_recycler_view);
                    initListTab();
                    break;
                case R.id.layout_dashboard_map:
                    MapView mapView = view.findViewById(R.id.map_view);
                    DashboardFragment.this.mapViewManager.takeMapView(mapView);
                    break;
            }
        }
    };

    private View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (viewPager.getCurrentItem()) {
                case DashboardPagerMapper.POSITION_LIST:
                    // TODO Add new Foodtruck
                    break;
                case DashboardPagerMapper.POSITION_MAP:
                    presenter.zoomOnCurrentDeviceLocation();
                    break;
            }
        }
    };

    public DashboardFragment() {}

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.generateUniqueId(args);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        getControllerComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new DashboardPagerAdapter(getContext(), onViewInflatedListener);
        listAdapter = new DashboardListAdapter(this);
        presenter.loadViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
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
    protected void initViews() {
        toolbar.setTitle(R.string.dashboard_toolbar_title);
        activityContract.setActionBar(toolbar);
        fab.setImageResource(R.drawable.ic_add_circle_outline_white_24dp);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void registerListeners() {
        fab.setOnClickListener(fabOnClickListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void unregisterListeners() {
        fab.setOnClickListener(null);
        viewPager.clearOnPageChangeListeners();
    }

    @Nullable
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Timber.d("onRequestPermissionsResult(%d, %s, %s)", requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void updateFoodtrucks(List<Foodtruck> foodtrucks) {
        listAdapter.setFoodtrucks(foodtrucks);
    }

    @Override
    public void clearFoodtrucks() {
        listAdapter.clearFoodtrucks();
    }

    @Override
    public void switchToMapTab() {
        if (viewPager.getCurrentItem() != DashboardPagerMapper.POSITION_MAP) {
            viewPager.setCurrentItem(DashboardPagerMapper.POSITION_MAP);
        }
    }

    @Override
    public void initMapViewManager() {
        initMapViewManager(presenter.getOnMapReadyCallback());
    }

    @Override
    public void disposeMap() {
        presenter.disposeMap();
    }

    private void initListTab() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.reloadFoodtrucks());
        swipeRefreshLayout.setRefreshing(presenter.isRefreshing());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ListItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_dashboard_list_offset)));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onFoodtruckSelected(Foodtruck foodtruck) {
        presenter.viewFoodtruck(foodtruck.getId(), foodtruck.getName());
    }

    @Override
    public void onFoodtruckLocationButtonClicked(Foodtruck foodtruck) {
        presenter.zoomOnLocation(foodtruck.getCoordinates().getLatitude(),
                foodtruck.getCoordinates().getLongitude());
    }
}

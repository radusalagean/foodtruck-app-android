package com.busytrack.foodtruckclient.screen.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.generic.activity.ActivityContract;
import com.busytrack.foodtruckclient.generic.decoration.GridItemDecoration;
import com.busytrack.foodtruckclient.generic.decoration.ListItemDecoration;
import com.busytrack.foodtruckclient.generic.list.foodtruck.FoodtruckContract;
import com.busytrack.foodtruckclient.generic.mapmvp.BaseMapFragment;
import com.busytrack.foodtruckclient.generic.mvp.BaseMVP;
import com.busytrack.foodtruckclient.generic.view.OnViewInflatedListener;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.persistence.SharedPreferencesRepository;
import com.busytrack.foodtruckclient.util.ViewUtils;
import com.busytrack.foodtruckclient.view.MorphableFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends BaseMapFragment
        implements DashboardMVP.View, FoodtruckContract {

    private static final String ARG_RECYCLER_VIEW_LAYOUT_STATE = "recycler_view_layout_state";

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

    @Inject
    SharedPreferencesRepository sharedPreferencesRepository;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DashboardPagerAdapter pagerAdapter;
    private DashboardListAdapter listAdapter;
    private Parcelable recyclerViewLayoutState;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            MenuItem menuItem = toolbar.getMenu().findItem(R.id.menu_toggle_layout);
            switch (position) {
                case DashboardPagerMapper.POSITION_LIST:
                    fab.morph(R.drawable.avd_location_to_add, R.drawable.ic_add_circle_outline_white_24dp);
                    if (menuItem != null) {
                        menuItem.setVisible(true);
                    }
                    break;
                case DashboardPagerMapper.POSITION_MAP:
                    fab.morph(R.drawable.avd_add_to_location, R.drawable.ic_my_location_white_24dp);
                    if (menuItem != null) {
                        menuItem.setVisible(false);
                    }
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
                    swipeRefreshLayout = view.findViewById(R.id.dashboard_list_swipe_refresh_layout);
                    swipeRefreshLayout.setRefreshing(presenter.isRefreshing());
                    recyclerView = view.findViewById(R.id.dashboard_list_recycler_view);
                    swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
                    swipeRefreshLayout.setOnRefreshListener(() -> presenter.reloadFoodtrucks());
                    setLayoutType(sharedPreferencesRepository.getDashboardLayoutType());
                    if (recyclerView.getLayoutManager() != null && recyclerViewLayoutState != null) {
                        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewLayoutState);
                    }
                    recyclerViewLayoutState = null;
                    break;
                case R.id.layout_dashboard_map:
                    mapView = view.findViewById(R.id.map_view);
                    DashboardFragment.this.mapViewManager.takeMapView(mapView);
                    presenter.setOnInfoWindowClickListener(marker -> {
                        String foodtruckId = presenter.getFoodtruckIdByMarker(marker);
                        String foodtruckName = marker.getTitle();
                        if (foodtruckId != null) {
                            activityContract.showFoodtruckViewerScreen(foodtruckId, foodtruckName);
                        }
                    });
                    break;
            }
        }

        @Override
        public void onViewDestroyed(View view) {
            switch (view.getId()) {
                case R.id.layout_dashboard_list:
                    swipeRefreshLayout = null;
                    recyclerView.setAdapter(null);
                    recyclerView = null;
                    break;
                case R.id.layout_dashboard_map:
                    DashboardFragment.this.mapViewManager.dropMapView(mapView);
                    presenter.setOnInfoWindowClickListener(null);
                    break;
            }
        }
    };

    private View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (viewPager.getCurrentItem()) {
                case DashboardPagerMapper.POSITION_LIST:
                    if (activityContract.isUserAuthenticated()) {
                        activityContract.showFoodtruckEditorScreen();
                    } else {
                        activityContract.showLoginScreen();
                    }
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

    private void setLayoutType(int layoutType) {
        if (recyclerView.getItemDecorationCount() > 0) {
            recyclerView.removeItemDecorationAt(0);
        }
        if (layoutType == DashboardLayoutType.GRID) {
            int span = ViewUtils.computeGridLayoutSpan(getContext(),
                    getResources().getDimensionPixelSize(R.dimen.item_card_image_size) +
                            getResources().getDimensionPixelSize(R.dimen.general_layout_margin));
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), span));
            recyclerView.addItemDecoration(new GridItemDecoration(
                    getResources().getDimensionPixelSize(R.dimen.item_dashboard_list_offset),
                    span
            ));
        } else if (layoutType == DashboardLayoutType.LIST) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new ListItemDecoration(getResources()
                    .getDimensionPixelSize(R.dimen.item_dashboard_list_offset)));
        }
        listAdapter.setLayoutType(layoutType);
        recyclerView.setAdapter(listAdapter);
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
        listAdapter = new DashboardListAdapter(this,
                sharedPreferencesRepository.getDashboardLayoutType());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // called for logging purposes
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recyclerView != null && recyclerView.getLayoutManager() != null) {
            recyclerViewLayoutState = recyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(ARG_RECYCLER_VIEW_LAYOUT_STATE, recyclerViewLayoutState);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
        MenuItem item = menu.findItem(R.id.menu_toggle_layout);
        item.setIcon(DashboardLayoutIconMapper
                .getIconResId(sharedPreferencesRepository.getDashboardLayoutType() ^ 1));
        item.setVisible(viewPager.getCurrentItem() == DashboardPagerMapper.POSITION_LIST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toggle_layout) {
            int oldType = sharedPreferencesRepository.getDashboardLayoutType();
            int newType = oldType ^ 1;
            setLayoutType(newType);
            sharedPreferencesRepository.updateDashboardLayoutType(newType);
            toolbar.getMenu().findItem(R.id.menu_toggle_layout)
                    .setIcon(DashboardLayoutIconMapper.getIconResId(oldType));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(R.string.dashboard_toolbar_title);
        activityContract.setToolbar(toolbar);
        fab.setImageResource(R.drawable.ic_add_circle_outline_white_24dp);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(DashboardPagerMapper.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void disposeViews() {
        viewPager.setAdapter(null);
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

    @Override
    protected void loadData() {
        if (!presenter.restoreDataFromCache()) {
            presenter.loadViewModel();
        }
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {
        recyclerViewLayoutState = savedInstanceState.getParcelable(ARG_RECYCLER_VIEW_LAYOUT_STATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BaseMVP.View> BaseMVP.Presenter<T> getPresenter() {
        return (BaseMVP.Presenter<T>) presenter;
    }

    @Nullable
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void updateFoodtrucks(List<Foodtruck> foodtrucks) {
        listAdapter.setFoodtrucks(foodtrucks);
    }

    @Override
    public void switchToMapTab() {
        if (viewPager.getCurrentItem() != DashboardPagerMapper.POSITION_MAP) {
            viewPager.setCurrentItem(DashboardPagerMapper.POSITION_MAP);
        }
    }

    @Override
    public void onFoodtruckSelected(Foodtruck foodtruck) {
        activityContract.showFoodtruckViewerScreen(foodtruck.getId(), foodtruck.getName());
    }

    @Override
    public void onFoodtruckLocationButtonClicked(Foodtruck foodtruck) {
        presenter.zoomOnLocation(foodtruck.getCoordinates().getLatitude(),
                foodtruck.getCoordinates().getLongitude(), false);
    }
}

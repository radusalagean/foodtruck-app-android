package com.example.foodtruckclient.generic.viewmodel;

import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.screen.dashboard.DashboardFragment;
import com.example.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerFragment;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;

import java.util.Map;

public class ViewModelManager {

    private Map<Class<? extends BaseFragment>, BaseViewModelRepository<? extends BaseViewModel>>
            viewModelRepositoryMap;

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener;

    public ViewModelManager(DashboardViewModelRepository dashboardViewModelRepository,
                            FoodtruckViewerViewModelRepository foodtruckViewerViewModelRepository) {
        viewModelRepositoryMap = new ArrayMap<>();
        viewModelRepositoryMap.put(DashboardFragment.class, dashboardViewModelRepository);
        viewModelRepositoryMap.put(FoodtruckViewerFragment.class, foodtruckViewerViewModelRepository);
    }

    public void registerListener(FragmentManager fragmentManager) {
        if (onBackStackChangedListener != null) {
            throw new IllegalStateException("Attempting to register onBackStackChangeListener multiple times");
        }
        onBackStackChangedListener = () -> {
            for (BaseViewModelRepository<? extends BaseViewModel> repository :
                    viewModelRepositoryMap.values()) {
                repository.disposeUnusedViewModels(fragmentManager);
            }
        };
        fragmentManager.addOnBackStackChangedListener(onBackStackChangedListener);
    }

    public void unregisterListener(FragmentManager fragmentManager) {
        fragmentManager.removeOnBackStackChangedListener(onBackStackChangedListener);
        onBackStackChangedListener = null;
    }
}

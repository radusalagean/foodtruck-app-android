package com.example.foodtruckclient.generic.viewmodel;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.screen.dashboard.DashboardFragment;
import com.example.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorFragment;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorViewModelRepository;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerFragment;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.example.foodtruckclient.screen.profile.ProfileFragment;
import com.example.foodtruckclient.screen.profile.ProfileViewModelRepository;

import java.util.Map;
import java.util.UUID;

public class ViewModelManager {

    private Map<Class<? extends BaseFragment>, BaseViewModelRepository<? extends BaseViewModel>>
            viewModelRepositoryMap;

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener;

    public ViewModelManager(DashboardViewModelRepository dashboardViewModelRepository,
                            FoodtruckViewerViewModelRepository foodtruckViewerViewModelRepository,
                            ProfileViewModelRepository profileViewModelRepository,
                            FoodtruckEditorViewModelRepository foodtruckEditorViewModelRepository) {
        viewModelRepositoryMap = new ArrayMap<>();
        viewModelRepositoryMap.put(DashboardFragment.class, dashboardViewModelRepository);
        viewModelRepositoryMap.put(FoodtruckViewerFragment.class, foodtruckViewerViewModelRepository);
        viewModelRepositoryMap.put(ProfileFragment.class, profileViewModelRepository);
        viewModelRepositoryMap.put(FoodtruckEditorFragment.class, foodtruckEditorViewModelRepository);
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

    @Nullable
    public String getDashboardUuidString() {
        BaseViewModelRepository repository = viewModelRepositoryMap.get(DashboardFragment.class);
        if (repository instanceof DashboardViewModelRepository) {
            return ((DashboardViewModelRepository) repository).getDashboardUuidString();
        }
        return null;
    }

    public void sendInvalidationBundle(InvalidationBundle invalidationBundle, UUID currentScreenUuid) {
        for (BaseViewModelRepository<? extends BaseViewModel> repository :
                viewModelRepositoryMap.values()) {
            repository.sendInvalidationBundle(invalidationBundle, currentScreenUuid);
        }
    }
}

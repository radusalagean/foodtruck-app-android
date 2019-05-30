package com.example.foodtruckclient.generic.viewmodel;

import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import timber.log.Timber;

public abstract class BaseViewModelRepository<T extends BaseViewModel> {

    protected Map<UUID, T> viewModelMap;

    public BaseViewModelRepository() {
        this.viewModelMap = new ArrayMap<>();
    }

    public T getViewModel(UUID uuid) {
        return viewModelMap.get(uuid);
    }

    public void addViewModel(UUID uuid, T viewModel) {
        Timber.d("addViewModel(%s, %s)", uuid, viewModel);
        viewModelMap.put(uuid, viewModel);
    }

    public void disposeUnusedViewModels(FragmentManager fragmentManager) {
        Iterator<Map.Entry<UUID, T>> iterator = viewModelMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (fragmentManager.findFragmentByTag(iterator.next().getKey().toString()) == null) {
                // No reference to the fragment found, so release the associated View Model
                // reference from the map
                Timber.d("Removing unused view model");
                iterator.remove();
            }
        }
    }

    /**
     * Send the invalidation bundle to all screens, except the current one
     */
    public void sendInvalidationBundle(InvalidationBundle invalidationBundle, UUID currentScreenUuid) {
        for (Map.Entry<UUID, T> entry : viewModelMap.entrySet()) {
            if (!entry.getKey().equals(currentScreenUuid)) {
                entry.getValue().processInvalidationBundle(invalidationBundle);
            }
        }
    }
}

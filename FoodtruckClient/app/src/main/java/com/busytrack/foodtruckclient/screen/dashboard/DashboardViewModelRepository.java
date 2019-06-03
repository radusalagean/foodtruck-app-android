package com.busytrack.foodtruckclient.screen.dashboard;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModelRepository;

public class DashboardViewModelRepository extends BaseViewModelRepository<DashboardViewModel> {

    @Nullable
    public String getDashboardUuidString() {
        Object[] uuids = viewModelMap.keySet().toArray();
        if (uuids != null && uuids.length > 0) {
            return uuids[0].toString();
        }
        return null;
    }
}

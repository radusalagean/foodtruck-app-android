package com.example.foodtruckclient.dashboard;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class DashboardPresenter implements DashboardMVP.Presenter {

    private DashboardMVP.Model model;
    private DashboardMVP.View view;
    private CompositeDisposable compositeDisposable;

    public DashboardPresenter(DashboardMVP.Model model) {
        this.model = model;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void loadData() {
        compositeDisposable.add(model.getResults()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<DashboardFoodtruckViewModel>>() {
                    @Override
                    public void onNext(List<DashboardFoodtruckViewModel> dashboardFoodtruckViewModels) {
                        if (view != null) {
                            view.updateData(dashboardFoodtruckViewModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {}
                }));
    }

    @Override
    public void takeView(DashboardMVP.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
        this.view = null;
    }
}

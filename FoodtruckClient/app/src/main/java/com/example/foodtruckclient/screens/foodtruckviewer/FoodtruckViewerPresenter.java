package com.example.foodtruckclient.screens.foodtruckviewer;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.generic.mvp.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class FoodtruckViewerPresenter extends BasePresenter<FoodtruckViewerMVP.View, FoodtruckViewerMVP.Model>
        implements FoodtruckViewerMVP.Presenter {

    public FoodtruckViewerPresenter(FoodtruckViewerMVP.Model model) {
        super(model);
    }

    @Override
    public void loadViewModel(String foodtruckId, boolean refresh) {
        Observable<FoodtruckViewerViewModel> observable = refresh ?
                model.getFreshViewModel(foodtruckId) : model.getViewModel(foodtruckId);
        compositeDisposable.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FoodtruckViewerViewModel>() {
                    @Override
                    public void onNext(FoodtruckViewerViewModel foodtruckViewerViewModel) {
                        processData(foodtruckViewerViewModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        view.toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                }));
    }

    @Override
    public void reloadData(String foodtruckId) {
        loadViewModel(foodtruckId, true);
    }

    private void processData(@NonNull FoodtruckViewerViewModel viewModel) {
        view.updateFoodtruck(viewModel.getFoodtruck());
        view.updateReviews(viewModel.getReviews());
        view.updateMyReview(viewModel.getMyReview());
    }
}

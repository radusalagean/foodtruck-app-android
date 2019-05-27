package com.example.foodtruckclient.screen.foodtruckeditor;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.image.ImageBundle;
import com.example.foodtruckclient.generic.mapmvp.BaseMapMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface FoodtruckEditorMVP {

    interface Model extends BaseMapMVP.Model<FoodtruckEditorViewModel> {
        FoodtruckEditorViewModel getViewModel();
        @Nullable Foodtruck getOriginalFoodtruck();
        void setOriginalFoodtruck(Foodtruck originalFoodtruck);
        Observable<Foodtruck> addFoodtruck(Foodtruck foodtruck);
        Observable<Message> updateFoodtruck(String foodtruckId, Foodtruck foodtruck);
        Observable<Message> uploadFoodtruckImage(String foodtruckId, MultipartBody.Part image);
        Observable<Message> deleteFoodtruckImage(String foodtruckId);
    }

    interface View extends BaseMapMVP.View {
        void onImageFileReceived(@Nullable File file, boolean fromCache);
    }

    interface Presenter extends BaseMapMVP.Presenter<View> {
        boolean isInEditMode();
        void setFoodtruck(Foodtruck foodtruck);
        String getTypedName();
        void setTypedName(String typedName);
        String getTypedFoodtype();
        void setTypedFoodtype(String typedFoodtype);
        List<String> getAddedFoodtypes();
        void setAddedFoodtypes(List<String> addedFoodtypes);
        Coordinates getCoordinates();
        void setCoordinates(Coordinates coordinates);
        ImageBundle getImageBundle();
        void setImageBundle(ImageBundle imageBundle);
        File getImageFile();
        void setImageFile(File file);
        void onSaveButtonClicked();
    }
}

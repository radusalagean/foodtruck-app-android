package com.busytrack.foodtruckclient.screen.foodtruckeditor;

import com.busytrack.foodtruckclient.generic.contentinvalidation.ContentType;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.busytrack.foodtruckclient.generic.image.ImageBundle;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.io.File;
import java.util.List;

public class FoodtruckEditorViewModel extends BaseViewModel {

    private String foodtruckId;
    private String typedName;
    private String typedFoodtype;
    private List<String> addedFoodtypes;
    private Coordinates coordinates;
    private ImageBundle imageBundle;
    private File imageFile;

    public String getFoodtruckId() {
        return foodtruckId;
    }

    public void setFoodtruckId(String foodtruckId) {
        this.foodtruckId = foodtruckId;
    }

    public String getTypedName() {
        return typedName;
    }

    public void setTypedName(String typedName) {
        this.typedName = typedName;
    }

    public String getTypedFoodtype() {
        return typedFoodtype;
    }

    public void setTypedFoodtype(String typedFoodtype) {
        this.typedFoodtype = typedFoodtype;
    }

    public List<String> getAddedFoodtypes() {
        return addedFoodtypes;
    }

    public void setAddedFoodtypes(List<String> addedFoodtypes) {
        this.addedFoodtypes = addedFoodtypes;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ImageBundle getImageBundle() {
        return imageBundle;
    }

    public void setImageBundle(ImageBundle imageBundle) {
        this.imageBundle = imageBundle;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public static FoodtruckEditorViewModel createFrom(Foodtruck foodtruck) {
        FoodtruckEditorViewModel viewModel = new FoodtruckEditorViewModel();
        viewModel.setFoodtruckId(foodtruck.getId());
        viewModel.setTypedName(foodtruck.getName());
        viewModel.setAddedFoodtypes(foodtruck.getFoodtypes());
        viewModel.setCoordinates(foodtruck.getCoordinates());
        viewModel.setImageBundle(new ImageBundle(foodtruck.getImageUrl(),
                foodtruck.getImageSignature()));
        return viewModel;
    }

    @Override
    public void processInvalidationBundle(InvalidationBundle invalidationBundle) {
        if (invalidationBundle.getContentId().equals(foodtruckId) &&
                invalidationBundle.getContentType() == ContentType.FOODTRUCK &&
                invalidationBundle.getInvalidationType() == InvalidationType.REMOVE) {
            invalidationEffects |= InvalidationEffect.POP_FRAGMENT;
        }
    }
}

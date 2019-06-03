package com.busytrack.foodtruckclient.generic.image;

public class ImageBundle {

    private String imageUrl;
    private String signature;

    public ImageBundle(String imageUrl, String signature) {
        this.imageUrl = imageUrl;
        this.signature = signature;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSignature() {
        return signature;
    }
}

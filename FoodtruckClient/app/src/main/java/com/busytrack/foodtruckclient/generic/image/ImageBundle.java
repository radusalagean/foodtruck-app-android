package com.busytrack.foodtruckclient.generic.image;

/**
 * Stores the image url and the associated signature, used for Glide caching.<br>
 * Signatures are used because the default Glide signature is based on the url.
 * Since the server uses the same url, even if images are replaced, we need a custom signature.
 */
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

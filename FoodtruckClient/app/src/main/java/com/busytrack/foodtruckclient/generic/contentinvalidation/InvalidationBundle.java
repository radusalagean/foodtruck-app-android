package com.busytrack.foodtruckclient.generic.contentinvalidation;

import timber.log.Timber;

/**
 * A bundle that is being sent and processed whenever data in a screen changes and can render the
 * data in the other screens outdated
 */
public class InvalidationBundle {

    private String contentId;
    private @ContentType int contentType;
    private @InvalidationType int invalidationType;

    public InvalidationBundle(String contentId, @ContentType int contentType,
                              @InvalidationType int invalidationType) {
        Timber.d("New InvalidationBundle(%s, %d, %d)",
                contentId, contentType, invalidationType);
        this.contentId = contentId;
        this.contentType = contentType;
        this.invalidationType = invalidationType;
    }

    public String getContentId() {
        return contentId;
    }

    public int getContentType() {
        return contentType;
    }

    public int getInvalidationType() {
        return invalidationType;
    }
}

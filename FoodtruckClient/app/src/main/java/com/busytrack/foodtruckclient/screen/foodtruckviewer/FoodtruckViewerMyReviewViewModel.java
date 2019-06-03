package com.busytrack.foodtruckclient.screen.foodtruckviewer;

public class FoodtruckViewerMyReviewViewModel {

    private int state;
    private String title;
    private String content;
    private float rating;

    public FoodtruckViewerMyReviewViewModel(int state, String title, String content, float rating) {
        this.state = state;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public int getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public float getRating() {
        return rating;
    }
}

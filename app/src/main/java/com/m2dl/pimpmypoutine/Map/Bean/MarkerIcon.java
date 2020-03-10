package com.m2dl.pimpmypoutine.Map.Bean;

import android.graphics.drawable.Drawable;

import org.osmdroid.views.overlay.Marker;

public class MarkerIcon {
    private Drawable drawable;
    private String url;
    private Marker marker;
    private String image;

    public MarkerIcon() {
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

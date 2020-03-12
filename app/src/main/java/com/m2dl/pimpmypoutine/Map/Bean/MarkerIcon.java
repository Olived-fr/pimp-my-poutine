package com.m2dl.pimpmypoutine.Map.Bean;

import android.graphics.drawable.Drawable;

import org.osmdroid.views.overlay.Marker;

public class MarkerIcon {
    private Drawable drawable;
    private Marker marker;

    public MarkerIcon() {
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}

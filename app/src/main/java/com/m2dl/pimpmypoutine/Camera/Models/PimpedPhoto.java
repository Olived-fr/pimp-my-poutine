package com.m2dl.pimpmypoutine.Camera.Models;

import android.graphics.Bitmap;
import android.location.Location;

public class PimpedPhoto {
    private Bitmap bmp;
    private String imgUrl;
    private Location location;

    public PimpedPhoto(Bitmap bmp, String imgUrl, Location location) {
        this.bmp = bmp;
        this.imgUrl = imgUrl;
        this.location = location;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

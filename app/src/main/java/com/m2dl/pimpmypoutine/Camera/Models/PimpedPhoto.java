package com.m2dl.pimpmypoutine.Camera.Models;

import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import java.io.Serializable;

public class PimpedPhoto implements Serializable {
    private Image image;
    private String imgUrl;//url image firebase
    private Location location;//cordonnées GPS
    private String path;//chemin de l'image dans l'espace de stockage du smartphone

    public PimpedPhoto(Image image, String imgUrl, Location location, String path) {
        this.image = image;
        this.imgUrl = imgUrl;
        this.location = location;
        this.path = path;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }
}

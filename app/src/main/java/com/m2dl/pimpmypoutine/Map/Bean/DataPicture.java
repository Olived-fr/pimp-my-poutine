package com.m2dl.pimpmypoutine.Map.Bean;

import java.io.File;

public class DataPicture {
    private double latitude;
    private double longitude;
    private File file;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}

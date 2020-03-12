package com.m2dl.pimpmypoutine.Map.Api;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.m2dl.pimpmypoutine.Map.Bean.DataPicture;
import com.m2dl.pimpmypoutine.Map.Bean.MarkerIcon;
import com.m2dl.pimpmypoutine.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.exifinterface.media.ExifInterface;

public class MapApi {
    private List<MarkerIcon> markerIconList;

    public void MapApi() {
        markerIconList = new ArrayList<>();
    }

    public void setMarkerIconList(List<MarkerIcon> markerIconList) {
        this.markerIconList = markerIconList;
    }

    public List<MarkerIcon> getMarkerIconList() {
        return markerIconList;
    }

    public void addMarkerIcon(MarkerIcon markerIcon) {
        if (markerIconList == null) {
            markerIconList = new ArrayList<>();
        }
        markerIconList.add(markerIcon);
    }

    public void deleteMarkerIconList() {
        this.markerIconList.clear();
    }

    public void initMap(MapView myOpenMapView) {
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setClickable(true);
        myOpenMapView.getController().setZoom(10);
        myOpenMapView.getController().setCenter(new GeoPoint(43.6, 1.433));
    }

    public void addScale(MapView myOpenMapView) {
        //ajout d'une échelle sur la carte
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(myOpenMapView);
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
    }

    public void addCompass(MapView myOpenMapView, Context context) {
        //ajout d'un boussole sur la carte
        CompassOverlay mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), myOpenMapView);
        mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(mCompassOverlay);
    }

    public void addLocation(MapView myOpenMapView, Context context) {
        //ajout de la position GPS
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), myOpenMapView);
        mLocationOverlay.enableMyLocation();
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mLocationOverlay);
    }

    public MarkerIcon addMarker(MapView myOpenMapView, Resources resources) throws IOException {

        Marker marker = new Marker(myOpenMapView);

        //On défini la position du marker
        marker.setPosition(new GeoPoint(43.6, 1.4333));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        //On défini l'image du marker
        Drawable image = resources.getDrawable(R.drawable.imagetest);
        marker.setImage(image);

        //On défini l'icone du marker
        Drawable dr = resources.getDrawable(R.drawable.maps_marker_black_icon);
        //On transforme l'objet Drawable en Bitmap pour redéfinir sa taille
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable icone = new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        marker.setIcon(icone);

        MarkerIcon markerIcon = new MarkerIcon();
        markerIcon.setDrawable(icone);

        setClick(myOpenMapView, marker, markerIcon, resources);
        myOpenMapView.getOverlays().add(marker);

        markerIcon.setMarker(marker);
        addMarkerIcon(markerIcon);

        return markerIcon;
    }

    private void setClick(final MapView myOpenMapView, Marker marker, final MarkerIcon markerIcon, final Resources resources) {
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Drawable image = markerIcon.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
                Drawable drawable;
                if (marker.getInfoWindow().isOpen()) {

                    drawable = new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true));
                    marker.setIcon(drawable);
                    marker.closeInfoWindow();
                } else {

                    drawable = new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                    marker.setIcon(drawable);
                    marker.showInfoWindow();
                }

                myOpenMapView.getOverlays().add(marker);

                return true;
            }
        });
    }

    public void getAllPictures(){
        //initialisation de la liste de markerIcone
    }

    public List<DataPicture> getDataPicture(List<String> uriList) {
        List<DataPicture> dataPictureList = new ArrayList<>();
        for (String uri : uriList) {

            try {
                ExifInterface exif = new ExifInterface(uri);
                System.out.println("//////////////////////////////"+uri);
                double lat = exif.getLatLong()[0];
                double lng = exif.getLatLong()[1];
                DataPicture dataPicture = new DataPicture();
                dataPicture.setUrl(uri);
                dataPicture.setLongitude(lng);
                dataPicture.setLatitude(lat);
                dataPictureList.add(dataPicture);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dataPictureList;
    }

    public List<MarkerIcon> addMarkers(MapView myOpenMapView, Resources resources, List<DataPicture> pictures) throws IOException {

        List<MarkerIcon> markerIcons = new ArrayList<>();

        for(DataPicture picture : pictures) {
            Marker marker = new Marker(myOpenMapView);

            //On défini la position du marker
            marker.setPosition(new GeoPoint(picture.getLatitude(), picture.getLongitude()));
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            //On défini l'image du marker
            //URL url = new URL(picture.getUrl());
            //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Bitmap bmp = BitmapFactory.decodeFile(picture.getUrl());
            Drawable image = new BitmapDrawable(resources, bmp);
            marker.setImage(image);

            //On défini l'icone du marker
            Drawable dr = resources.getDrawable(R.drawable.maps_marker_black_icon);
            //On transforme l'objet Drawable en Bitmap pour redéfinir sa taille
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable icone = new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true));
            marker.setIcon(icone);

            MarkerIcon markerIcon = new MarkerIcon();
            markerIcon.setDrawable(icone);

            setClick(myOpenMapView, marker, markerIcon, resources);
            myOpenMapView.getOverlays().add(marker);

            markerIcon.setMarker(marker);
            addMarkerIcon(markerIcon);

            markerIcons.add(markerIcon);
        }
        
        return markerIcons;
    }
}

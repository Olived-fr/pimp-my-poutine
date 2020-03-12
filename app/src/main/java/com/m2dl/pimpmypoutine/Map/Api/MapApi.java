package com.m2dl.pimpmypoutine.Map.Api;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.m2dl.pimpmypoutine.Map.Bean.DataPicture;
import com.m2dl.pimpmypoutine.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MapApi {

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

    public void addMarker( DataPicture dataPicture, File image, MapView myOpenMapView, Resources resources) {
        Marker marker = new Marker(myOpenMapView);

        //On défini la position du marker
        marker.setPosition(new GeoPoint(dataPicture.getLatitude(), dataPicture.getLongitude()));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        //On défini l'icone du marker
        Drawable dr = resources.getDrawable(R.drawable.maps_marker_black_icon);
        //On transforme l'objet Drawable en Bitmap pour redéfinir sa taille
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable icon = new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        marker.setIcon(icon);
        InputStream targetStream = null;

        try {
            targetStream = new FileInputStream(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //On défini l'image du marker
        marker.setImage(Drawable.createFromStream(targetStream, image.getName()));

        //ajout du marker sur la map
        myOpenMapView.getOverlays().add(marker);

    }
}

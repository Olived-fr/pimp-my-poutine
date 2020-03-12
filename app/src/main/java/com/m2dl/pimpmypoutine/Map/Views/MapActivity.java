package com.m2dl.pimpmypoutine.Map.Views;

import android.os.Bundle;
import com.m2dl.pimpmypoutine.BuildConfig;
import com.m2dl.pimpmypoutine.Database.Firebase;
import com.m2dl.pimpmypoutine.Map.Api.MapApi;
import com.m2dl.pimpmypoutine.R;

import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.views.MapView;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapApi mapApi = new MapApi();
        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        setContentView(R.layout.activity_map);
        MapView myOpenMapView = findViewById(R.id.mapview);
        mapApi.initMap(myOpenMapView);
        mapApi.addScale(myOpenMapView);
        mapApi.addCompass(myOpenMapView, getApplicationContext());
        mapApi.addLocation(myOpenMapView, getApplicationContext());

        Firebase firebase = new Firebase();
        //Récupération des images stockées sur Firebase puis affichage des markers
        firebase.firebaseRequest(mapApi, myOpenMapView, getResources());

    }
}

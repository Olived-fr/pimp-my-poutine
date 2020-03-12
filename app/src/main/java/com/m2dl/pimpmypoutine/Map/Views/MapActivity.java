package com.m2dl.pimpmypoutine.Map.Views;

import android.os.Bundle;

import com.m2dl.pimpmypoutine.BuildConfig;
import com.m2dl.pimpmypoutine.Database.Firebase;
import com.m2dl.pimpmypoutine.Map.Api.MapApi;
import com.m2dl.pimpmypoutine.Map.Bean.DataPicture;
import com.m2dl.pimpmypoutine.R;

import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    private MapView myOpenMapView;
    private MapApi mapApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapApi = new MapApi();
        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        setContentView(R.layout.activity_map);
        System.out.println("Démarrage de l'activité map");
        myOpenMapView = findViewById(R.id.mapview);
        mapApi.initMap(myOpenMapView);
        mapApi.addScale(myOpenMapView);
        mapApi.addCompass(myOpenMapView, getApplicationContext());
        mapApi.addLocation(myOpenMapView, getApplicationContext());

        //ajout d'un marker
        try {
            ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
            if (myList != null
                    && !myList.isEmpty()) {
                List<DataPicture> dataPictureList = mapApi.getDataPicture(myList);
                mapApi.addMarkers(myOpenMapView, getResources(), dataPictureList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        myOpenMapView.invalidate();
    }
}

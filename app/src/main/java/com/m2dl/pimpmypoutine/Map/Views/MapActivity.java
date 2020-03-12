package com.m2dl.pimpmypoutine.Map.Views;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.m2dl.pimpmypoutine.BuildConfig;
import com.m2dl.pimpmypoutine.Database.Firebase;
import com.m2dl.pimpmypoutine.Map.Api.MapApi;
import com.m2dl.pimpmypoutine.Map.Bean.DataPicture;
import com.m2dl.pimpmypoutine.R;

import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.views.MapView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

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

        Firebase firebase = new Firebase();
        //Récupération des images stockées sur Firebase puis affichage des markers
        firebase.firebaseRequest(mapApi, myOpenMapView, getResources());

    }
}

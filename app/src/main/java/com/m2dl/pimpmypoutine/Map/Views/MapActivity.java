package com.m2dl.pimpmypoutine.Map.Views;

import android.os.Bundle;

import com.m2dl.pimpmypoutine.Database.Firebase;
import com.m2dl.pimpmypoutine.Map.Api.MapApi;
import com.m2dl.pimpmypoutine.R;

import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    private MapView myOpenMapView;
    private MapApi mapApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapApi = new MapApi();
        setContentView(R.layout.activity_map);
        System.out.println("Démarrage de l'activité map");
        myOpenMapView = findViewById(R.id.mapview);

        mapApi.initMap(myOpenMapView);
        mapApi.addScale(myOpenMapView);
        mapApi.addCompass(myOpenMapView, getApplicationContext());
        mapApi.addLocation(myOpenMapView, getApplicationContext());

        //ajout d'un marker
        try {
            mapApi.addMarker(myOpenMapView, getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Firebase firebase = new Firebase();
        List<String> stringList = firebase.getAllImages();
        myOpenMapView.invalidate();

    }

//    //Récupérer un png depuis /assets
//    AssetManager manager = getAssets();
//    InputStream open = manager.open("maps_marker_black_icon.png");
//    Bitmap bitmap = BitmapFactory.decodeStream(open);
//    Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));

//    // Récupérer une image depuis une URL
//    URL url =new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

}

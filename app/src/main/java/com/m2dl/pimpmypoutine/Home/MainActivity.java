package com.m2dl.pimpmypoutine.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.m2dl.pimpmypoutine.Camera.Views.CameraActivity;
import com.m2dl.pimpmypoutine.Database.Firebase;
import com.m2dl.pimpmypoutine.Map.Views.MapActivity;
import com.m2dl.pimpmypoutine.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Demande d'autorisation
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
        }

        Firebase firebase = new Firebase();
        firebase.getAllImages();

       //Configuration du bouton pour acceder à la carte
        Button openMap = findViewById(R.id.openMap);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapActivity = new Intent(getBaseContext(), MapActivity.class);
                startActivity(mapActivity);
            }
        });

        //Configuration du bouton pour acceder à la carte
        Button openCamera = findViewById((R.id.openCamera));
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraActivity = new Intent(getBaseContext(), CameraActivity.class);
                startActivity(cameraActivity);
            }
        });


    }
}

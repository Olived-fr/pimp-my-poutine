package com.m2dl.pimpmypoutine.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.m2dl.pimpmypoutine.BuildConfig;
import com.m2dl.pimpmypoutine.Camera.CameraActivity;
import com.m2dl.pimpmypoutine.Map.Views.MapActivity;
import com.m2dl.pimpmypoutine.R;
import com.m2dl.pimpmypoutine.utils.PoutineUtils;

import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        //Configuration du bouton pour acceder à la carte
        Button openMap = findViewById(R.id.openMap);
        //setButton(openMap, PoutineUtils.MAP_ACTIVITY);

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
        }

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraActivity = new Intent(getBaseContext(), MapActivity.class);
                startActivity(cameraActivity);
            }
        });
    }

    private void setButton(Button button, final String activity) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (activity) {
                    case PoutineUtils.CAMERA_ACTIVITY:
                        Intent cameraActivity = new Intent(getBaseContext(), MapActivity.class);
                        startActivity(cameraActivity);
                        break;
                    case PoutineUtils.MAP_ACTIVITY:
                        Intent mapActivity = new Intent(getBaseContext(), CameraActivity.class);
                        startActivity(mapActivity);
                        break;
                    default:
                        break;
                }

            }
        });
    }


}

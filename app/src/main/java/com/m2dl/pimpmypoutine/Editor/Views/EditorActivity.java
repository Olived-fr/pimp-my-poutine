package com.m2dl.pimpmypoutine.Editor.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import androidx.exifinterface.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.m2dl.pimpmypoutine.Database.Firebase;
import com.m2dl.pimpmypoutine.Home.MainActivity;
import com.m2dl.pimpmypoutine.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class EditorActivity extends AppCompatActivity {
    private EditorView viewtest;
    static String pimpedPhoto;
    private ImageButton buttonFiltre1, buttonFiltre2;
    private Button buttonValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        pimpedPhoto = intent.getStringExtra("pathPhoto");
        setContentView(R.layout.activity_editor);
        viewtest =(EditorView) findViewById(R.id.editorView);
        buttonFiltre1 = findViewById(R.id.buttonFiltre1);
        buttonFiltre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewtest.getPoutine("fromage");
            }
        });

        buttonFiltre2 = findViewById(R.id.buttonFiltre2);
        buttonFiltre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewtest.getPoutine("effiloché");
            }
        });

        buttonValid = findViewById(R.id.buttonValid);
        buttonValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString() + "/" + "temp" + ".png");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                viewtest.validImage().compress(Bitmap.CompressFormat.JPEG, 100, fOut);

                Location location = getLocation();
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(f.getPath());
                    exif.setGpsInfo(location);
                    exif.saveAttributes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Firebase firebase = new Firebase();
                firebase.uploadImage(f.getPath());
                Intent mainActivity = new Intent(EditorActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }

    public Location getLocation ()
    {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        try {
            return location;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}

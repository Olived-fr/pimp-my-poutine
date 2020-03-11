package com.m2dl.pimpmypoutine.Editor.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;
import com.m2dl.pimpmypoutine.Camera.Views.ShowPictureActivity;
import com.m2dl.pimpmypoutine.Home.MainActivity;
import com.m2dl.pimpmypoutine.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

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
                //  buttonFiltre1.setText("oui");
                System.out.println("fromage");

                viewtest.getPoutine("fromage");
            }
        });

        buttonFiltre2 = findViewById(R.id.buttonFiltre2);
        buttonFiltre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  buttonFiltre1.setText("oui");
                System.out.println("effiloché");

                viewtest.getPoutine("effiloché");
            }
        });

        buttonValid = findViewById(R.id.buttonValid);
        buttonValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  buttonFiltre1.setText("oui");
                System.out.println("valider");
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString() + "/" + "test" + ".png");
                System.out.println("f " + f.getPath());

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
                viewtest.validImage().compress(Bitmap.CompressFormat.PNG, 100, fOut);
              /*  ExifInterface exif = null;
                try {
                    exif = new ExifInterface(f.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, String.valueOf(latLng.latitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, String.valueOf(latLng.longitude));
*/
                LatLng latLng = getLocation();

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(f.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GPS.convert(latLng.latitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GPS.latitudeRef(latLng.latitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GPS.convert(latLng.longitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GPS.longitudeRef(latLng.longitude));
                try {
                    exif.saveAttributes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    exif.saveAttributes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    ExifInterface exif2 = new ExifInterface(f.getPath());
                    Log.e("LATITUDE EXTRACTED", Objects.requireNonNull(exif2.getAttribute(ExifInterface.TAG_GPS_LATITUDE)));
                    Log.e("LONGITUDE EXTRACTED", Objects.requireNonNull(exif2.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent mainActivity = new Intent(EditorActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    public LatLng getLocation ()
    {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                //   return ;
            }
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            lat = location.getLatitude ();
            // latitudeGPS = location.getLatitude ();
            lon = location.getLongitude ();
            // longitudeGPS = location.getLongitude ();
            System.out.println("location.getLongitude()" + location.getLongitude());
            System.out.println("location.getLongitude()" + location.getLatitude());

            return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}

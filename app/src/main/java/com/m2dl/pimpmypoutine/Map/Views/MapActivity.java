package com.m2dl.pimpmypoutine.Map.Views;

import android.os.Bundle;

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

    private StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pimp-my-poutine.appspot.com");

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

        initFirebase();
        //ajout d'un marker
        /*try {
            ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
            if (myList != null
                    && !myList.isEmpty()) {
                List<DataPicture> dataPictureList = mapApi.getDataPicture(myList);
                mapApi.addMarkers(myOpenMapView, getResources(), dataPictureList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //myOpenMapView.invalidate();
    }

    public void initFirebase() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                try {
                    downloadImagesAndAddMarker(storageRef);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*private void downloadImagesAndAddMarker(StorageReference storageRef) throws IOException {
        File localFile = null;
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference fileRef : listResult.getItems()) {

                    try {
                        localFile = File.createTempFile("test", "jpeg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            DataPicture dataPicture = new DataPicture();
                            //mapApi.addMarker(dataPicture, );

                            myOpenMapView.invalidate();
                        }
                    });
                }
            }
        });
    }*/

    private void downloadImagesAndAddMarker(StorageReference storageRef) throws IOException {
        File localFile = File.createTempFile("test", ".jpeg");

        storageRef.child("/images").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                for (StorageReference fileRef : listResult.getItems()) {

                    fileRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    String uri = localFile.getAbsolutePath();
                                    ExifInterface exif = null;
                                    try {
                                        exif = new ExifInterface(uri);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::"+fileRef.getName()+"::::::::::::::::::::::::::::::::::");
                                    System.out.println("//////////////////////////////"+uri);
                                    double lat = exif.getLatLong()[0];
                                    double lng = exif.getLatLong()[1];

                                    DataPicture dataPicture = new DataPicture();
                                    dataPicture.setLatitude(lat);
                                    dataPicture.setLongitude(lng);
                                    dataPicture.setFile(localFile);

                                    mapApi.addMarker(dataPicture, localFile, myOpenMapView, getResources());
                                    invalidateMapView();

                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    System.out.println("//////////////////////////////onFailure::::::::::::::::::::::::::::::");
                                    exception.printStackTrace();
                                }
                            });
                }
            }
        });

    }

    public void invalidateMapView() {
        myOpenMapView.invalidate();
    }

}

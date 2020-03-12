package com.m2dl.pimpmypoutine.Database;

import android.content.res.Resources;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.m2dl.pimpmypoutine.Map.Api.MapApi;
import com.m2dl.pimpmypoutine.Map.Bean.DataPicture;

import org.osmdroid.views.MapView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Firebase {

    private ArrayList<String> uriList;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pimp-my-poutine.appspot.com");

    public Firebase() {
        uriList = new ArrayList<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously();
    }

    public ArrayList<String> getUriList() {
        return uriList;
    }

    public void uploadImage(String path) {

        Uri filePath = Uri.fromFile(new File(path));

        StorageReference childRef = storageRef.child("images/" + UUID.randomUUID().toString());

        childRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        exception.printStackTrace();
                    }
                });
    }

    public void firebaseRequest(MapApi mapApi, MapView myOpenMapView, Resources resources) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                try {
                    getAllImages(mapApi, myOpenMapView, resources);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAllImages(MapApi mapApi, MapView myOpenMapView, Resources resources) throws IOException {

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
                            System.out.println("/////////////////"+uri+"////////////"+exif.getLatLong()+"//////////////");
                            double lat = 0;
                            double lng = 0;

                            if (exif.getLatLong() != null) {
                                lat = exif.getLatLong()[0];
                                lng = exif.getLatLong()[1];
                                DataPicture dataPicture = new DataPicture();
                                dataPicture.setLatitude(lat);
                                dataPicture.setLongitude(lng);
                                dataPicture.setFile(localFile);

                                mapApi.addMarker(dataPicture, localFile, myOpenMapView, resources);
                                myOpenMapView.invalidate();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
            }
        });
    }

}


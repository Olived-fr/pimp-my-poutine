package com.m2dl.pimpmypoutine.Database;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    public List<String> getAllImages(final String path) {

        final List<String> listUri = new ArrayList<>();

        storageRef.child("/images").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult result) {
                for (StorageReference fileRef : result.getItems()) {
                    String uri = path + fileRef.getName() + ".jpg";
                    File newFile = new File(uri);
                    try {
                        newFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uriList.add(uri);
                    System.out.println(uri);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                exception.printStackTrace();
            }
        });

        return listUri;
    }

}


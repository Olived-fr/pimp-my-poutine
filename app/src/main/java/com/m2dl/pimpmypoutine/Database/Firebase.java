package com.m2dl.pimpmypoutine.Database;

import android.app.ProgressDialog;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Firebase {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pimp-my-poutine.appspot.com");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<String> uriList;

    public Firebase() {
        uriList = new ArrayList<>();
        mAuth.signInAnonymously();
    }

    public ArrayList<String> getUriList() {
        return uriList;
    }

    public void uploadImage(String path){

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

    public List<String> getAllImages(final String path){

        final List<String> listUri = new ArrayList<>();

        storageRef.child("/images").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult result) {
                for(StorageReference fileRef : result.getItems()) {

                    Uri uri = Uri.parse(path);
                    fileRef.getFile(new File("/storage/emulated/0/"+fileRef.getName()));
                    uriList.add("/storage/emulated/0/"+fileRef.getName());
                    System.out.println("/storage/emulated/0/"+fileRef.getName());
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


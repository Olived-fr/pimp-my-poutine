package com.m2dl.pimpmypoutine.Database;

import android.app.ProgressDialog;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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


    public Firebase(){
        mAuth.signInAnonymously();
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

    public List<String> getAllImages(){

        final List<String> listUri = new ArrayList<>();

        storageRef.child("/images").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult result) {
                for(StorageReference fileRef : result.getItems()) {
                    System.out.println(fileRef);
                    listUri.add(fileRef.getPath());
                    fileRef.getMetadata();
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


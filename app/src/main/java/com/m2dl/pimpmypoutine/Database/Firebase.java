package com.m2dl.pimpmypoutine.Database;

import android.app.ProgressDialog;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;


public class Firebase {

    Uri filePath;
    ProgressDialog pd;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pimp-my-poutine.appspot.com");    //change the url according to your firebase app
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public Firebase(){
        mAuth.signInAnonymously();
    }

    public void uploadImage(String path){

        filePath = Uri.fromFile(new File(path));

        StorageReference childRef = storageRef.child("image.jpg");

        //uploading the image
        childRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
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

}


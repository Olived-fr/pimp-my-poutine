package com.m2dl.pimpmypoutine.Camera.Views;

import androidx.appcompat.app.AppCompatActivity;

import com.m2dl.pimpmypoutine.Camera.Models.PimpedPhoto;
import com.m2dl.pimpmypoutine.Editor.Views.EditorActivity;
import com.m2dl.pimpmypoutine.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class ShowPictureActivity extends AppCompatActivity {

    private Button returnButton;
    private Button validButton;
    private ImageView imageToValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Log.d("ShowPictureActivity", " lat " + String.valueOf(CameraActivity.pimpedPhoto.getLocation().getLatitude()));
        returnButton = findViewById(R.id.returnButton);
        validButton = findViewById(R.id.validButton);
        imageToValid = findViewById(R.id.imageToValid);
        Intent intent = getIntent();

        final String pimpedPhoto = intent.getStringExtra("pathPhoto");
        if (CameraActivity.pimpedPhoto != null) {
            final File imgFile = new File(pimpedPhoto);
            Log.d("AndroidCameraApi", imgFile.getAbsolutePath());
            Log.d("AndroidCameraApi", imgFile.getPath());

            if (imgFile.exists()) {

                Handler mHandler = new Handler(getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Log.d("AndroidCameraApi", myBitmap.getHeight() + " w " + myBitmap.getWidth());
                        //A utiliser si pb d'angle'
                        imageToValid.setImageBitmap(RotateBitmap(myBitmap,90));
                    }
                });
            }
        }

        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editor = new Intent(ShowPictureActivity.this, EditorActivity.class);
                editor.putExtra("pathPhoto", pimpedPhoto);

                startActivity(editor);

            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}

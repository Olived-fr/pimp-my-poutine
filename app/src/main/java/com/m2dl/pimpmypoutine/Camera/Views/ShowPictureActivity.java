package com.m2dl.pimpmypoutine.Camera.Views;

import androidx.appcompat.app.AppCompatActivity;
import com.m2dl.pimpmypoutine.Editor.Views.EditorActivity;
import com.m2dl.pimpmypoutine.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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
        returnButton = findViewById(R.id.returnButton);
        validButton = findViewById(R.id.validButton);
        imageToValid = findViewById(R.id.imageToValid);
        Intent intent = getIntent();

        final String pimpedPhoto = intent.getStringExtra("pathPhoto");

        if (pimpedPhoto != null) {
            final File imgFile = new File(pimpedPhoto);
            if (imgFile.exists()) {
                Handler mHandler = new Handler(getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        //A utiliser si pb d'angle'
                        imageToValid.setImageBitmap(myBitmap);
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
}

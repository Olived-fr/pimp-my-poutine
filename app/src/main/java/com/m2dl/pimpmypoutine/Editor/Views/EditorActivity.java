package com.m2dl.pimpmypoutine.Editor.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.m2dl.pimpmypoutine.Camera.Views.ShowPictureActivity;
import com.m2dl.pimpmypoutine.Home.MainActivity;
import com.m2dl.pimpmypoutine.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;


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

                Intent mainActivity = new Intent(EditorActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}

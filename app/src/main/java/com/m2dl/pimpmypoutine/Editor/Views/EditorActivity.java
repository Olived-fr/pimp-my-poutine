package com.m2dl.pimpmypoutine.Editor.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.m2dl.pimpmypoutine.R;

import androidx.appcompat.app.AppCompatActivity;


public class EditorActivity extends AppCompatActivity {
private EditorView viewtest;
    static String pimpedPhoto;
    private Button buttonFiltre1;

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
                buttonFiltre1.setText("oui");
                viewtest.getFiltre("lol");
            }
        });
    }
}
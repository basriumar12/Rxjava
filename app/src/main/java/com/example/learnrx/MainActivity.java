package com.example.learnrx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btntab, btnValidasiPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btntab = (Button) findViewById(R.id.btn_tab);
        btnValidasiPsw = (Button) findViewById(R.id.btn_validasi_psd);


        btntab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TapActivity.class));

            }
        });

        btnValidasiPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TapActivity.class));

            }
        });
    }
}

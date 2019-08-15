package com.example.learnrx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnrx.TestApi.GithubActivity;

public class MainActivity extends AppCompatActivity {

    Button btntab, btnValidasiPsw, btnGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btntab = (Button) findViewById(R.id.btn_tab);
        btnValidasiPsw = (Button) findViewById(R.id.btn_validasi_psd);
        btnGithub = (Button) findViewById(R.id.btn_github);


        btntab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TapActivity.class));

            }
        });

        btnValidasiPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ValidasiActivity.class));

            }
        });

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GithubActivity.class));

            }
        });
    }
}

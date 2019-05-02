package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.weatherapp.R;
import com.example.weatherapp.database.SqlLiteServices;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        imageView = findViewById(R.id.imageView);
        /* Creating tables on install of app */
        sharedpreferences = getSharedPreferences("tables", MODE_PRIVATE);
        Map m = sharedpreferences.getAll();
        if (m.size() == 0) {
            editor = sharedpreferences.edit();
            editor.putString("val", "1");
            editor.commit();
            new SqlLiteServices(SplashActivity.this).createTables();
        }
//        imageView.setImageResource(R.mipmap.ic_launcher);

        final ProgressDialog loading = ProgressDialog.show(SplashActivity.this, "", "Please wait...", false, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.cancel();
                Intent intent= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
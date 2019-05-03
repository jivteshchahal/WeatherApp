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

import static com.example.weatherapp.utils.Constants.TABLES;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences(TABLES, MODE_PRIVATE);
        Map m = sharedpreferences.getAll();
        if (m.size() == 0) {
            editor = sharedpreferences.edit();
            editor.putString(getString(R.string.spVal), getString(R.string.spValValue));
            editor.commit();
            new SqlLiteServices(SplashActivity.this).createTables();
        }

        final ProgressDialog loading = ProgressDialog.show(SplashActivity.this, getString(R.string.none), getString(R.string.pbMsg), false, false);
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
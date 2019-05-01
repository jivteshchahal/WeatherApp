package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.bean.WeatherBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button refreshButton;
    TextView txtCity, txtUpdatedTime, txtWeather, txtTemp, txtWind;
    WeatherBean myModelList;
    Gson gson;
    Type listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerCitySelect);
        refreshButton = findViewById(R.id.btnRefresh);
        txtCity = findViewById(R.id.txtcity);
        txtUpdatedTime = findViewById(R.id.txtDayAndTime);
        txtWeather = findViewById(R.id.txtWeather);
        txtTemp = findViewById(R.id.txtTemp);
        txtWind = findViewById(R.id.txtWindSpeed);

        List<String> cityList =  new ArrayList<>();
        cityList.add("Select City");
        cityList.add("Sydney");
        cityList.add("Melbourne");
        cityList.add("Wollongong");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                Log.e("VVVVVVVVVVVV",parent.getSelectedItem().toString());
                volley(parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void volley(final String place) {

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+place+"&APPID=172c74216a3ffe8dfbbdec6565a9dac7&units=metric";

        final ProgressDialog loading = ProgressDialog.show(this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                loading.cancel();
                gson = new Gson();
                listType = new TypeToken<WeatherBean>() {
                }.getType();
//                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("EEEE h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
//                Log.e("Time",date+"");
                myModelList = gson.fromJson(response, listType);
                if (myModelList.getWeather().size() != 0) {
                    for (int k = 0; k < myModelList.getWeather().size(); k++) {
                        txtCity.setText(myModelList.getName());
                        txtUpdatedTime.setText(date);
                        txtWeather.setText(myModelList.getWeather().get(k).getDescription());
                        txtTemp.setText(myModelList.getMain().get("temp")+"\u00b0 C");
                        txtWind.setText(myModelList.getWind().get("speed")+"km/h");
                    }
                }
                else{
                    Log.e("NNNNNNNNNNN", "Errorrrrrrrrrrrr");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Log.e("error in volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private boolean isInternetOn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button refreshButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerCitySelect);
        refreshButton = findViewById(R.id.btnRefresh);


        List<String> cityArray =  new ArrayList<>();
        cityArray.add("Select City");
        cityArray.add("Sydney");
        cityArray.add("Melbourne");
        cityArray.add("Wollongong");
        volley("melbourne");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                Log.e("VVVVVVVVVVVV",parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void volley(final String place) {

        String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=172c74216a3ffe8dfbbdec6565a9dac7";

        //        final ProgressDialog loading = ProgressDialog.show(this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

//                loading.cancel();
                Gson gson = new Gson();
                Type listType = new TypeToken<WeatherBean>() {
                }.getType();

                WeatherBean myModelList = gson.fromJson(response, listType);
                if (myModelList.getWeather().size() != 0) {

                    for (int k = 0; k < myModelList.getWeather().size(); k++) {
//                        places[k] = myModelList.getPredictions().get(k).getDescription();
                        Log.e("NNNNNNNNNNN", myModelList.getWeather().get(k).getMain() + "");
                    }
                }
                else{
                    Log.e("NNNNNNNNNNN", "Errorrrrrrrrrrrr");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loading.cancel();
                Log.e("error in volley", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
//                params.put("input", place);
//                params.put("to", runner_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
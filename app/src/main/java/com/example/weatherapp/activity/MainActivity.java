package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.bean.MainBean;
import com.example.weatherapp.bean.WeatherBean;
import com.example.weatherapp.database.SqlLiteServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button refreshButton;
    TextView txtCity, txtUpdatedTime, txtWeather, txtTemp, txtWind;
    Gson gson;
    String city;
    String[] cityArray;
    public WeatherBean weatherBean;

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
        city = getString(R.string.none);
        cityArray = getResources().getStringArray(R.array.cityArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city = parent.getSelectedItem().toString();

                if (!city.equalsIgnoreCase(getString(R.string.citySelect))) {
                    showData(view);
                }else if(city.equalsIgnoreCase(getString(R.string.citySelect))){
                    Snackbar snackbar = Snackbar.make(view, getString(R.string.sbSelectCity), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            private void showData(View view) {
                if (isNetworkAvailable()) {
                    volley(city);

                } else {

                    /* getting data from database */

                    List<MainBean> mainBeanList;
                    try {
                        mainBeanList = new SqlLiteServices(MainActivity.this).getAllWeatherData();
                        if(mainBeanList.size() >1) {
                            for (int index = 0; index < mainBeanList.size(); index++)
                                if (mainBeanList.get(index).getCity().equalsIgnoreCase(city)) {
                                    txtCity.setText(city);
                                    txtTemp.setText(mainBeanList.get(index).getTemp() + getString(R.string.degreeC));
                                    txtUpdatedTime.setText(mainBeanList.get(index).getTime());
                                    txtWeather.setText(mainBeanList.get(index).getWeather());
                                    txtWind.setText(mainBeanList.get(index).getSpeed() + getString(R.string.kmperHour));
                                }
                        }else{
                            Snackbar snackbar = Snackbar.make(view, getString(R.string.sbOffline), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } catch (Exception e) {
                        Log.e(getString(R.string.dbExeption), e.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    volley(city);
                } else {
                    Snackbar snackbar = Snackbar.make(v, getString(R.string.sbMsg), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    public void volley(final String place) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.urlHttp)).authority(getString(R.string.urlDomain)).appendPath(getString(R.string.urlData)).appendPath(getString(R.string.urlVer)).appendPath(getString(R.string.urlWea)).appendQueryParameter(getString(R.string.apiQuery), place).appendQueryParameter(getString(R.string.urlApistart), getString(R.string.urlApiWeatherKey)).appendQueryParameter(getString(R.string.apiUnits), getString(R.string.apiMetrics));
        String url = builder.build().toString();
        final ProgressDialog loading = ProgressDialog.show(this, getString(R.string.none), getString(R.string.pbMsg), false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.cancel();

                gson = new Gson();
                Type listType = new TypeToken<WeatherBean>() {
                }.getType();

                DateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                String date = df.format(Calendar.getInstance().getTime());

                weatherBean = gson.fromJson(response, listType);
                MainBean mainBean = new MainBean();

                try {
                    if (weatherBean.getWeather().size() != 0) {
                        for (int position = 0; position < weatherBean.getWeather().size(); position++) {

                            /* Getting data out of the array and object */
                            mainBean.setCity(city);
                            mainBean.setTime(date);
                            mainBean.setWeather(weatherBean.getWeather().get(position).getDescription());
                            mainBean.setTemp(weatherBean.getMain().get(getString(R.string.apiRefTemperature)).toString());
                            mainBean.setSpeed(weatherBean.getWind().get(getString(R.string.apiRefSpeed)).toString());

                            /* live data display */
                            txtCity.setText(city);
                            txtTemp.setText(weatherBean.getMain().get(getString(R.string.apiRefTemperature)).toString() + getString(R.string.degreeC));
                            txtUpdatedTime.setText(date);
                            txtWeather.setText(weatherBean.getWeather().get(position).getDescription());
                            txtWind.setText(weatherBean.getWind().get(getString(R.string.apiRefSpeed)).toString() + getString(R.string.kmperHour));
                        }

                        new SqlLiteServices(MainActivity.this).insertOrUpdate(mainBean);

                    } else {
                        Log.e(getString(R.string.volleyErrortag), getString(R.string.volleyErrormsg));
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Log.e(getString(R.string.volleyError), error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /* Checking the Internet Connection */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
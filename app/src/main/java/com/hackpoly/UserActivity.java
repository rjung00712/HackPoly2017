package com.hackpoly;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private static String Password;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        final TextView tvActivityUsername = (TextView) findViewById(R.id.tvUsername);
        final Button bSetting = (Button) findViewById(R.id.bSetting);
        //final Button bFilter = (Button) findViewById(R.id.bFilter);
        //final Button bPeople = (Button) findViewById(R.id.bAddPeopleToList);
        final Button bPlay = (Button) findViewById(R.id.bPlay);
        final String GpsUrl = "http://app.comli.com/findFriends.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String username = intent.getStringExtra("Username");
        final Gps locationListener = new Gps();

        Password = intent.getStringExtra("password");
        //System.out.println("Rating: " + Filter.getRating());
        if (tvActivityUsername != null) {
            tvActivityUsername.setText(username);
        }

        //When "Setting" is selected; Setting.class
        if (bSetting != null) {
            bSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingIntent = new Intent(UserActivity.this, Setting.class);
                    UserActivity.this.startActivity(settingIntent);
                }
            });
        }
//
//        //When "Filter" is selected; Filter.class
//        if (bFilter != null) {
//            bFilter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //wait for sean
//                    //   Intent filterIntent = new Intent(UserActivity.this, Filter.class);
//                    //UserActivity.this.startActivity(filterIntent);
//                }
//            });
//        }
//
//        //When "People" is selected; Gps.class
//        if (bPeople != null) {
//            bPeople.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // System.out.println("adding Gps");
//                    // System.out.println("Long: " +  locationListener.getLongitude() + "\nLat: " +  locationListener.getLatitude());
//                    StringRequest request = new StringRequest(Request.Method.POST, GpsUrl, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            System.out.println(response);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> parameters = new HashMap<>();
//                            parameters.put("Longitude", String.valueOf(locationListener.getLongitude()));
//                            parameters.put("Latitude", String.valueOf(locationListener.getLatitude()));
//                            parameters.put("Active", "1");
//                            parameters.put("Username", username);
//
//                            return parameters;
//                        }
//                    };
//                    requestQueue.add(request);
//
//                    //waiting for friends class
//                    //Intent peopleIntent = new Intent(UserActivity.this, Friends.class);
//                    // UserActivity.this.startActivity(peopleIntent);
//                }
//            });
//
//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.INTERNET}, 10);
//                    return;
//                }
//            }
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//        }

        //When "Play" is selected; Setting.class
        if (bPlay != null) {
            bPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //RICHARD CODE
//                        Intent playIntent = new Intent(UserActivity.this, Play.class);
//                        UserActivity.this.startActivity(playIntent);
                }
            });/**/
        }
    }
}

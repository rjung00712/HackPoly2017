package com.hackpoly;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.location.LocationListener;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackpoly.AmazonAws.DynamoDB;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements LocationListener {

    private static String Password;
    private static double longitude, latitude;
    private LocationManager locationManager;
    private static String username;
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET }, 10);
                return;
            }
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);


        final TextView tvActivityUsername = (TextView) findViewById(R.id.tvUsername);
        final Button bSetting = (Button) findViewById(R.id.bSetting);
        //final Button bFilter = (Button) findViewById(R.id.bFilter);
        //final Button bPeople = (Button) findViewById(R.id.bAddPeopleToList);
        final Button bPlay = (Button) findViewById(R.id.bPlay);
//        final String GpsUrl = "http://app.comli.com/findFriends.php";
//        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Intent intent = getIntent();
        //final String username = intent.getStringExtra("Username");
        username = intent.getStringExtra("Username");
        Password = intent.getStringExtra("password");


//        double tempLat = 0, tempLong = 0;
//        if(tempLat != latitude || tempLong != longitude)
//        {
//            DynamoDB db = new DynamoDB(this);
//            db.updateLocation(username, latitude, longitude);
//            tempLat = latitude;
//            tempLong = longitude;
//        }
        //System.out.println("Rating: " + Filter.getRating());
        if (tvActivityUsername != null) {
            tvActivityUsername.setText(username);
        }

        //When "Setting" is selected; Setting.class
        if (bSetting != null) {
            bSetting.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent settingIntent = new Intent(UserActivity.this, Setting.class);
                    UserActivity.this.startActivity(settingIntent);
                }
            });
        }

        Button bGPS = (Button) findViewById(R.id.bGPS);
        if(bGPS != null)
        {
            bGPS.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DynamoDB db = new DynamoDB(UserActivity.this);
                    db.updateLocation(username, latitude, longitude);
                }
        });
    }

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

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        System.out.println("LONG: "  + longitude);
        System.out.println("LAT: " + latitude);
        DynamoDB db = new DynamoDB(UserActivity.this);
        db.updateLocation(username, latitude, longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }
}

package androidlabs.gsheets2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidlabs.gsheets2.Post.PostData;


public class ServerMap extends AppCompatActivity {

    Button getLocation;
    TextView tv;
    LocationManager locationManager;
    LocationListener locationListener;
    public double lati=0.0f;
    public double longi=1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_map);
        getLocation = (Button) findViewById(R.id.getcoordinates);
        tv = (TextView) findViewById(R.id.values);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati=location.getLatitude();
                longi=location.getLongitude();
                tv.append("\n" +lati+ " " + longi);
                Intent i=new Intent(ServerMap.this,PostData.class);
                i.putExtra("latitude",lati+"");
                i.putExtra("longitude",longi+"");
                startActivity(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }

            @Override
            public void onProviderDisabled(String s) {
                //when location disabled write code here
            }
        };
        //seeking permissions from user to use location services
        //we are doing this as from Android 6.0 it is necessary
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                configureButton();
            }
        }
        onRequestPermissionsResult(10, permissions, new int[]{PackageManager.PERMISSION_GRANTED});// result will be handled by this function
        configureButton();
    }


    private void configureButton() {
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                //1000 is for every 1 sec and 0 is distance parameter in meters if specified any num like 5 then for 5 meters change in position result is printed
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
            }
        });

    }
}
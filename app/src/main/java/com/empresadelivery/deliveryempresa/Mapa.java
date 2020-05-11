package com.empresadelivery.deliveryempresa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class Mapa extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final int REQUEST_FINE_LOCATION = 0;
    String FileName = "myfile";
    SharedPreferences prefs;
    String apiKey = "MY API KEY";
    private GoogleMap mapa;
    private FusedLocationProviderClient mFusedLocationClient;
    TextView direccion;
    EditText referencia;
    Button listo, aregistro;
    //private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    //private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public Double longituduser;
    public Double latituduser;


    LocationRequest mLocationRequest;
    private final LatLng Sopdapop = new LatLng(-11.495692495131408, -77.208248);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        String longitudiso = getIntent().getStringExtra("longitud");
        String latitudiso = getIntent().getStringExtra("latitud");
        String nombreiso = getIntent().getStringExtra("nombre");
        String dire = getIntent().getStringExtra("direccion");
        float la = Float.parseFloat(latitudiso);
        float lon = Float.parseFloat(longitudiso);
         final LatLng cliente = new LatLng(la, lon);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);


        direccion = (TextView) findViewById(R.id.direc);
        listo = (Button) findViewById(R.id.listo);

        mFusedLocationClient = getFusedLocationProviderClient(this);

//        checkRunTimePermission();




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
checkPermissions();
        String longitudiso = getIntent().getStringExtra("longitud");
        String latitudiso = getIntent().getStringExtra("latitud");
        String nombreiso = getIntent().getStringExtra("nombre");
        String dire = getIntent().getStringExtra("direccion");
        direccion.setText(dire);
        float la = Float.parseFloat(latitudiso);
        float lon = Float.parseFloat(longitudiso);
        final LatLng cliente = new LatLng(la, lon);

            Toast.makeText(this, "Activa tu gps por favor", Toast.LENGTH_LONG).show();
            mapa = googleMap;
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(cliente, 15));
            //  mapa.setMyLocationEnabled(true);
            // mapa.getUiSettings().setCompassEnabled(true);

            Marker marker = mapa.addMarker(new MarkerOptions()

                    .position(cliente)
                    .title(nombreiso)
                    .snippet("aqui recibire mis pedidos :)")

                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_success))
                    .anchor(0.5f, 0.5f)
            );

           // mapa.setInfoWindowAdapter(new Marketclaselocal(LayoutInflater.from(getApplicationContext())));
            marker.showInfoWindow();


        }









    public void onLocationChanged(Location location) {


    }


    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }


    private boolean checkPermissions() {

        if ((ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
        (ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) ){


            return true;

        } else {
     ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION},REQUEST_FINE_LOCATION);
                return false;

            }

        }

    private void revaamapa() {
        Intent intent = new Intent(getApplicationContext(), Mapa.class);

        startActivity(intent);

    }


    public void guardardireccionlatitudylongitud(String direccion, String referencia, Double latitud, Double longitud) {
        SharedPreferences sharedPreferences = getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("direccion", direccion);
        editor.putString("referencia", referencia);
        editor.putString("latitud", String.valueOf(latitud));
        editor.putString("longitud", String.valueOf(longitud));
        editor.commit();

    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.referencia);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}


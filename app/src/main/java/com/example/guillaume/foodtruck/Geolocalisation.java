package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/*public class Geolocalisation extends FragmentActivity {

    private GoogleMap map;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalisation);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #map} is not null.
     */
  /*  private void setUpMap() {
        marker=map.addMarker(new MarkerOptions().title("Vous êtes ici").position(new LatLng(48.856614, 2.3522219000000177)));


    }
}*/


/**
 * Created by guillaume on 07/02/15.
 */
public class Geolocalisation extends Activity implements LocationListener {

    private GoogleMap map;
    private LocationManager locationManager;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalisation);

        map= ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        marker = map.addMarker(new MarkerOptions().title("Localisation par défaut").position(new LatLng(48.856614, 2.3522219000000177)));
    }

    @Override
    public void onResume() {
        super.onResume();

        //Obtention de la référence du service
       locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //On appelle la méthode pour se désabonner
        desabonnementGPS();
    }

    /**
     * Méthode permettant de s'abonner à la localisation par GPS.
     */
    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    /**
     * Méthode permettant de se désabonner de la localisation par GPS.
     */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(final Location location) {
     //Mise à jour des coordonnées
     /*   final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions option=new MarkerOptions().position(latLng).title("Vous êtes ici");
        map.addMarker(option);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));*/
    }

    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est désactivé on se désabonne
     /*   if("gps".equals(provider)) {
            desabonnementGPS();
        }*/
    }

    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activé on s'abonne
       /* if("gps".equals(provider)) {
            abonnementGPS();
        }*/
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {

    }
}

package com.example.guillaume.foodtruck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Client extends FragmentActivity implements LocationListener{

    private TextView deco, id;
    private Button select;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ObjectMapper objectMapper;
    private Personne personne;
    private LocationManager locationManager;
    private Double latitude, longitude;
    private Page page;
    private Position1 position;
    public ArrayList<Marker> markers;
    public Marker courant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        id = (TextView) findViewById(R.id.identifiantClient);
        deco = (TextView) findViewById(R.id.deconnectionClient);
        select=(Button) findViewById(R.id.select);
        markers=new ArrayList<Marker>();

        //TODO cache personne (recuperation)
        //recuperation des données de personne uniquement pour recuperer le mail en guise de clé BDD
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        //TODO cache page (recuperation)
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("page.json");
            page=objectMapper.readValue(in, Page.class);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        //TODO cache markers (recuperation)
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("markers.json");
            position=objectMapper.readValue(in, Position1.class);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }
        setUpMapIfNeeded();

        //TODO modification a faire: recuperation du pseudo du client sur le serveur
        id.setText(personne.getPseudo());

        //redirection vers la page MainActivity
        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Client.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //selection des foodTrucks
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder boite;
                LinearLayout layout=new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final SeekBar barre=new SeekBar(v.getContext());
                final TextView valeur=new TextView(v.getContext());
                final TextView titre=new TextView(v.getContext());
                boite=new AlertDialog.Builder(v.getContext());
                titre.setText(R.string.distance);
                barre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        valeur.setText(progress + " Km");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                barre.setMax(100);
                barre.setProgress(50);
                valeur.setGravity(Gravity.CENTER);
                layout.addView(titre);
                layout.addView(barre);
                layout.addView(valeur);
                boite.setView(layout);
                boite.setTitle(R.string.food);
                boite.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMap.clear();
                        CircleOptions circle=new CircleOptions().center(new LatLng(latitude, longitude)).radius(barre.getProgress()*1000).strokeColor(Color.RED).strokeWidth(5);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Vous êtes ici"));
                        mMap.addCircle(circle);
                        int earth=6378137;
                        for(int i=0; i<markers.size(); i++){
                            double distLat=((Math.toRadians(markers.get(i).getPosition().latitude)-Math.toRadians(latitude))/2);
                            double distLon=((Math.toRadians(markers.get(i).getPosition().longitude)-Math.toRadians(longitude))/2);
                            double dist=(Math.sin(distLat)*Math.sin(distLat))+Math.cos(Math.toRadians(latitude))*Math.cos(Math.toRadians(markers.get(i).getPosition().latitude))*(Math.sin(distLon)*Math.sin(distLon));
                            double result=(2*Math.atan2(Math.sqrt(dist), Math.sqrt(1-dist)))*earth;
                            if(result<=circle.getRadius()){
                                markers.get(i).setVisible(true);
                            }else{
                                markers.get(i).setVisible(false);
                            }
                            mMap.addMarker(new MarkerOptions().title(markers.get(i).getTitle()).position(new LatLng(markers.get(i).getPosition().latitude, markers.get(i).getPosition().longitude)).visible(markers.get(i).isVisible()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            double ratioDist=(circle.getRadius()*100)/earth;
                            double bidouillage=(circle.getCenter().latitude*ratioDist)/100;
                            double ecartLat=0.02*(circle.getRadius()/10000);
                            double ecartLon=0.06*(circle.getRadius()/10000);
                            double newLatP=circle.getCenter().latitude+ecartLat+bidouillage;
                            double newLatN=circle.getCenter().latitude-ecartLat-bidouillage;
                            double newLonP=circle.getCenter().longitude+bidouillage+ecartLon;
                            double newLonN=circle.getCenter().longitude-bidouillage-ecartLon;
                            LatLngBounds.Builder builder=new LatLngBounds.Builder();
                            LatLng latLng=new LatLng(newLatP, newLonP);
                            LatLng latLng1=new LatLng(newLatP, newLonN);
                            LatLng latLng2=new LatLng(newLatN, newLonP);
                            LatLng latLng3=new LatLng(newLatN, newLonN);
                            builder.include(latLng);
                            builder.include(latLng1);
                            builder.include(latLng2);
                            builder.include(latLng3);
                            LatLngBounds bounds=builder.build();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
                        }
                    }
                });
                boite.show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                boolean bool=false;
                if(marker.getTitle().equals("Vous êtes ici")){
                    bool=false;
                }else {
                    marker.showInfoWindow();
                    bool=true;
                }
                return false;
            }
        });
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager=(LocationManager)this.getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            abonnementGps();
        }
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        desabonnementGps();
    }

    public void abonnementGps(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    public void desabonnementGps(){
        locationManager.removeUpdates(this);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapCli)).getMap();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48, 2), 5));
            //TODO modifiacation a faire: recuperation de tous les markers de tous les vendeurs sur le serveur
            for(String valeur:position.keySet()){
                MarkerOptions options=new MarkerOptions().title(valeur).position(new LatLng(position.getPosition(valeur).get(0), position.getPosition(valeur).get(1))).visible(false).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                Marker m=mMap.addMarker(options);
                markers.add(m);
            }
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        if(courant==null) {
            final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            courant = mMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {

    }

    @Override
    public void onProviderEnabled(final String provider) {
        if("gps".equals(provider)){
            abonnementGps();
        }
    }

    @Override
    public void onProviderDisabled(final String provider) {
        if("gps".equals(provider)){
            desabonnementGps();
        }
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        private View view;

        public CustomInfoWindowAdapter(){
            view=getLayoutInflater().inflate(R.layout.info_bulle, null);
            view.setBackgroundColor(Color.WHITE);
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            //TODO modification a faire:recuperation de tous les informations de la page du vendeur, /!\marchera uniqument dans le cas d'un vendeur
            final TextView titre = (TextView) view.findViewById(R.id.titreInfo);
            titre.setText(page.getTitre());
            final ImageView image = (ImageView) view.findViewById(R.id.imageInfo);
            image.setImageBitmap(BitmapFactory.decodeByteArray(page.getLogo(), 0, page.getLogo().length));
            final TextView desc = (TextView) view.findViewById(R.id.desInfo);
            desc.setText(page.getDescription());
            final TextView menu = (TextView) view.findViewById(R.id.menuInfo);
            menu.setText(page.getMenu());
            final TextView promo = (TextView) view.findViewById(R.id.promoInfo);
            promo.setText(page.getPromo());
            final TextView dispo = (TextView) view.findViewById(R.id.dispoInfo);
            String[] phrase=marker.getTitle().split(" ");
            dispo.setText("Présent le " + phrase[0]+" à "+phrase[1]+"h");
            final TextView num = (TextView) view.findViewById(R.id.numInfo);
            num.setText("Numéro: "+page.getTelephone());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            if(Client.this.courant!=null){
                Client.this.courant.showInfoWindow();
            }
            return null;
        }
    }
}
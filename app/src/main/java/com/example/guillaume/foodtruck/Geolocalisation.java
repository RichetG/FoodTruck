package com.example.guillaume.foodtruck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Geolocalisation extends FragmentActivity implements GoogleMap.OnMarkerDragListener{

    private double currentLatitude, currentLongitude;
    private String titre;
    private Map<String, List<Double>> markers;
    private Button envoie, ajouter, supprimer, modifier;
    private MarkerOptions options;
    private int nb=1;
    private ObjectMapper objectMapper;
    private Position1 position;
    private Position2 position2;
    private Personne personne;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalisation);
        markers=new HashMap<String, List<Double>>();
        envoie=(Button) findViewById(R.id.envoie);
        ajouter=(Button) findViewById(R.id.ajouter);
        supprimer=(Button) findViewById(R.id.supprimer);
        modifier=(Button) findViewById(R.id.modifier);

        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

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

        mMap.setOnMarkerDragListener(this);

        envoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //envoi la localisation au client

                for(String valeur: markers.keySet()){
                    String coord=markers.get(valeur).get(0)+" "+markers.get(valeur).get(1);
                    Toast.makeText(Geolocalisation.this, coord, Toast.LENGTH_SHORT).show();
                }
                objectMapper=new ObjectMapper();
                position=new Position1(personne.getMail());
                for(String valeur:markers.keySet()) {
                    position2 = new Position2(valeur, markers.get(valeur).get(0), markers.get(valeur).get(1));
                    position.addPosition(position2);
                }
                try {
                    FileOutputStream out=openFileOutput("markers.json", MODE_PRIVATE);
                    objectMapper.writeValue(out, position);
                }catch (JsonGenerationException f){
                    f.printStackTrace();
                }catch (IOException f){
                    f.printStackTrace();
                }
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder boite;
                LinearLayout layout=new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText latitude=new EditText(v.getContext());
                final EditText longitude=new EditText(v.getContext());
                final EditText titre=new EditText(v.getContext());
                boite=new AlertDialog.Builder(v.getContext());
                latitude.setText(R.string.latitude);
                latitude.setTextColor(Color.GRAY);
                latitude.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        latitude.setText("");
                        latitude.setTextColor(Color.WHITE);
                    }
                });
                longitude.setText(R.string.longitude);
                longitude.setTextColor(Color.GRAY);
                longitude.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longitude.setText("");
                        longitude.setTextColor(Color.WHITE);
                    }
                });
                titre.setText(R.string.titre);
                titre.setTextColor(Color.GRAY);
                titre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        titre.setText("");
                        titre.setTextColor(Color.WHITE);
                    }
                });
                layout.addView(latitude);
                layout.addView(longitude);
                layout.addView(titre);
                boite.setView(layout);
                boite.setTitle(R.string.ajouter);
                boite.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!(latitude.getText().toString().matches("[0-9]+.[0-9]+") || latitude.getText().toString().matches("[0-9]+") || longitude.getText().toString().matches("[0-9]+.[0-9]+") || longitude.getText().toString().matches("[0-9]+")) ){
                            Toast.makeText(Geolocalisation.this, R.string.LatIncorrect, Toast.LENGTH_SHORT).show();
                        }else {
                            LatLng latLng = new LatLng(Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longitude.getText().toString()));
                            if(titre.getText().toString().equals("Titre")){
                                if(determinationCpt()==true){
                                    determinationCpt();
                                }
                                options = new MarkerOptions().title("Localisation "+nb).draggable(true).position(latLng);
                                nb=1;
                            }else {
                                options = new MarkerOptions().title(titre.getText().toString()).draggable(true).position(latLng);
                            }
                            mMap.addMarker(options);
                            List<Double> liste = new ArrayList<Double>();
                            liste.add(latLng.latitude);
                            liste.add(latLng.longitude);
                            markers.put(options.getTitle(), liste);
                            centrer();
                        }
                    }
                });
                boite.setNeutralButton(R.string.defaut, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LatLng latLng = new LatLng(48.856614, 2.3522219000000177);
                        if(determinationCpt()==true){
                            determinationCpt();
                        }
                        options = new MarkerOptions().title("Localisation "+nb).draggable(true).position(latLng);
                        mMap.addMarker(options);
                        List<Double> liste = new ArrayList<Double>();
                        liste.add(latLng.latitude);
                        liste.add(latLng.longitude);
                        markers.put(options.getTitle(), liste);
                        nb=1;
                        centrer();
                    }
                });
                boite.show();
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                AlertDialog.Builder boite;
                final EditText texte=new EditText(v.getContext());
                boite=new AlertDialog.Builder(v.getContext());
                texte.setText(R.string.selection);
                texte.setTextColor(Color.GRAY);
                texte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        texte.setText("");
                        texte.setTextColor(Color.WHITE);
                    }
                });
                boite.setView(texte);
                boite.setTitle(R.string.supp);
                boite.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int start=markers.size();
                        markers.remove(texte.getText().toString());
                        mMap.clear();
                        for(String valeur:markers.keySet()){
                            options=new MarkerOptions().draggable(true).title(valeur).position(new LatLng(markers.get(valeur).get(0), markers.get(valeur).get(1)));
                            mMap.addMarker(options);
                        }
                        if(start==markers.size()){
                            Toast.makeText(Geolocalisation.this, R.string.markerIncorrect, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                boite.show();
            }
        });

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder boite;
                LinearLayout layout=new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText ancien=new EditText(v.getContext());
                final EditText nouveau=new EditText(v.getContext());
                boite=new AlertDialog.Builder(v.getContext());
                ancien.setText(R.string.ancien);
                ancien.setTextColor(Color.GRAY);
                ancien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ancien.setText("");
                        ancien.setTextColor(Color.WHITE);
                    }
                });
                nouveau.setText(R.string.nouveauM);
                nouveau.setTextColor(Color.GRAY);
                nouveau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nouveau.setText("");
                        nouveau.setTextColor(Color.WHITE);
                    }
                });
                layout.addView(ancien);
                layout.addView(nouveau);
                boite.setView(layout);
                boite.setTitle(R.string.modi);
                boite.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(markers.get(nouveau.getText().toString())!=null){
                            Toast.makeText(Geolocalisation.this, R.string.identique, Toast.LENGTH_SHORT).show();
                        }else if (markers.get(ancien.getText().toString()) != null) {
                            currentLatitude = markers.get(ancien.getText().toString()).get(0);
                            currentLongitude = markers.get(ancien.getText().toString()).get(1);
                            options = new MarkerOptions().title(nouveau.getText().toString()).position(new LatLng(currentLatitude, currentLongitude)).draggable(true);
                            markers.remove(ancien.getText().toString());
                            List<Double> liste = new ArrayList<Double>();
                            liste.add(currentLatitude);
                            liste.add(currentLongitude);
                            markers.put(nouveau.getText().toString(), liste);
                            mMap.clear();
                            for (String valeur : markers.keySet()) {
                                options = new MarkerOptions().draggable(true).title(valeur).position(new LatLng(markers.get(valeur).get(0), markers.get(valeur).get(1)));
                                mMap.addMarker(options);
                            }
                            Toast.makeText(Geolocalisation.this, R.string.changement, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Geolocalisation.this, R.string.markerIncorrect, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                boite.show();
            }
        });
    }

    private void centrer(){
        LatLngBounds.Builder builder=new LatLngBounds.Builder();
        for(String valeur: markers.keySet()){
            LatLng latLng=new LatLng(markers.get(valeur).get(0), markers.get(valeur).get(1));
            builder.include(latLng);
        }
        LatLngBounds bounds=builder.build();
        CameraUpdate cu=CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.animateCamera(cu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected  void onPause() {
        super.onPause();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48, 2), 5));
            if(position!=null) {
                for (String valeur : position.keySet()) {
                    markers.put(valeur, position.getPosition(valeur));
                    LatLng latLng = new LatLng(position.getPosition(valeur).get(0), position.getPosition(valeur).get(1));
                    options = new MarkerOptions().title(valeur).draggable(true).position(latLng);
                    mMap.addMarker(options);
                }
            }
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        currentLatitude=marker.getPosition().latitude;
        currentLongitude=marker.getPosition().longitude;
        List<Double>liste=new ArrayList<Double>();
        liste.add(currentLatitude);
        liste.add(currentLongitude);
        markers.remove(marker.getTitle());
        markers.put(marker.getTitle(), liste);
    }

    private boolean determinationCpt(){
        boolean test=false;
        for(String valeur:markers.keySet()){
            String phrase[]=valeur.split(" ");
            if(phrase.length==2 && nb==Integer.parseInt(phrase[1])){
                nb++;
                test=true;
            }
        }
        return test;
    }
}
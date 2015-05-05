package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by guillaume on 07/02/15.
 */
public class Vendeur extends Activity{

    private TextView deconnection, login;
    private Button geolocalisation, modifPage;
    private Personne personne;
    private ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendeur);

        deconnection = (TextView) findViewById(R.id.deconnection);
        login = (TextView) findViewById(R.id.identifiantVendeur);
        geolocalisation = (Button) findViewById(R.id.geolocalisation);
        modifPage = (Button) findViewById(R.id.modifPage);

        //TODO cache personne (recuperation)
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);

        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        login.setText(personne.getPseudo());//TODO modification a faire: recuperer pseudo du vendeur

        deconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fermer la connexion
                Intent intent = new Intent(Vendeur.this, MainActivity.class);
                startActivity(intent);
            }
        });

        geolocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vendeur.this, Geolocalisation.class);
                startActivity(intent);
            }
        });

        modifPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vendeur.this, ModifPage.class);
                startActivity(intent);
            }
        });
    }
}

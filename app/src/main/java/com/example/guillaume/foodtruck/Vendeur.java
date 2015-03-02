package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by guillaume on 07/02/15.
 */
public class Vendeur extends Activity{

    private TextView deconnection, login;
    private Button geolocalisation, modifPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendeur);

        deconnection = (TextView) findViewById(R.id.deconnection);
        login = (TextView) findViewById(R.id.login);
        geolocalisation = (Button) findViewById(R.id.geolocalisation);
        modifPage = (Button) findViewById(R.id.modifPage);

        deconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                //dermer la connexion
                Intent intent = new Intent(Vendeur.this, MainActivity.class);
                startActivity(intent);
            }
        });

        geolocalisation.setTextColor(Color.WHITE);
        geolocalisation.setBackgroundColor(Color.GREEN);

        modifPage.setBackgroundColor(Color.GREEN);
        modifPage.setTextColor(Color.WHITE);

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

        //TODO
        //mettre nom identifiant
        login.setText("Identifiant");
    }
}

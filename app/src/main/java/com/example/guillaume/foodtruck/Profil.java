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
public class Profil extends Activity {

    private Button client, vendeur;
    private TextView retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        client = (Button) findViewById(R.id.client);
        vendeur = (Button) findViewById(R.id.vendeur);
        retour = (TextView) findViewById(R.id.retour);

        client.setTextColor(Color.WHITE);
        client.setBackgroundColor(Color.GREEN);

        vendeur.setTextColor(Color.WHITE);
        vendeur.setBackgroundColor(Color.GREEN);

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Client.class);
                startActivity(intent);
            }
        });

        vendeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Vendeur.class);
                startActivity(intent);
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Compte.class);
                startActivity(intent);
            }
        });
    }

}

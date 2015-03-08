package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by guillaume on 07/02/15.
 */
public class Profil extends Activity {

    private Button client, vendeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        client = (Button) findViewById(R.id.client);
        vendeur = (Button) findViewById(R.id.vendeur);

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Client.class);
                startActivity(intent);
                //TODO
                //ajouter BDD qu'il est client
            }
        });

        vendeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Vendeur.class);
                startActivity(intent);
                //TODO
                //ajouter BDD qu'il est vendeur
            }
        });
    }

}

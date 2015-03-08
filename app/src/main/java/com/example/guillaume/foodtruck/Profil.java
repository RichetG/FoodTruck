package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                //sauvegarde typeCompte
                try {
                    FileOutputStream outId = openFileOutput("compte.txt", Context.MODE_WORLD_READABLE);
                    outId.write("client".getBytes());
                    outId.close();
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
        });

        vendeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Vendeur.class);
                startActivity(intent);
                //sauvegarde typeCompte
                try {
                    FileOutputStream outId = openFileOutput("compte.txt", Context.MODE_WORLD_READABLE);
                    outId.write("vendeur".getBytes());
                    outId.close();
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
        });
    }

}

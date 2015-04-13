package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guillaume on 07/02/15.
 */
public class Profil extends Activity {

    private Button client, vendeur;
    private Personne personne;
    private ObjectMapper objectMapper;

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
                //stockage des donné d'une personne de type client
                personne=new Personne(Compte.identifiant.getText().toString(), Compte.mail.getText().toString(), Compte.mdp.getText().toString(), 1);
                objectMapper=new ObjectMapper();
                try {
                    FileOutputStream out=openFileOutput("personne.json", Context.MODE_PRIVATE);
                    objectMapper.writeValue(out, personne);
                }catch (JsonGenerationException f){
                    f.printStackTrace();
                }catch (IOException f){
                    f.printStackTrace();
                }
            }
        });

        vendeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, Vendeur.class);
                startActivity(intent);
                //stockage des donnée d'une personne de type vendeur
                personne=new Personne(Compte.identifiant.getText().toString(), Compte.mail.getText().toString(), Compte.mdp.getText().toString(), 0);
                objectMapper=new ObjectMapper();
                try {
                    FileOutputStream out=openFileOutput("personne.json", Context.MODE_PRIVATE);
                    objectMapper.writeValue(out, personne);
                }catch (JsonGenerationException f){
                    f.printStackTrace();
                }catch (IOException f){
                    f.printStackTrace();
                }
            }
        });
    }

}

package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guillaume on 07/02/15.
 */
public class Profil extends Activity {

    private ImageView client, vendeur;
    private Personne personne;
    private ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        client = (ImageView) findViewById(R.id.client);
        vendeur = (ImageView) findViewById(R.id.vendeur);

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO envoi les informations personne au serveur FAIT
               try {
                    MainActivity.socket.emit("newUser", new JSONObject().put("type", 1).put("mdp", Compte.mdp).put("mail", Compte.mail).put("pseudo", Compte.identifiant));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Profil.this, Client.class);
                startActivity(intent);
                //TODO cache personne (sauvegarde)
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
                //TODO envoi des information personne au serveur FAIT
               try {
                    MainActivity.socket.emit("newUser", new JSONObject().put("type", 0).put("mdp", Compte.mdp).put("mail", Compte.mail).put("pseudo", Compte.identifiant));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Profil.this, Vendeur.class);
                startActivity(intent);
                //TODO cache personne (sauvegarde)
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

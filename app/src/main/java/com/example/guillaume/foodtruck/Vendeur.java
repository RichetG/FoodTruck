package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        login = (TextView) findViewById(R.id.identifiantVendeur);
        geolocalisation = (Button) findViewById(R.id.geolocalisation);
        modifPage = (Button) findViewById(R.id.modifPage);

        //recuperation identifiant
        try{
            FileInputStream in=openFileInput("identite.txt");
            int c;
            String temp="";
            while( (c = in.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            login.setText(temp);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

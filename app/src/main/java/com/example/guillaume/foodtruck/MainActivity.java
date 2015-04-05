package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    private EditText email, mdp;
    private TextView creerCompte, oublier;
    private Button connexion;
    private CheckBox auto;
    private boolean coche=false;
    private ObjectMapper objectMapper;
    private Personne personne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        email = (EditText) findViewById(R.id.identifiant);
        mdp = (EditText) findViewById(R.id.motDePasse);
        creerCompte = (TextView) findViewById(R.id.buttonCreer);
        connexion = (Button) findViewById(R.id.buttonConnexion);
        auto = (CheckBox) findViewById(R.id.auto);
        oublier = (TextView) findViewById(R.id.oublier);

        //recuperation coche
        try {
            FileInputStream in=MainActivity.this.openFileInput("coche.txt");
            int c;
            String temp="";
            while( (c = in.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            coche= Boolean.parseBoolean(temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //recuperation donnée
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
            Log.d("re", objectMapper.writeValueAsString(personne));
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        if(coche==true) {
            auto.setChecked(true);
            email.setText(personne.getMail());
            email.setTextColor(Color.BLACK);
            mdp.setText(personne.getMdp());
            mdp.setTextColor(Color.BLACK);
            mdp.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        //action sur EditText de l'identifiant
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("");
                email.setTextColor(Color.BLACK);
            }
        });

        //action sue EditText du mot de passe
        mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdp.setText("");
                mdp.setTextColor(Color.BLACK);
                mdp.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        //action pour changer de vue lors de la creation d'un compte
        creerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Compte.class);
                startActivity(intent);
            }
        });

        //action pour la connexion
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m=mdp.getText().toString();
                String e=email.getText().toString();
                //Message d'erreur si manque une variable de connexion
                if(m.equals("Saisir mot de passe") || e.equals("Saisir adresse email") || m.equals("") || e.equals("")){
                    Toast.makeText(MainActivity.this, R.string.erreurVide, Toast.LENGTH_SHORT).show();
                    //Message d'erreur si ne correspond pas a ine adresse email
                }else if(!(e.matches("(.*)@(.*).(.*)"))){
                    Toast.makeText(MainActivity.this, R.string.erreurMail, Toast.LENGTH_SHORT).show();
                    email.setText("");
                    //Message d'erreur si ne correspond pas au base d'un mot de passe
                }else if(!(m.length()>6 && (m.matches(".*[0-9]+[A-Z]+.*") || m.matches(".*[A-Z]+[0-9]+.*") || m.matches(".*[A-Z]+.*[0-9]+.*") || m.matches(".*[0-9]+.*[A-Z]+.*")))) {
                    Toast.makeText(MainActivity.this, R.string.erreurValMdp, Toast.LENGTH_SHORT).show();
                    mdp.setText("");
                }else if(personne==null){
                        Toast.makeText(MainActivity.this, R.string.erreurInconnu, Toast.LENGTH_SHORT).show();
                }else if(!(m.equals(personne.getMdp()) && e.equals(personne.getMail()))) {
                        Toast.makeText(MainActivity.this, R.string.erreurInconnu, Toast.LENGTH_SHORT).show();
                }else if(personne.getType()==1) {
                        Intent intentClient = new Intent(MainActivity.this, Client.class);
                        startActivity(intentClient);
                }else if(personne.getType()==0){
                        Intent intentClient = new Intent(MainActivity.this, Vendeur.class);
                        startActivity(intentClient);
                }
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(auto.isChecked()) {
                    coche=true;
                    //sauvegarde coche
                    try {
                        FileOutputStream out = MainActivity.this.openFileOutput("coche.txt", Context.MODE_PRIVATE);
                        out.write(String.valueOf(coche).getBytes());
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    coche=false;
                    //sauvegarde coche
                    try {
                        FileOutputStream out = MainActivity.this.openFileOutput("coche.txt", Context.MODE_PRIVATE);
                        out.write(String.valueOf(coche).getBytes());
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        oublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MdpOublier.class);
                startActivity(intent);
            }
        });
    }
}

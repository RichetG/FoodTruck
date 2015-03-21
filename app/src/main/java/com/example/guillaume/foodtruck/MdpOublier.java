package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guillaume on 07/02/15.
 */
public class MdpOublier extends Activity{

    private EditText mail, nouveauMdp, verifNouveauMdp;
    private Button valider;
    private ObjectMapper objectMapper;
    private Personne personne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdp_oublier);

        mail = (EditText) findViewById(R.id.mailOublier);
        nouveauMdp = (EditText) findViewById(R.id.nouveauMdp);
        verifNouveauMdp = (EditText) findViewById(R.id.verifNouveauMdp);
        valider = (Button) findViewById(R.id.validerNouveauMdp);
        //recuperation donnée
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail.setTextColor(Color.BLACK);
                mail.setText("");
            }
        });

        nouveauMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nouveauMdp.setText("");
                nouveauMdp.setTextColor(Color.BLACK);
                nouveauMdp.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        verifNouveauMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifNouveauMdp.setText("");
                verifNouveauMdp.setTextColor(Color.BLACK);
                verifNouveauMdp.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m=nouveauMdp.getText().toString();
                String e=mail.getText().toString();
                String v=verifNouveauMdp.getText().toString();
                //Message d'erreur si manque une variable de connexion
                if(m.equals("Saisir nouveau mot de passe") || e.equals("Saisir adresse email") || v.equals("Resaisir nouveau mot de passe")|| m.equals("") || e.equals("") || v.equals("")){
                    Toast.makeText(MdpOublier.this, R.string.erreurVide3, Toast.LENGTH_SHORT).show();
                    //Message d'erreur si ne correspond pas a une adresse email
                }else if(!(e.matches("(.*)@(.*).(.*)"))){
                    Toast.makeText(MdpOublier.this, R.string.erreurMail, Toast.LENGTH_SHORT).show();
                    mail.setText("");
                    //Message d'erreur si ne correspond pas au base d'un mot de passe
                }else if(!(v.equals(m))){
                    Toast.makeText(MdpOublier.this, R.string.erreurMdp, Toast.LENGTH_SHORT).show();
                    nouveauMdp.setText("");
                    verifNouveauMdp.setText("");
                }else if(!(m.length()>6 && (m.matches(".*[0-9]+[A-Z]+.*") || m.matches(".*[A-Z]+[0-9]+.*") || m.matches(".*[A-Z]+.*[0-9]+.*") || m.matches(".*[0-9]+.*[A-Z]+.*")))){
                    Toast.makeText(MdpOublier.this, R.string.erreurValMdp, Toast.LENGTH_SHORT).show();
                    nouveauMdp.setText("");
                    verifNouveauMdp.setText("");
                }else{
                    if(e.equals(personne.getMail())) {
                        //stockage des donnée d'une personne
                        personne=new Personne(personne.getPseudo(), personne.getMail(), nouveauMdp.getText().toString(), personne.getType());
                        objectMapper=new ObjectMapper();
                        try {
                            FileOutputStream out=openFileOutput("personne.json", Context.MODE_PRIVATE);
                            objectMapper.writeValue(out, personne);
                        }catch (JsonGenerationException f){
                            f.printStackTrace();
                        }catch (IOException f){
                            f.printStackTrace();
                        }

                        Toast.makeText(MdpOublier.this, R.string.validerMdp, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MdpOublier.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MdpOublier.this, R.string.erreurMailFaux, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by guillaume on 06/02/15.
 */
public class Compte extends Activity{

    private EditText  verif;
    public static EditText identifiant, mail, mdp;
    private Button valider;
    private Personne personne;
    private ObjectMapper objectMapper;
    private ImageView load;
    public static int identifiantExist=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compte);

        identifiant = (EditText) findViewById(R.id.identifiantCompte);
        mail = (EditText) findViewById(R.id.mailCompte);
        mdp = (EditText) findViewById(R.id.motDePasseCompte);
        verif = (EditText) findViewById(R.id.verificationCompte);
        valider = (Button) findViewById(R.id.buttonValiderCompte);
        /*load=(ImageView) findViewById(R.id.load);
        load.startAnimation(AnimationUtils.loadAnimation(Compte.this, R.anim.rotate));
        load.setVisibility(View.INVISIBLE);*/

        //action identifiant
        identifiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifiant.setTextColor(Color.BLACK);
                identifiant.setText("");
            }
        });

        //action mail
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail.setText("");
                mail.setTextColor(Color.BLACK);
            }
        });

        //action mdp
        mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdp.setText("");
                mdp.setTextColor(Color.BLACK);
                mdp.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        //action verif
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verif.setText("");
                verif.setTextColor(Color.BLACK);
                verif.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        //action valider
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v=verif.getText().toString();
                String m=mdp.getText().toString();
                String i=identifiant.getText().toString();
                String e=mail.getText().toString();
                //Message d'erreur si manque une variable d'inscription
                if(v.equals("") || m.equals("") || i.equals("") || e.equals("") || v.equals("Resaisir mot de passe") || m.equals("Saisir mot de passe") || i.equals("Saisir idantifiant") || e.equals("Saisir adresse email")){
                    Toast.makeText(Compte.this, R.string.erreurVide2, Toast.LENGTH_SHORT).show();
                    //Mesage d'erreur si identifiant inferieue a 4 caracteres
                }else if(identifiant.length()<4){
                    Toast.makeText(Compte.this, R.string.erreurIdentif, Toast.LENGTH_SHORT).show();
                    identifiant.setText("");
                    //Message d'erreur si email ne comporte pas @ et .
                }else if(!(e.matches("(.*)@(.*).(.*)"))){
                    Toast.makeText(Compte.this, R.string.erreurMail, Toast.LENGTH_SHORT).show();
                    mail.setText("");
                    //Message d'erreur si le mdp et verif sont different
                }else if(!(v.equals(m))){
                    Toast.makeText(Compte.this, R.string.erreurMdp, Toast.LENGTH_SHORT).show();
                    mdp.setText("");
                    verif.setText("");
                    //Message d'erreur si mdp inferieur a 7, ne comprend pas une majuscule et un chiffre
                }else if (!(m.length() > 6 && (m.matches(".*[0-9]+[A-Z]+.*") || m.matches(".*[A-Z]+[0-9]+.*") || m.matches(".*[A-Z]+.*[0-9]+.*") || m.matches(".*[0-9]+.*[A-Z]+.*")))) {
                    Toast.makeText(Compte.this, R.string.erreurValMdp, Toast.LENGTH_SHORT).show();
                    mdp.setText("");
                    verif.setText("");
                    //sinon redirection sur la page Profil
                }
                //TODO recherche du l'existance du login sur le serveur   FAIT
                MainActivity.socket.emit("listPersonnes");
                if(identifiantExist==0){
                    Toast.makeText(Compte.this, R.string.erreurIdentifiant, Toast.LENGTH_SHORT).show();
                }else{
                    //redirection vers la page profil
                    Intent intent = new Intent(Compte.this, Profil.class);
                    startActivity(intent);
                }
            }
        });
    }
}
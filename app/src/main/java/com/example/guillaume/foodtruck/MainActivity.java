package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.net.ssl.SSLContext;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class MainActivity extends Activity implements IOCallback{

    private EditText email, mdp;
    private TextView creerCompte, oublier;
    private Button connexion;
    private CheckBox auto;
    private boolean coche=false;
    private ObjectMapper objectMapper;
    private Personne personne;
    public static SocketIO socket;

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

        //connexion au serveur
        try {
            connection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //recuperation de la valeur de coche //TODO pas touché
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

        //TODO cache personne (recuperation) //recuperation des données de Personne (pseudo, mail, mdp, type)
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        //si checkbox coche alors on recupere les identifiants d'authentification
        if(coche==true) {
            auto.setChecked(true);
            email.setText(personne.getMail()); //TODO modification a faire: recupere mail utilisateur sur le serveur /!\ se demerder pour trouver quel client/vendeur est utilisateur
            email.setTextColor(Color.BLACK);
            mdp.setText(personne.getMdp());//TODO modifiacation a faire: recupere mdp utilisateur sue le serveur
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
                    //Message d'erreur si ne correspond pas a une adresse email
                }else if(!(e.matches("(.*)@(.*).(.*)"))){
                    Toast.makeText(MainActivity.this, R.string.erreurMail, Toast.LENGTH_SHORT).show();
                    email.setText("");
                    //Message d'erreur si ne correspond pas au base d'un mot de passe
                }else if(!(m.length()>6 && (m.matches(".*[0-9]+[A-Z]+.*") || m.matches(".*[A-Z]+[0-9]+.*") || m.matches(".*[A-Z]+.*[0-9]+.*") || m.matches(".*[0-9]+.*[A-Z]+.*")))) {
                    Toast.makeText(MainActivity.this, R.string.erreurValMdp, Toast.LENGTH_SHORT).show();
                    mdp.setText("");
                    //Message d'erreur si tentative de connection alors que c'est la premiere fois d'utilisation
                    //TODO modifiacation a faire: tous ce qui est en rapport avec l'oblet "personne" faire en sorte de le trouver sur le serveur /!\se demerder pour savoir quel client/vendeur est l'utilisateur
                    //TODO je me demande s'il faudra pas garder le cache de reception pour avoir une empreinte car sa va etre difficile de trouver qui est qui
                }else if(personne==null){//TODO celui ci est utile pour le cache "personne" reception, une fois retirer inutile
                        Toast.makeText(MainActivity.this, R.string.erreurInconnu, Toast.LENGTH_SHORT).show();
                    //Message d'erreur si le mail et le mdp ne correspondent pas
                }else if(!(m.equals(personne.getMdp()) && e.equals(personne.getMail()))) {//TODO modifiacation a faire: faire appel au serveur pour savoir si le couple (mdp, mail) de l'utilisateur est bien correct
                        Toast.makeText(MainActivity.this, R.string.erreurInconnu, Toast.LENGTH_SHORT).show();
                    //si le type vaut 1 alors redirection sur la page client
                }else if(personne.getType()==1) {//TODO modification a faire: appel au serveur pour connaitre son type
                        Intent intentClient = new Intent(MainActivity.this, Client.class);
                        startActivity(intentClient);
                }else if(personne.getType()==0){//TODO modification a faire: appel au serveur pour connaitre son type
                    //si le type vaut 0 alors redirection sur la page vendeur
                        Intent intentClient = new Intent(MainActivity.this, Vendeur.class);
                        startActivity(intentClient);
                }
            }
        });

        //action lors de la selection/deselection de la checkbox
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(auto.isChecked()) {
                    coche=true;
                    //sauvegarde valeur coche //TODO pas touche
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
                    //sauvegarde valeur coche //TODO pas touche
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

        //redirection sur la page MdpOublier
        oublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MdpOublier.class);
                startActivity(intent);
            }
        });
    }

    //connection vers le serveur
    public void connection() throws Exception{
        SocketIO.setDefaultSSLSocketFactory(SSLContext.getDefault());
        socket=new SocketIO();
        socket.connect("http://foodtruck-jorvek.rhcloud.com:8000/", this);
    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onMessage(String s, IOAcknowledge ioAcknowledge) {

    }

    @Override
    public void onMessage(JSONObject jsonObject, IOAcknowledge ioAcknowledge) {

    }

    @Override
    public void on(String event, IOAcknowledge ioAcknowledge, Object... args) {
        //identifiant existant
        if(event.equals("resListPersonnes")){
            JSONObject jsonObject=null;
            String jsonMail="";
            JSONArray jsonArray= (JSONArray) args[0];
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    jsonObject=jsonArray.getJSONObject(i);
                    if(jsonObject.getString("mail").equals(Compte.mail)){
                        Compte.identifiantExist=0;
                        break;
                    }else{
                        Compte.identifiantExist=1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onError(SocketIOException e) {

    }
}

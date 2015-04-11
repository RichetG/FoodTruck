package com.example.guillaume.foodtruck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends FragmentActivity{

    private TextView deco, id;
    private Button select;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ObjectMapper objectMapper;
    private ListView liste;
    private ArrayList<HashMap<String, String>>table;
    private Personne personne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        setUpMapIfNeeded();
        id = (TextView) findViewById(R.id.identifiantClient);
        deco = (TextView) findViewById(R.id.deconnectionClient);
        liste=(ListView) findViewById(R.id.repertoire);
        select=(Button) findViewById(R.id.select);
        table=new ArrayList<HashMap<String, String>>();

        //recuperation des données de personne uniquement pour recuperer le mail en guise de clé BDD
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
            id.setText(personne.getPseudo());
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

        //redirection vers la page MainActivity
        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Client.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //selection des foodTrucks
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder boite;
                LinearLayout layout=new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final Spinner liste=new Spinner(v.getContext());
                final ArrayList<String>departement=new ArrayList<String>();
                departement.add("Ain (01)");
                departement.add("Aisne (02)");
                departement.add("Allier (03)");
                departement.add("Alpes-de-Haute-Provence (04)");
                departement.add("Hautes-Alpes (05)");
                departement.add("Alpes-Maritimes (06)");
                departement.add("Ardèche (07)");
                departement.add("Ardennes (08)");
                departement.add("Ariège (09)");
                departement.add("Aube (10)");
                departement.add("Aude (11)");
                departement.add("Aveyron (12)");
                departement.add("Bouches-du-Rhône (13)");
                departement.add("Calvados (14)");
                departement.add("Cantal (15)");
                departement.add("Charente (16)");
                departement.add("Charente-Maritime (17)");
                departement.add("Cher (18)");
                departement.add("Corrèze (19)");
                departement.add("Corse-du-Sud (2A)");
                departement.add("Haute-Corse (2B)");
                departement.add("Côtes-d'Or (21)");
                departement.add("Côtes-d'Armor (22)");
                departement.add("Creuse (23)");
                departement.add("Dordogne (24)");
                departement.add("Doubs (25)");
                departement.add("Drôme (26)");
                departement.add("Eure (27)");
                departement.add("Eure-et-Loir (28)");
                departement.add("Finistère (29)");
                departement.add("Gard (30)");
                departement.add("Haute-Garonne (31)");
                departement.add("Gers (32)");
                departement.add("Gironde (33)");
                departement.add("Hérault (34)");
                departement.add("Ille-et-Vilaine (35)");
                departement.add("Indre (36)");
                departement.add("Indre-et-Loire (37)");
                departement.add("Isère (38)");
                departement.add("Jura (39)");
                departement.add("Landes (40)");
                departement.add("Loir-et-Cher (41)");
                departement.add("Loire (42)");
                departement.add("Haute-Loire (43)");
                departement.add("Loire-Atlantique (44)");
                departement.add("Loiret (45)");
                departement.add("Lot (46)");
                departement.add("Lot-et-Garonne (47)");
                departement.add("Lozère (48)");
                departement.add("Maine-et-Loire (49)");
                departement.add("Manche (50)");
                departement.add("Marne (51)");
                departement.add("Haute-Marne (52)");
                departement.add("Mayenne (53)");
                departement.add("Meurthe-et-Moselle (54)");
                departement.add("Meuse (55)");
                departement.add("Morbihan (56)");
                departement.add("Moselle (57)");
                departement.add("Nièvre (58)");
                departement.add("Nord (59)");
                departement.add("Oise (60)");
                departement.add("Orne (61)");
                departement.add("Pas-de-Calais (62)");
                departement.add("Puy-de-Dôme (63)");
                departement.add("Pyrénées-Atlantiques (64)");
                departement.add("Hautes-Pyrénées (65)");
                departement.add("Pyrénées-Orientales (66)");
                departement.add("Bas-Rhin (67)");
                departement.add("Haut-Rhin (68)");
                departement.add("Rhône/Métropole de Lyon (69)");
                departement.add("Haute-Saône (70)");
                departement.add("Saône-et-Loire (71)");
                departement.add("Sarthe (72)");
                departement.add("Savoie (73)");
                departement.add("Haute-Savoie (74)");
                departement.add("Paris (75)");
                departement.add("Seine-Maritime (76)");
                departement.add("Seine-et-Marne (77)");
                departement.add("Yvelines (78)");
                departement.add("Deux-Sèvres (79)");
                departement.add("Somme (80)");
                departement.add("Tarn (81)");
                departement.add("Tarn-et-Garonne (82)");
                departement.add("Var (83)");
                departement.add("Vaucluse (84)");
                departement.add("Vendée (85)");
                departement.add("Vienne (86)");
                departement.add("Haute-Vienne (87)");
                departement.add("Vosges (88)");
                departement.add("Yonne (89)");
                departement.add("Territoire de Belfort (90)");
                departement.add("Essonne (91)");
                departement.add("Hauts-de-Seine (92)");
                departement.add("Seine-Saint-Denis (93)");
                departement.add("Val-de-marne (94)");
                departement.add("Val-d'Oise (95)");
                departement.add("Guadeloupe (971)");
                departement.add("Martinique (972)");
                departement.add("Guyane (973)");
                departement.add("La Reunion (974");
                departement.add("Mayotte (976)");

                ArrayAdapter adapter=new ArrayAdapter(Client.this, android.R.layout.simple_spinner_item, departement);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                liste.setAdapter(adapter);

                final SeekBar barre=new SeekBar(v.getContext());
                final TextView valeur=new TextView(v.getContext());
                boite=new AlertDialog.Builder(v.getContext());
                barre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        valeur.setText(progress + " Km");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                barre.setMax(100);
                barre.setProgress(50);
                valeur.setGravity(Gravity.CENTER);
                layout.addView(liste);
                layout.addView(barre);
                layout.addView(valeur);
                boite.setView(layout);
                boite.setTitle(R.string.food);
                boite.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO recherche bdd


                    }
                });
                boite.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    //TODO
    //faire en sorte de recuperer les coords du ou des vendeurs

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapCli)).getMap();
        }
    }
}
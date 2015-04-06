package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume on 07/02/15.
 */
public class ModifPage extends Activity{

    private EditText description, promo, menus, titre, telephone;
    private Button logo, valider;
    private static final int REQUEST_CODE=1;
    private Bitmap bitmap;
    private ImageView imageView;
    private ObjectMapper objectMapper;
    private Spinner deroulant;
    private Page page;
    private List<String>departement;
    private int numDep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modif_page);

        description = (EditText) findViewById(R.id.description);
        promo = (EditText) findViewById(R.id.promo);
        menus = (EditText) findViewById(R.id.menu);
        titre=(EditText) findViewById(R.id.titre);
        logo = (Button) findViewById(R.id.choisir);
        telephone = (EditText) findViewById(R.id.telephone);
        valider = (Button) findViewById(R.id.validerPage);
        imageView = (ImageView) findViewById(R.id.image);
        deroulant=(Spinner) findViewById(R.id.deroulant);
        departement=new ArrayList<String>();
        numDep=1;

        //recuperation donnée
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in=openFileInput("page.json");
            page=objectMapper.readValue(in, Page.class);
            titre.setText(page.getTitre());
            titre.setTextColor(Color.BLACK);
            numDep=page.getDepartement();
            description.setText(page.getDescription());
            promo.setText(page.getPromo());
            menus.setText(page.getMenu());
            telephone.setText(page.getTelephone());
            Bitmap bm = BitmapFactory.decodeByteArray(page.getLogo(), 0, page.getLogo().length);
            imageView.setImageBitmap(bm);
        }catch (JsonGenerationException f){
            f.printStackTrace();
        }catch (IOException f){
            f.printStackTrace();
        }

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

        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, departement);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deroulant.setAdapter(adapter);

        if(numDep<20){
            deroulant.setSelection(numDep-1);
        }else if(numDep>=21 && numDep<=95){
            deroulant.setSelection(numDep);
        }else if(numDep==201){
            deroulant.setSelection(19);
        }else if(numDep==202){
            deroulant.setSelection(20);
        }else if(numDep==971){
            deroulant.setSelection(96);
        }else if(numDep==972){
            deroulant.setSelection(97);
        }else if(numDep==973){
            deroulant.setSelection(98);
        }else if(numDep==974){
            deroulant.setSelection(99);
        }else if(numDep==976){
            deroulant.setSelection(100);
        }


        deroulant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String phrase[] = String.valueOf(deroulant.getSelectedItem()).split(" ");
                if(phrase[1].toString().equals("(2A)")){
                    numDep=201;
                }else if(phrase[1].toString().equals("(2B)")){
                    numDep=202;
                }else {
                    numDep = Integer.parseInt(phrase[1].substring(1, phrase[1].length() - 1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        titre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titre.setText("");
                titre.setTextColor(Color.BLACK);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(telephone.getText().toString().matches("[0-9]+") && telephone.length()==10)){
                    telephone.setText("");
                    Toast.makeText(ModifPage.this, R.string.erreurTelephone, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ModifPage.this, R.string.modification, Toast.LENGTH_SHORT).show();
                    //stockage des donnée d'une personne de type vendeur
                    imageView.buildDrawingCache();
                    Bitmap bmap = imageView.getDrawingCache();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                    byte[] biteArray = bos.toByteArray();
                    page = new Page(titre.getText().toString(), numDep, description.getText().toString(), promo.getText().toString(), menus.getText().toString(), telephone.getText().toString(), biteArray);
                    objectMapper = new ObjectMapper();
                    try {
                        FileOutputStream out = openFileOutput("page.json", Context.MODE_PRIVATE);
                        objectMapper.writeValue(out, page);
                    } catch (JsonGenerationException f) {
                        f.printStackTrace();
                    } catch (IOException f) {
                        f.printStackTrace();
                    }
                }
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    //TODO
    /*faire en sorte d'envoyer tous les informations a la BDD*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
        if(requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK){
            try{
                if(bitmap!=null){
                    bitmap.recycle();
                }
                InputStream stream=getContentResolver().openInputStream(imageReturnedIntent.getData());
                bitmap= BitmapFactory.decodeStream(stream);
                stream.close();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        }
    }
}

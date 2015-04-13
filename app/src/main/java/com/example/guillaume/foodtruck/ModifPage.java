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
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
    private Page page;
    private Personne personne;

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

        //recuperation donnée
        objectMapper=new ObjectMapper();
        try {
            FileInputStream in = openFileInput("personne.json");
            personne=objectMapper.readValue(in, Personne.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //recuperation donnée
        objectMapper=new ObjectMapper();
        try {
            //TODO recuperer fichier json du bon vendeur avec appel sur son mail
            FileInputStream in=openFileInput("page.json");
            page=objectMapper.readValue(in, Page.class);
            titre.setText(page.getTitre());
            titre.setTextColor(Color.BLACK);
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
                    page = new Page(personne.getMail(), titre.getText().toString(), description.getText().toString(), promo.getText().toString(), menus.getText().toString(), telephone.getText().toString(), biteArray);
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

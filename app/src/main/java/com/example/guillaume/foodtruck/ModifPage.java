package com.example.guillaume.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by guillaume on 07/02/15.
 */
public class ModifPage extends Activity{

    private EditText description, promo, menus;
    private Button logo, valider;
    private static final int REQUEST_CODE=1;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modif_page);

        description = (EditText) findViewById(R.id.description);
        promo = (EditText) findViewById(R.id.promo);
        menus = (EditText) findViewById(R.id.menu);
        logo = (Button) findViewById(R.id.choisir);
        valider = (Button) findViewById(R.id.validerPage);
        imageView = (ImageView) findViewById(R.id.image);

        logo.setBackgroundColor(Color.GREEN);
        valider.setBackgroundColor(Color.GREEN);

        //recuperation du contenu de description
        try{
            FileInputStream inDes=ModifPage.this.openFileInput("des.txt");
            int c;
            String temp="";
            while( (c = inDes.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            description.setText(temp);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //recuperation du contenu de promo
        try{
            FileInputStream inPro=ModifPage.this.openFileInput("pro.txt");
            int c;
            String temp="";
            while( (c = inPro.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            promo.setText(temp);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //recuperation du contenu de menus
        try{
            FileInputStream inMen=ModifPage.this.openFileInput("men.txt");
            int c;
            String temp="";
            while( (c = inMen.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            menus.setText(temp);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //recuperation image
        try {
            FileInputStream inImg= ModifPage.this.openFileInput("icone.png");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int bytesRead;
            while ((bytesRead = inImg.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byte[] bytes = bos.toByteArray();
            if(bytes==null){
                imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.couvert));
            }else {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bm);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ModifPage.this, R.string.modification, Toast.LENGTH_SHORT).show();
                //sauvegarde du contenu de description
                try{
                    FileOutputStream outDes=ModifPage.this.openFileOutput("des.txt", Context.MODE_PRIVATE);
                    outDes.write(description.getText().toString().getBytes());
                    outDes.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //sauvegarde du contenu de promo
                try{
                    FileOutputStream outPro=ModifPage.this.openFileOutput("pro.txt", Context.MODE_PRIVATE);
                    outPro.write(promo.getText().toString().getBytes());
                    outPro.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //sauvegarde du contenu de menus
                try{
                    FileOutputStream outMen=ModifPage.this.openFileOutput("men.txt", Context.MODE_PRIVATE);
                    outMen.write(menus.getText().toString().getBytes());
                    outMen.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //sauvegarde image
                imageView.buildDrawingCache();
                Bitmap bmap = imageView.getDrawingCache();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 0 , bos);
                byte[] biteArray = bos.toByteArray();
                try {
                    FileOutputStream fos=ModifPage.this.openFileOutput("icone.png", Context.MODE_PRIVATE);
                    fos.write(biteArray);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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

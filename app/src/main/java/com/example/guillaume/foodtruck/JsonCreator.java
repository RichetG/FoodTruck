package com.example.guillaume.foodtruck;

import android.os.Environment;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by guillaume on 16/03/2015.
 */
public class JsonCreator {

    private static final String url="http://www.tutos-android.com/JSON/users.json";//TODO
    private ObjectMapper objectMapper=null;
    private JsonFactory jsonFactory=null;
    private JsonParser jp=null;
    private Personne personne=null;
    private File jsonOutputFile;
    private File jsonFile;

    public JsonCreator(){
        objectMapper=new ObjectMapper();
        jsonFactory=new JsonFactory();
    }

    public void init(){
        downloadJsonFile();
        try{
            jp=jsonFactory.createJsonParser(jsonFile);
            personne=objectMapper.readValue(jp, Personne.class);
        }catch (JsonParseException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void downloadJsonFile(){
        try{
            createFileAndDirectory();
            URL url=new URL(JsonCreator.url);
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            FileOutputStream fileOutput = new FileOutputStream(jsonFile);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileAndDirectory() throws FileNotFoundException {
        final String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        final String Directory_path = extStorageDirectory + "/android";
        jsonOutputFile = new File(Directory_path, "/");
        if (jsonOutputFile.exists() == false) {
            jsonOutputFile.mkdirs();
        }
        jsonFile = new File(jsonOutputFile, "personnes.json");
    }
}

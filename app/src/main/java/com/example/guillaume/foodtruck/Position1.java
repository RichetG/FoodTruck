package com.example.guillaume.foodtruck;

/**
 * Created by guillaume on 11/04/2015.
 */

import android.util.Log;

import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.annotate.JsonCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Position1 {

    private String mail;
    public Map<String, List<Double>> markers=new HashMap<String, List<Double>>();

    @JsonCreator
    public Position1(@JsonProperty("mail") String mail){
        this.mail=mail;
    }

    public String getMail(){
        return mail;
    }

    public void addPosition(Position2 position){
        List<Double>liste=new ArrayList<Double>();
        liste.add(position.getLatitude());
        liste.add(position.getLongitude());
        markers.put(position.getTitre(), liste);
    }

    public Set<String> keySet(){
        return markers.keySet();
    }

    public List<Double> getPosition(String titre){
        return markers.get(titre);
    }

    public int size(){
        return markers.size();
    }

}

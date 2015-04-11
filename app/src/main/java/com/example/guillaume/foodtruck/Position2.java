package com.example.guillaume.foodtruck;

/**
 * Created by guillaume on 11/04/2015.
 */

import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.annotate.JsonCreator;

public class Position2 {

    private String titre;
    private Double latitude, longitude;

    @JsonCreator
    public Position2(@JsonProperty("titre")String titre, @JsonProperty("latitude")Double latitude, @JsonProperty("longitude")Double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
        this.titre=titre;
    }

    public String  getTitre(){
        return titre;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude(){
        return longitude;
    }
}

package com.example.guillaume.foodtruck;

import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Created by guillaume on 16/03/2015.
 */
public class Page {

    public String promo, description, menu, titre;
    public byte[] logo;

    @JsonCreator
    public Page(@JsonProperty("titre")String titre, @JsonProperty("description")String description, @JsonProperty("promo")String promo, @JsonProperty("menu")String menu, @JsonProperty("logo")byte[] logo){
        this.titre=titre;
        this.description=description;
        this.promo=promo;
        this.menu=menu;
        this.logo=logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public byte[] getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    public String getMenu() {
        return menu;
    }

    public String getPromo() {
        return promo;
    }

    public String getTitre() {
        return titre;
    }
}

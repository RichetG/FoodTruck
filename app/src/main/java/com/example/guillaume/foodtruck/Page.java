package com.example.guillaume.foodtruck;

import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Created by guillaume on 16/03/2015.
 */
public class Page {

    public String promo, description, menu, titre, telephone, mail;
    public byte[] logo;
    public int departement;

    @JsonCreator
    public Page(@JsonProperty("mail")String mail, @JsonProperty("titre")String titre, @JsonProperty("departement")int departement, @JsonProperty("description")String description, @JsonProperty("promo")String promo, @JsonProperty("menu")String menu, @JsonProperty("telephone")String telephone, @JsonProperty("logo")byte[] logo){
        this.titre=titre;
        this.departement=departement;
        this.description=description;
        this.promo=promo;
        this.menu=menu;
        this.telephone=telephone;
        this.logo=logo;
        this.mail=mail;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public void setDepartement(int departement){ this.departement=departement;}

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

    public void setTelephone(String telephone){ this.telephone= telephone; }

    public byte[] getLogo() {
        return logo;
    }

    public int getDepartement(){return departement;}

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

    public String getTelephone() { return telephone; }

    public String getMail(){ return mail;}
}

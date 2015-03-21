package com.example.guillaume.foodtruck;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by guillaume on 16/03/2015.
 */
public class Personne {

    public String pseudo;
    public String mail;
    public String mdp;
    public String type;

    @JsonCreator
    public Personne(@JsonProperty("pseudo")String pseudo, @JsonProperty("mail")String mail, @JsonProperty("mdp")String mdp, @JsonProperty("type")String type) {
        this.pseudo=pseudo;
        this.mail=mail;
        this.mdp=mdp;
        this.type=type;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getType() {
        return type;
    }
}

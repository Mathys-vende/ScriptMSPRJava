package fr.epsi.MSPR.models;

import java.util.ArrayList;

public class Salarie {
    private String Nom;
    private String Prenom;
    private String Mission;
    private String Htpasswd;
    private ArrayList<Equipement> Equipements;

    public Salarie(String Nom, String Prenom, String Mission , String Htpasswd, ArrayList<Equipement> Equipements){
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Mission = Mission;
        this.Htpasswd = Htpasswd;
        this.Equipements = Equipements;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getMission() {
        return Mission;
    }

    public void setMission(String mission) {
        Mission = mission;
    }

    public String getHtpasswd() {
        return Htpasswd;
    }

    public void setHtpasswd(String htpasswd) {
        Htpasswd = htpasswd;
    }

    public ArrayList<Equipement> getEquipements() {
        return Equipements;
    }

    public void setEquipements(ArrayList<Equipement> equipements) {
        Equipements = equipements;
    }
}

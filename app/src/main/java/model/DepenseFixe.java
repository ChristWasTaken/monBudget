package model;

import java.time.LocalDate;

public class DepenseFixe {
    //Class pojo pour bd

    //Attributs
    private String description;
    private double montant;
    private String categorie;
    private String sousCategorie;
    private int frequence;
    private LocalDate date;

    //Constructeurs
    public DepenseFixe(String description, double montant, String categorie, String sousCategorie, int frequence, LocalDate date) {
        this.description = description;
        this.montant = montant;
        this.categorie = categorie;
        this.sousCategorie = sousCategorie;
        this.frequence = frequence;
        this.date = date;
    }

    public DepenseFixe() {
    }

    //Getter + Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(String sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    public int getFrequence() {
        return frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    //To-String
    @Override
    public String toString() {
        return "DepenseFixe{" +
                "description='" + description + '\'' +
                ", montant=" + montant +
                ", categorie='" + categorie + '\'' +
                ", sousCategorie='" + sousCategorie + '\'' +
                ", frequence=" + frequence +
                ", date=" + date +
                '}';
    }
}

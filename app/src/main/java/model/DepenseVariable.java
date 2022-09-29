package model;

import java.time.LocalDate;

public class DepenseVariable {
    //Class pojo pour bd

    //Attributs
    private String description;
    private double montant;
    private String categorie;
    private String sousCategorie;
    private LocalDate date;
    private int idCompte;
    //Constructeurs
    public DepenseVariable(String description, double montant, String categorie, String sousCategorie, LocalDate date, int idCompte) {
        this.description = description;
        this.montant = montant;
        this.categorie = categorie;
        this.sousCategorie = sousCategorie;
        this.date = date;
        this.idCompte = idCompte;
    }

    public DepenseVariable() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    //To-String

    @Override
    public String toString() {
        return "DepenseVariable{" +
                "description='" + description + '\'' +
                ", montant=" + montant +
                ", categorie='" + categorie + '\'' +
                ", sousCategorie='" + sousCategorie + '\'' +
                ", date=" + date +
                ", idCompte=" + idCompte +
                '}';
    }
}

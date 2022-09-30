package model;

import java.time.LocalDate;

public class Revenue {
    //Class pojo pour bd

    //Attributs
    private int idRevenue;
    private String description;
    private double montant;
    private String type;
    private int frequence;
    private LocalDate date;
    private int idCompte;

    //Constructeurs
    public Revenue(String description, double montant, String type, int frequence, LocalDate date, int idCompte) {
        this.description = description;
        this.montant = montant;
        this.type = type;
        this.frequence = frequence;
        this.date = date;
        this.idCompte = idCompte;
    }

    public Revenue() {
    }

    //Getter + Setters

    public int getIdRevenue() {
        return idRevenue;
    }

    public void setIdRevenue(int idRevenue) {
        this.idRevenue = idRevenue;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    //To-String

    @Override
    public String toString() {
        return "Revenue{" +
                "description='" + description + '\'' +
                ", montant=" + montant +
                ", type='" + type + '\'' +
                ", frequence=" + frequence +
                ", date=" + date +
                ", idCompte=" + idCompte +
                '}';
    }
}

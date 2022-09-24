package model;

import java.time.LocalDate;

public class Revenue {
    //Class pojo pour bd

    //Attributs
    private String description;
    private double montant;
    private String type;
    private int frequence;
    private LocalDate date;

    //Constructeurs
    public Revenue(String description, double montant, String type, int frequence, LocalDate date) {
        this.description = description;
        this.montant = montant;
        this.type = type;
        this.frequence = frequence;
        this.date = date;
    }

    public Revenue() {
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

    //To-String
    @Override
    public String toString() {
        return "Revenue{" +
                "description='" + description + '\'' +
                ", montant=" + montant +
                ", type='" + type + '\'' +
                ", frequence=" + frequence +
                ", date=" + date +
                '}';
    }
}

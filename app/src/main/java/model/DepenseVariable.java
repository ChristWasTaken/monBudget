package model;

import java.time.LocalDate;
import java.util.List;

public class DepenseVariable {
    //Class pojo pour bd

    //Attributs
    private int idDepenseVariable;
    private String description;
    private double montant;
    private String categorie;
    private String sousCategorie;
    private LocalDate date;
    private int idCompte;

    //Constructeurs
    //Sans id pour l'ajout dans la base de donnee (auto-increment)
    public DepenseVariable(String description, double montant, String categorie, String sousCategorie, LocalDate date, int idCompte) {
        this.description = description;
        this.montant = montant;
        this.categorie = categorie;
        this.sousCategorie = sousCategorie;
        this.date = date;
        this.idCompte = idCompte;
    }

    //Avec id pour aller le chercher
    public DepenseVariable(int idDepenseVariable, String description, double montant, String categorie, String sousCategorie, LocalDate date, int idCompte) {
        this.idDepenseVariable = idDepenseVariable;
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

    public int getIdDepenseVariable() {
        return idDepenseVariable;
    }

    public void setIdDepenseVariable(int idDepenseVariable) {
        this.idDepenseVariable = idDepenseVariable;
    }

    public double calculerTotalDepenseMensuelle(List<DepenseVariable> liste) {
        double total = 0;
        for (DepenseVariable depense : liste) {
            total += depense.getMontant();
        }
        return total;
    }

    //To-String

    @Override
    public String toString() {
        return  "$ = " + montant;
    }
}

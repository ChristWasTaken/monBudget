package model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    //Méthodes
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Integer> trouverJoursVersement(Revenue revenue, LocalDate moisAfficher) {

        // Instantiation des données du premier versement
        int frequencePaiement = revenue.getFrequence();
        int jourInitialDePaiement = revenue.getDate().getDayOfMonth();
        int anneInitialDePaiement = revenue.getDate().getYear();

        // Instantiation des données du mois à afficher
        LocalDate premierJourDuMois = LocalDate.of(moisAfficher.getYear(), moisAfficher.getMonth(), 1);
        int premierDuMois = premierJourDuMois.getDayOfYear();
        int jourDansMois = premierJourDuMois.lengthOfMonth();
        int dernierDuMois = premierDuMois + jourDansMois;
        int anneeActuel = premierJourDuMois.getYear();

        // Calcul du nombre de jours entre le premier versement et le premier du mois à afficher
        int nbJours = 0;
        if (anneeActuel == anneInitialDePaiement) {
            nbJours = premierDuMois - jourInitialDePaiement;
        } else {
            nbJours = (anneeActuel - anneInitialDePaiement) * 365 + premierDuMois - jourInitialDePaiement;
        }

        // Calcul du nombre de versements à effecter dans le mois et leur dates
        int nbVersementsTotauxEffectue = (int) Math.floor(nbJours / frequencePaiement);

        int jourDuMois = premierDuMois;
        int jourProchainVersement = jourInitialDePaiement + frequencePaiement * nbVersementsTotauxEffectue;
        List<Integer> joursVersements = new ArrayList<>();

        if(jourProchainVersement < jourDuMois){
            jourProchainVersement = jourProchainVersement + frequencePaiement*7;
        }

        while (jourDuMois <= dernierDuMois) {
            if (jourDuMois == jourProchainVersement) {
                joursVersements.add(jourDuMois);
                jourDuMois += frequencePaiement;
                jourProchainVersement += frequencePaiement*7;
            } else {
                jourDuMois++;
            }
        }
        return joursVersements;
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

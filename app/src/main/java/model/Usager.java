package model;

public class Usager {
    //Class pojo pour la bd

    //Attribut
    private String nom;
    private String motPasse;
    private String courriel;

    //Constructeur
    public Usager(String nom, String motPasse, String courriel) {
        this.nom = nom;
        this.motPasse = motPasse;
        this.courriel = courriel;
    }

    public Usager() {
    }

    //Getter et setter
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    //Tostring
    @Override
    public String toString() {
        return "Usager{" +
                "nom='" + nom + '\'' +
                ", motPasse='" + motPasse + '\'' +
                ", courriel='" + courriel + '\'' +
                '}';
    }
}

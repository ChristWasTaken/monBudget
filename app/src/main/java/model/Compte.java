package model;

public class Compte {
    //Class pojo pour bd

    //Attributs
    private int idCompte;
    private String description;
    private double solde;
    private String type;
    private String institution;
    private int numCompte;
    private int numSuccursale;

    //Constructeurs
    //Sans id pour l'ajout dans la base de donnee (auto-increment)
    public Compte(String description, double solde, String type, String institution, int numCompte, int numSuccursale) {
        this.description = description;
        this.solde = solde;
        this.type = type;
        this.institution = institution;
        this.numCompte = numCompte;
        this.numSuccursale = numSuccursale;
    }

    //Avec id pour aller le chercher
    public Compte(int idCompte, String description, double solde, String type, String institution, int numCompte, int numSuccursale) {
        this.idCompte = idCompte;
        this.description = description;
        this.solde = solde;
        this.type = type;
        this.institution = institution;
        this.numCompte = numCompte;
        this.numSuccursale = numSuccursale;
    }

    public Compte() {
    }

    //Getter + Setters
    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public int getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }

    public int getNumSuccursale() {
        return numSuccursale;
    }

    public void setNumSuccursale(int numSuccursale) {
        this.numSuccursale = numSuccursale;
    }

    public double calculerSoldesTotaux(double[] soldes) {
        return 0;
    }

    //To-String
    @Override
    public String toString() {
        return "Compte{" +
                "description='" + description + '\'' +
                ", solde=" + solde +
                ", type='" + type + '\'' +
                ", institution='" + institution + '\'' +
                ", numCompte=" + numCompte +
                ", numSuccursale=" + numSuccursale +
                '}';
    }
}

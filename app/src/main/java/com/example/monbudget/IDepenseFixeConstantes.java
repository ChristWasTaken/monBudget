package com.example.monbudget;

public interface IDepenseFixeConstantes {
    //Categories fixes
    String LOGEMENT = "Logement";
    String SERVICE_PUBLIC = "Services publics";
    String ASSURANCES = "Assurances";
    String EMPRUNTS = "Emprunts";
    String GARDERIE = "Garderie";
    String FRAIS_COMPTE_BANCAIRE = "Frais de comptes bancaires";
    String AUTRE = "Autre";

    //Sous-categories fixes
    String HYPOTHEQUE = "Hypotheque";
    String LOYER = "Loyer";
    String TAXES_MUNICIPALE = "Taxes municipales";
    String TAXES_SCOLAIRE = "Taxes Scolaire";
    String ELECTRICITE = "Electricite";
    String CHAUFFAGE = "Chauffage";
    String CABLE = "Cable";
    String TELEPHONE = "Telephone";
    String INTERNET = "Internet";
    String ASSURANCE_VIE = "Vie/Invalidite";
    String ASSURANCE_HAB = "Habitation";
    String ASSURANCE_VOITURE = "Automobile/immatriculation/permis de conduire";
    String AUTOMOBILE = "Automobile";
    String CREDIT = "Cartes de credits";
    String MARGE = "Marge de credit";
    String AUTRE_EMPRUNTS = "Autre";

    //Tableau de categories fixes
    String[] CATEGORIES_FIXES = {LOGEMENT, SERVICE_PUBLIC, ASSURANCES, EMPRUNTS, GARDERIE, FRAIS_COMPTE_BANCAIRE, AUTRE};

    //Tableau de sous-categorie fixes
    String[] S_CATEGOORIE_LOGEMENT = {HYPOTHEQUE, LOYER};
    String[] S_CATEGORIE_SERVICES_PUBLICS = {TAXES_MUNICIPALE, TAXES_SCOLAIRE, ELECTRICITE, CHAUFFAGE, CABLE, TELEPHONE, INTERNET};
    String[] S_CATEGORIE_ASSURANCES = {ASSURANCE_VIE, ASSURANCE_HAB, ASSURANCE_VOITURE};
    String[] S_CATEGORIE_EMPRUNTS = {AUTOMOBILE, CREDIT, MARGE, AUTRE_EMPRUNTS};

    //Categorie variables
    String ALIMENTATION = "Alimentation";
    String TABAC_ALCOOL = "Tabac/Alcool";
    String VETEMENTS = "Vetements achats et/ou entretien";
    String SANTE_BEAUTE = "Sante/Beaute";
    String ENFANTS = "Depenses enfants";
    String ANIMAL = "Depenses animaux domestiques";
    String MENAGER = "Entretien menage";
    String JOURNAL = "Journaux/magazines/etc.";
    String SPORTS = "Sports/Sorties/Cours";
    String POCHE = "Argent de poche";
    String CADEAUX = "Cadeaux/Dons";
    String AUTRE_DEPENSES = "Autres depenses";

    //Sous categorie variables
    String EPICERIE = "Epicerie";
    String RESTO = "Restaurants/livraison";
    String TRAVAIL = "Repas travil/ecole";

    //Tableau
    String[] CATEGORIES_VARIABLE = {ALIMENTATION, TABAC_ALCOOL, VETEMENTS, SANTE_BEAUTE, ENFANTS, ANIMAL, MENAGER, JOURNAL, SPORTS, POCHE, CADEAUX, AUTRE_DEPENSES};
    String[] S_CATEGORIE_ALIMENTATION = {EPICERIE, RESTO, TRAVAIL};

}

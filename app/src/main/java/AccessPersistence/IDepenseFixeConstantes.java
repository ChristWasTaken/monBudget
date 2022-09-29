package AccessPersistence;
@SuppressWarnings("ALL")

public interface IDepenseFixeConstantes {

    //Declaration des constantes pour la bd + la table
    //BD
    String BD_NOM = "monBudget";
    int VERSION = 1;

    //Table
    String TABLE_DEPENSEFIXE = "tbl_depenseFixe";
    String COL_ID = "_id";
    String COL_DESCRIPTION = "description";
    String COL_MONTANT = "montant";
    String COL_CATEGORIE = "categorie";
    String COL_SOUSCATEGORIE = "sousCategorie";
    String COL_FREQUENCE = "frequence";
    String COL_DATE = "date";
    String COL_IDCOMPTE = "idCompte";

    // Tableau des colonnes pour Select *
    String[] COLONNES = {COL_ID, COL_DESCRIPTION, COL_MONTANT,
            COL_CATEGORIE, COL_SOUSCATEGORIE, COL_FREQUENCE, COL_DATE, COL_IDCOMPTE};

    //DDL table
    String CREATE_TABLE_DEPENSEFIXE = "create table " + TABLE_DEPENSEFIXE + " ("
        + COL_ID + " integer primary key autoincrement, "
        + COL_DESCRIPTION + " text, "
        + COL_MONTANT + " real, "
        + COL_CATEGORIE + " text, "
        + COL_SOUSCATEGORIE + " text, "
        + COL_FREQUENCE + " integer, "
        + COL_DATE + " date, "
        + COL_IDCOMPTE + " integer"
        + ");";
}

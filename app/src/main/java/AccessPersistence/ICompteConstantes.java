package AccessPersistence;

public interface ICompteConstantes {

    //Declaration des constantes pour la bd + la table
    //BD
    String BD_NOM = "monBudget";
    int VERSION = 1;

    //Table
    String TABLE_COMPTE = "tbl_compte";
    String COL_ID = "_id";
    String COL_DESCRIPTION = "description";
    String COL_SOLDE = "solde";
    String COL_TYPE = "type";
    String COL_INSTITUTION = "institution";
    String COL_NUMCOMPTE = "numCompte";
    String COL_NUMSUCCURSALE = "numSuccursale";

    // Tableau des colonnes pour Select *
    String[] COLONNES = {COL_ID, COL_DESCRIPTION, COL_SOLDE,
            COL_TYPE, COL_INSTITUTION, COL_NUMCOMPTE, COL_NUMSUCCURSALE};

    //DDL table
    String CREATE_TABLE_COMPTE = "create table " + TABLE_COMPTE + " ("
        + COL_ID + " integer primary key autoincrement, "
        + COL_DESCRIPTION + " text, "
        + COL_SOLDE + " real, "
        + COL_TYPE + " text, "
        + COL_INSTITUTION + " text, "
        + COL_NUMCOMPTE + " integer, "
        + COL_NUMSUCCURSALE + " integer"
        + ")";
}

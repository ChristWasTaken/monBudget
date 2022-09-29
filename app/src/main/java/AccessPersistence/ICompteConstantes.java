package AccessPersistence;
@SuppressWarnings("ALL")

public interface ICompteConstantes {

    //Declaration des constantes pour la bd + la table
    //BD
    static final String BD_NOM = "monBudget";
    static final int VERSION = 1;

    //Table
    static final String TABLE_COMPTE = "tbl_compte";
    static final String COL_ID = "_id";
    static final String COL_DESCRIPTION = "description";
    static final String COL_SOLDE = "solde";
    static final String COL_TYPE = "type";
    static final String COL_INSTITUTION = "institution";
    static final String COL_NUMCOMPTE = "numCompte";
    static final String COL_NUMSUCCURSALE = "numSuccursale";

    // Tableau des colonnes pour Select *
    static final String[] COLONNES = {COL_ID, COL_DESCRIPTION, COL_SOLDE,
            COL_TYPE, COL_INSTITUTION, COL_NUMCOMPTE, COL_NUMSUCCURSALE};

    //DDL table
    static final String CREATE_TABLE_COMPTE = "create table " + TABLE_COMPTE + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_DESCRIPTION + " text, "
            + COL_SOLDE + " real, "
            + COL_TYPE + " text, "
            + COL_INSTITUTION + " text, "
            + COL_NUMCOMPTE + " integer, "
            + COL_NUMSUCCURSALE + " integer"
            + ")";
}

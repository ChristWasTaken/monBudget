package AccessPersistence;

public interface IRevenueConstantes {
    //Declaration des constantes pour la bd + la table
    //BD
    String BD_NOM = "monBudget";
    int VERSION = 1;

    //Table
    String TABLE_REVENUE = "tbl_revenue";
    String COL_ID = "_id";
    String COL_DESCRIPTION = "description";
    String COL_MONTANT = "montant";
    String COL_TYPE = "type";
    String COL_FREQUENCE = "frequence";
    String COL_DATE = "date";
    String COL_IDCOMPTE = "idCompte";

    //Tableau des colonnes pour Select *
    String[] COLONNES = {COL_ID, COL_DESCRIPTION, COL_MONTANT,
            COL_TYPE, COL_FREQUENCE, COL_DATE, COL_IDCOMPTE};

    //DDL table
    String CREATE_TABLE_REVENUE = "create table " + TABLE_REVENUE + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_DESCRIPTION + " text, "
            + COL_MONTANT + " real, "
            + COL_TYPE + " text, "
            + COL_FREQUENCE + " integer, "
            + COL_DATE + " text, "
            + COL_IDCOMPTE + " integer"
            + ")";
}

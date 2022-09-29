package AccessPersistence;
@SuppressWarnings("ALL")

public interface IDepenseFixeConstantes {

    //Declaration des constantes pour la bd + la table
    //BD
    static final String BD_NOM = "monBudget";
    static final int VERSION = 1;

    //Table
    static final String TABLE_DEPENSEFIXE = "tbl_depenseFixe";
    static final String COL_ID = "_id";
    static final String COL_DESCRIPTION = "description";
    static final String COL_MONTANT = "montant";
    static final String COL_CATEGORIE = "categorie";
    static final String COL_SOUSCATEGORIE = "sousCategorie";
    static final String COL_FREQUENCE = "frequence";
    static final String COL_DATE = "date";
    static final String COL_IDCOMPTE = "idCompte";

    static final String[] COLONNES = {COL_ID, COL_DESCRIPTION, COL_MONTANT,
            COL_CATEGORIE, COL_SOUSCATEGORIE, COL_FREQUENCE, COL_DATE, COL_IDCOMPTE};

    //DDL table
    static final String CREATE_TABLE_DEPENSEFIXE = "create table " + TABLE_DEPENSEFIXE + " (" + COL_ID +
            " integer primary key autoincrement, " + COL_DESCRIPTION + " text, " + COL_MONTANT + " real, "
            + COL_CATEGORIE + " text, " + COL_SOUSCATEGORIE + " text, " + COL_FREQUENCE + " integer, " + COL_DATE
            + " date, " + COL_IDCOMPTE + " integer);";
}

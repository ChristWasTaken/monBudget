package AccessPersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import model.DepenseFixe;

public class DepenseFixeDBHelper extends SQLiteOpenHelper {
    //Declaration des constantes pour la bd + la table
    //BD
    public static final String BD_NOM = "monBudget";
    public static final int VERSION = 1;

    //Table
    public static final String TABLE_DEPENSEFIXE = "tbl_depenseFixe";
    public static final String COL_ID = "_id";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_MONTANT = "montant";
    public static final String COL_CATEGORIE = "categorie";
    public static final String COL_SOUSCATEGORIE = "sousCategorie";
    public static final String COL_FREQUENCE = "frequence";
    public static final String COL_DATE = "date";
    public static final String COL_IDCOMPTE = "idCompte";

    //DDL table
    public static final String CREATE_TABLE_DEPENSEFIXE = "create table " + TABLE_DEPENSEFIXE + " (" + COL_ID +
            " integer primary key autoincrement, " + COL_DESCRIPTION + " text, " + COL_MONTANT + " real, "
            + COL_CATEGORIE + " text, " + COL_SOUSCATEGORIE + " text, " + COL_FREQUENCE + " integer, " + COL_DATE
            + " date, " + COL_IDCOMPTE + " integer);";

    public DepenseFixeDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DEPENSEFIXE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

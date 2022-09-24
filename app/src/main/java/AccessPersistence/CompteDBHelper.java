package AccessPersistence;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CompteDBHelper extends SQLiteOpenHelper {
    //Declaration des constantes pour la bd + la table
    //BD
    public static final String BD_NOM = "monBudget";
    public static final int VERSION = 1;

    //Table
    public static final String TABLE_COMPTE = "tbl_compte";
    public static final String COL_ID = "_id";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_SOLDE = "solde";
    public static final String COL_TYPE = "type";
    public static final String COL_INSTITUTION = "institution";
    public static final String COL_NUMCOMPTE = "numCompte";
    public static final String COL_NUMSUCCURSALE = "numSuccursale";

    //DDL table
    public static final String CREATE_TABLE_COMPTE = "create table " + TABLE_COMPTE + " (" + COL_ID +
            " integer primary key autoincrement, " + COL_DESCRIPTION + " text, " + COL_SOLDE + " real, "
            + COL_TYPE + " text, " + COL_INSTITUTION + " text, " + COL_NUMCOMPTE + " integer, " + COL_NUMSUCCURSALE
            + " integer)";

    public CompteDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package AccessPersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DepenseVariableDBHelper extends SQLiteOpenHelper {
    //Declaration des constantes pour la bd + la table
    //BD
    public static final String BD_NOM = "monBudget";
    public static final int VERSION = 1;

    //Table
    public static final String TABLE_DEPENSEVARIABLE = "tbl_depenseVariable";
    public static final String COL_ID = "_id";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_MONTANT = "montant";
    public static final String COL_CATEGORIE = "categorie";
    public static final String COL_SOUSCATEGORIE = "sousCategorie";
    public static final String COL_DATE = "date";

    //DDL table
    public static final String CREATE_TABLE_DEPENSEVARIABLE = "create table " + TABLE_DEPENSEVARIABLE + " (" + COL_ID +
            " integer primary key autoincrement, " + COL_DESCRIPTION + " text, " + COL_MONTANT + " real, "
            + COL_CATEGORIE + " text, " + COL_SOUSCATEGORIE + " text, " + COL_DATE + " date)";

    public DepenseVariableDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DEPENSEVARIABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

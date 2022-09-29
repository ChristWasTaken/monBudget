package AccessPersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RevenueDBHelper extends SQLiteOpenHelper {
    //Declaration des constantes pour la bd + la table
    //BD
    public static final String BD_NOM = "monBudget";
    public static final int VERSION = 1;

    //Table
    public static final String TABLE_REVENUE = "tbl_revenue";
    public static final String COL_ID = "_id";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_MONTANT = "montant";
    public static final String COL_TYPE = "type";
    public static final String COL_FREQUENCE = "frequence";
    public static final String COL_DATE = "date";
    public static final String COL_IDCOMPTE = "idCompte";

    //DDL table
    public static final String CREATE_TABLE_DEPENSEVARIABLE = "create table " + TABLE_REVENUE + " (" + COL_ID +
            " integer primary key autoincrement, " + COL_DESCRIPTION + " text, " + COL_MONTANT + " real, "
            + COL_TYPE + " text, " + COL_FREQUENCE + " integer, " + COL_DATE + " date, " + COL_IDCOMPTE + " integer);";

    public RevenueDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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

package AccessPersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UsagerDBHelper extends SQLiteOpenHelper {
    //Declaration des constantes pour la bd + la table
    //BD
    public static final String BD_NOM = "monBudget";
    public static final int VERSION = 1;

    //Table
    public static final String TABLE_USAGER = "tbl_usager";
    public static final String COL_ID = "_id";
    public static final String COL_NOM = "nom";
    public static final String COL_MOTPASSE = "motPasse";
    public static final String COL_COURRIEL = "courriel";


    //DDL table
    public static final String CREATE_TABLE_USAGER = "create table " + TABLE_USAGER + " (" + COL_ID +
            " integer primary key autoincrement, " + COL_NOM + " text, " + COL_MOTPASSE + " text, " + COL_COURRIEL + " text)";

    public UsagerDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USAGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

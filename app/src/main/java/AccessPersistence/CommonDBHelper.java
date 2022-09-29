package AccessPersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CommonDBHelper extends SQLiteOpenHelper {
    public CommonDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ICompteConstantes.CREATE_TABLE_COMPTE);
        sqLiteDatabase.execSQL(IDepenseFixeConstantes.CREATE_TABLE_DEPENSEFIXE);
        sqLiteDatabase.execSQL(IDepenseVariableConstantes.CREATE_TABLE_DEPENSEVARIABLE);
        sqLiteDatabase.execSQL(IRevenueConstantes.CREATE_TABLE_REVENUE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

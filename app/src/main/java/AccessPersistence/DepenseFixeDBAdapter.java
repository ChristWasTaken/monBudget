package AccessPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.DepenseFixe;

public class DepenseFixeDBAdapter {

    private SQLiteDatabase db;
    private CommonDBHelper dbHelper;
    private Context context;

    public DepenseFixeDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new CommonDBHelper(context, IDepenseFixeConstantes.BD_NOM, null, IDepenseFixeConstantes.VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //Methode create
    public boolean ajouterDepenseFixe(DepenseFixe depenseFixe) {
        try {
            open();

            ContentValues contentValues = getContentValuesDepenseFixe(depenseFixe);
            if(db.insert(IDepenseFixeConstantes.TABLE_DEPENSEFIXE, null, contentValues) != -1) {
                Toast.makeText(context, "Ajout Reussi", Toast.LENGTH_LONG).show();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Erreur lors de l'ajout", Toast.LENGTH_LONG).show();
        return false;
    }

    @NonNull
    private ContentValues getContentValuesDepenseFixe(DepenseFixe depenseFixe) {
        String description = depenseFixe.getDescription();
        double montant = depenseFixe.getMontant();
        String categorie = depenseFixe.getCategorie();
        String sousCategorie = depenseFixe.getSousCategorie();
        int frequence = depenseFixe.getFrequence();
        String date = depenseFixe.getDate().toString();
        int idCompte = depenseFixe.getIdCompte();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IDepenseFixeConstantes.COL_DESCRIPTION, description);
        contentValues.put(IDepenseFixeConstantes.COL_MONTANT, montant);
        contentValues.put(IDepenseFixeConstantes.COL_CATEGORIE, categorie);
        contentValues.put(IDepenseFixeConstantes.COL_SOUSCATEGORIE, sousCategorie);
        contentValues.put(IDepenseFixeConstantes.COL_FREQUENCE, frequence);
        contentValues.put(IDepenseFixeConstantes.COL_DATE, date);
        contentValues.put(IDepenseFixeConstantes.COL_IDCOMPTE, idCompte);
        return contentValues;
    }

    //Methode findAll
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DepenseFixe> findAllDepensesFixes() {
        ArrayList<DepenseFixe> depensesFixes = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.rawQuery("SELECT * FROM " + IDepenseFixeConstantes.TABLE_DEPENSEFIXE
                    + " ORDER BY " + IDepenseFixeConstantes.COL_DATE, null);
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    DepenseFixe depenseFixe = getDepenseFixeFromCursor(cursor);
                    depensesFixes.add(depenseFixe);
                }
            }
            return depensesFixes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DepenseFixe getDepenseFixeFromCursor(Cursor cursor) {
        DepenseFixe depenseFixe = new DepenseFixe();
        depenseFixe.setIdDepenseFixe(cursor.getInt(cursor.getInt(0)));
        depenseFixe.setDescription(cursor.getString(cursor.getInt(1)));
        depenseFixe.setMontant(cursor.getDouble(cursor.getInt(2)));
        depenseFixe.setCategorie(cursor.getString(cursor.getInt(3)));
        depenseFixe.setSousCategorie(cursor.getString(cursor.getInt(4)));
        depenseFixe.setFrequence(cursor.getInt(cursor.getInt(5)));
        depenseFixe.setDate(LocalDate.parse(cursor.getString(cursor.getInt(6))));
        depenseFixe.setIdCompte(cursor.getInt(cursor.getInt(7)));
        return depenseFixe;
    }
}

package AccessPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.monbudget.Revenues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Revenue;

public class RevenueDBAdapter {

    SQLiteDatabase db;
    CommonDBHelper dbHelper;
    private Context context;

    public RevenueDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new CommonDBHelper(context, IRevenueConstantes.BD_NOM, null, IRevenueConstantes.VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //Methode create
    public boolean ajouterRevenue(Revenue revenue) {
        boolean resultat = false;
        try {
            open();

            ContentValues contentValues = getContentValuesRevenue(revenue);
            if(db.insert(IRevenueConstantes.TABLE_REVENUE, null, contentValues) != -1) {
                Toast.makeText(context, "Ajout Revenu Reussi", Toast.LENGTH_LONG).show();
                resultat = true;
            } else {
                Toast.makeText(context, "Erreur Revenu échoué", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return resultat;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Revenue> findAll(){
        List<Revenue> revenues = new ArrayList<>();
        try{
            open();
            Cursor cursor = db.query(IRevenueConstantes.TABLE_REVENUE, IRevenueConstantes.COLONNES,
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
                while(cursor.moveToNext()){
                    Revenue revenue = getRevenueFromCursor(cursor);
                    revenues.add(revenue);
                }
            }
            return revenues;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Revenue trouverRevenueParId(int id){
        try {
            open();
            Revenue revenue = new Revenue();
            Cursor cursor = db.query(IRevenueConstantes.TABLE_REVENUE, IRevenueConstantes.COLONNES,
                    IRevenueConstantes.COL_ID + " = " + id, null, null, null, IRevenueConstantes.COL_DATE);
            if(cursor != null){
                cursor.moveToFirst();
                revenue = getRevenueFromCursor(cursor);
            }
            return revenue;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteRevenue(Revenue revenue){
        try{
            open();
            db.delete(IRevenueConstantes.TABLE_REVENUE, IRevenueConstantes.COL_ID
            + " = ?", new String[]{String.valueOf(revenue.getIdRevenue())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRevenue(Revenue revenue){
        try{
            open();
            ContentValues contentValues = getContentValuesRevenue(revenue);
            db.update(IRevenueConstantes.TABLE_REVENUE, contentValues, IRevenueConstantes.COL_ID
                    + " = ?", new String[] {String.valueOf(revenue.getIdRevenue())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private ContentValues getContentValuesRevenue(Revenue revenue) {
        String description = revenue.getDescription();
        double montant = revenue.getMontant();
        String type = revenue.getType();
        int frequence = revenue.getFrequence();
        String date = revenue.getDate().toString();
        int idCompte = revenue.getIdCompte();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IRevenueConstantes.COL_DESCRIPTION, description);
        contentValues.put(IRevenueConstantes.COL_MONTANT, montant);
        contentValues.put(IRevenueConstantes.COL_TYPE, type);
        contentValues.put(IRevenueConstantes.COL_FREQUENCE, frequence);
        contentValues.put(IRevenueConstantes.COL_DATE, date);
        contentValues.put(IRevenueConstantes.COL_IDCOMPTE, idCompte);

        return contentValues;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Revenue getRevenueFromCursor(Cursor cursor) {
        Revenue revenue = new Revenue();
        revenue.setIdRevenue(cursor.getInt(0));
        revenue.setDescription(cursor.getString(1));
        revenue.setMontant(cursor.getDouble(2));
        revenue.setType(cursor.getString(3));
        revenue.setFrequence(cursor.getInt(4));
        revenue.setDate(LocalDate.parse(cursor.getString(5)));
        revenue.setIdCompte(cursor.getInt(6));
        return revenue;
    }
}

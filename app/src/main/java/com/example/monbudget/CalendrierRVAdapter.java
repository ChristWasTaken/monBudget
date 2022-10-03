package com.example.monbudget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.DepenseFixe;

public class CalendrierRVAdapter extends RecyclerView.Adapter<CalendrierRVAdapter.MyViewHolder> {
    private Context context;
    List<DepenseFixe> listDepensesFixes;

    public CalendrierRVAdapter(Context context, List<DepenseFixe> listDepensesFixes){
        this.listDepensesFixes = listDepensesFixes;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendrierRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendrier_row, parent, false);
        return new CalendrierRVAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendrierRVAdapter.MyViewHolder holder, int position) {
        //Ajouter les valeurs aux objets dans le row
        holder.txtCalendarDescription.setText(listDepensesFixes.get(position).getDescription());
        holder.txtCalendarCategorie.setText(listDepensesFixes.get(position).getCategorie());
        holder.txtCalendarDate.setText(String.valueOf(listDepensesFixes.get(position).getDate()));
        holder.txtCalendarMontant.setText(String.format("%.2f $", listDepensesFixes.get(position).getMontant()));
        holder.checkBoxPaid.setChecked(listDepensesFixes.get(position).isPaye());
    }

    @Override
    public int getItemCount() {
        return listDepensesFixes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //Attributs du row
        TextView txtCalendarDescription, txtCalendarCategorie, txtCalendarDate, txtCalendarMontant;
        CheckBox checkBoxPaid;
        Context context;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            setWidgets();
            setListeners();
        }

        private void setWidgets(){
            txtCalendarDescription = itemView.findViewById(R.id.txtCalendarDescription);
            txtCalendarCategorie = itemView.findViewById(R.id.txtCalendarCategorie);
            txtCalendarDate = itemView.findViewById(R.id.txtCalendarDate);
            txtCalendarMontant = itemView.findViewById(R.id.txtCalendarMontant);
            checkBoxPaid = itemView.findViewById(R.id.checkBoxPaid);
        }

        private void setListeners(){
            checkBoxPaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("log", "onClick: " + getAdapterPosition());
                    ((CalendrierActivity) context).changerEtatPaye(getAdapterPosition());
                }
            });
        }
    }
}

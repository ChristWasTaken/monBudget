package com.example.monbudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.Compte;

public class ComptesRVAdapter extends RecyclerView.Adapter<ComptesRVAdapter.MyViewHolder> {
    private Context context;
    List<Compte> compteList;

    public ComptesRVAdapter(Context context, List<Compte> compteList){
        this.compteList = compteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ComptesRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate le layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comptes_row, parent, false);
        return new ComptesRVAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ComptesRVAdapter.MyViewHolder holder, int position) {
        //Ajouter les valeurs au views dans le comptes_row layout
        holder.lblDescription.setText(compteList.get(position).getDescription());
        holder.lblSolde.setText(String.valueOf(compteList.get(position).getSolde()));
    }

    @Override
    public int getItemCount() {
        //Le nombre de comptes
        return compteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Assigner des valeur au textView a l'interieur du comptes_row

        //Attribut du row
        TextView lblDescription, lblSolde;
        ImageView btnEdit, btnDelete;
        Context context;

        
        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            setWidget();
            setListeners();
        }

        private void setListeners() {
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ComptesActivity) context).openDialogueUpdateCompte(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ComptesActivity) context).openDialogueDeleteCompte(getAdapterPosition());
                }
            });
        }

        private void setWidget() {
            lblDescription = itemView.findViewById(R.id.lblDescriptionCompte);
            lblSolde = itemView.findViewById(R.id.lblSolde);
            btnDelete = itemView.findViewById(R.id.btnDeleteCompte);
            btnEdit = itemView.findViewById(R.id.btnEditCompte);
        }
    }
}

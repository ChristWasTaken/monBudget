package com.example.monbudget;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.Revenue;

public class RevenueRVAdapter extends RecyclerView.Adapter<RevenueRVAdapter.MyViewHolder> {
    private Context context;
    List<Revenue> listRevenues;

    public RevenueRVAdapter(Context context, List<Revenue> listRevenues){
        this.listRevenues = listRevenues;
        this.context = context;
    }

    @NonNull
    @Override
    public RevenueRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.revenue_row, parent, false);
        return new RevenueRVAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueRVAdapter.MyViewHolder holder, int position) {
        //Ajouter les valeurs aux objets dans le row
        holder.lblDescription.setText(listRevenues.get(position).getDescription());
        holder.lblMontant.setText(String.valueOf(listRevenues.get(position).getMontant()));
        holder.lblDate.setText(String.valueOf(listRevenues.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return listRevenues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //Attributs du row
        TextView lblDescription, lblDate, lblMontant;
        ImageView btnEdit, btnDelete;
        Context context;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            setWidgets();
            setListeners();
        }

        private void setListeners() {
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    ((RevenuesActivity) context).openDialogueUpdateRevenue(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    ((RevenuesActivity) context).openDialogueDeleteRevenue(getAdapterPosition());
                }
            });
        }

        private void setWidgets() {
            lblDescription = itemView.findViewById(R.id.lblDescriptionRevenue);
            lblDate = itemView.findViewById(R.id.lblDateRevenue);
            lblMontant = itemView.findViewById(R.id.lblMontantRevenue);
            btnDelete = itemView.findViewById(R.id.btnDeleteRevenue);
            btnEdit = itemView.findViewById(R.id.btnEditRevenue);
        }
    }
}

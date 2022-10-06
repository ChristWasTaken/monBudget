package com.example.monbudget;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.DepenseVariable;

public class DepenseVariableRVAdapter extends RecyclerView.Adapter<DepenseVariableRVAdapter.MyViewHolder> {
    private Context context;
    private List<DepenseVariable> listDepensesVariables;

    public DepenseVariableRVAdapter(Context context, List<DepenseVariable> listDepensesVariables){
        this.listDepensesVariables = listDepensesVariables;
        this.context = context;
    }

    @NonNull
    @Override
    public DepenseVariableRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.depenses_row, parent, false);
        return new DepenseVariableRVAdapter.MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DepenseVariableRVAdapter.MyViewHolder holder, int position) {
        holder.lblDescription.setText(listDepensesVariables.get(position).getDescription());
        holder.lblDate.setText(String.valueOf(listDepensesVariables.get(position).getDate()));
        holder.lblMontant.setText(String.valueOf(listDepensesVariables.get(position).getMontant()));
    }

    @Override
    public int getItemCount() {
        return listDepensesVariables.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView lblDescription, lblDate, lblMontant;
        ImageView imgEdit, imgDelete;
        Context context;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            setWidgets();
            setListeners();
            this.context = context;
        }

        private void setListeners() {
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    ((DepensesVariableActivity) context).openDialogueUpdateDepense(getAdapterPosition());
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    ((DepensesVariableActivity) context).openDialogueDeleteDepense(getAdapterPosition());
                }
            });

        }

        private void setWidgets() {
            lblDescription = itemView.findViewById(R.id.lblDescriptionDepense);
            lblDate = itemView.findViewById(R.id.lblDateDepense);
            lblMontant = itemView.findViewById(R.id.lblMontantDepende);
            imgEdit = itemView.findViewById(R.id.imgEditDepense);
            imgDelete = itemView.findViewById(R.id.imgDeleteDepences);
        }


    }
}

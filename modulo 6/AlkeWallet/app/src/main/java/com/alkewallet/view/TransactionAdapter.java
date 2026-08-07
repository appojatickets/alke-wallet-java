package com.alkewallet.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alkewallet.R;
import com.alkewallet.model.Transaction;

/**
 * Adapter del RecyclerView que muestra el historial de transacciones
 * (fecha, monto y descripción, tal como pide la consigna).
 * Es parte de la capa VIEW: solo pinta datos, sin lógica de negocio.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transacciones = new ArrayList<>();

    public void actualizarLista(List<Transaction> nuevaLista) {
        this.transacciones = nuevaLista != null ? nuevaLista : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction t = transacciones.get(position);

        boolean esDeposito = "DEPOSIT".equals(t.getType());
        String signo = esDeposito ? "+" : "-";
        String tipoTexto = esDeposito ? "Depósito" : "Retiro";

        holder.txtTipo.setText(tipoTexto);
        holder.txtDescripcion.setText(t.getDescription());
        holder.txtFecha.setText(t.getDate());
        holder.txtMonto.setText(String.format("%s$%,.2f", signo, t.getAmount()));
    }

    @Override
    public int getItemCount() {
        return transacciones.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView txtTipo;
        TextView txtDescripcion;
        TextView txtFecha;
        TextView txtMonto;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtMonto = itemView.findViewById(R.id.txtMonto);
        }
    }
}
package com.gabrielfernandes.desafio_trovata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Models.Produto;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    private List<Produto> produtos;

    public ListaAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }


    static class ListaViewHolder extends RecyclerView.ViewHolder {
        private TextView produtoTexto;

        TextView txtProduto = itemView.findViewById(R.id.txtProduto);
        TextView txtCodigo = itemView.findViewById(R.id.txtCodigo);
        ImageView imgProduto = itemView.findViewById(R.id.imgProduto);


        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        Produto nome = produtos.get(position);
        holder.txtProduto.setText(nome.getDescricao());

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

}


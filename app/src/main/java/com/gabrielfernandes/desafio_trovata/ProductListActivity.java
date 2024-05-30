package com.gabrielfernandes.desafio_trovata;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DAO.ProdutoDAO;
import Models.Produto;

public class ProductListActivity extends AppCompatActivity {

    TextView txtNomeEmpresa;
    RecyclerView rvProdutos;

    ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        produtoDAO = new ProdutoDAO(this);
        Bundle bundle = getIntent().getExtras();
        List<Produto> produtos = produtoDAO.getAllProdutos(bundle.getInt("ID"));
        txtNomeEmpresa = findViewById(R.id.txtNomeEmpresa);
        rvProdutos = findViewById(R.id.rvProdutos);
        ListaAdapter adapter = new ListaAdapter(produtos);
        rvProdutos.setAdapter(adapter);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        if (bundle != null){
            txtNomeEmpresa.setText(bundle.getString("Nome"));
        }


    }
}

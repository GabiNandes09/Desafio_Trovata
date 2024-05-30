package com.gabrielfernandes.desafio_trovata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DAO.ProdutoDAO;
import Database.DatabaseHelper;
import Models.Empresa;
import Models.Produto;

public class ProductListActivity extends AppCompatActivity {

    TextView txtNomeEmpresa;
    RecyclerView rvProdutos;
    Spinner spFiltro;

    ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        spFiltro = findViewById(R.id.spFiltro);
        txtNomeEmpresa = findViewById(R.id.txtNomeEmpresa);
        rvProdutos = findViewById(R.id.rvProdutos);

        String[] filtros = {"A - Z", "Z - A", "Código"};
        ArrayAdapter<String> adapterFiltro = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, filtros);
        adapterFiltro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFiltro.setAdapter(adapterFiltro);

        final String[] ordem = {DatabaseHelper.COLUMN_PRODUTO_DESCRICAO + " ASC"};

        produtoDAO = new ProdutoDAO(this);
        Bundle bundle = getIntent().getExtras();
        final List<Produto>[] produtos = new List[]{produtoDAO.getAllProdutos(bundle.getInt("ID"), ordem[0])};

        ListaAdapter adapter = new ListaAdapter(produtos[0]);

        rvProdutos.setAdapter(adapter);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));

        if (bundle != null){
            txtNomeEmpresa.setText(bundle.getString("Nome"));
        }
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spFiltro.getSelectedItem().toString()){
                    case "A - Z":
                        ordem[0] = DatabaseHelper.COLUMN_PRODUTO_DESCRICAO + " ASC";
                        break;
                    case "Z - A":
                        ordem[0] = DatabaseHelper.COLUMN_PRODUTO_DESCRICAO + " DESC";
                        break;
                    case "Código":
                        ordem[0] = DatabaseHelper.COLUMN_PRODUTO_PRODUTO + " ASC";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + spFiltro.getSelectedItem().toString());
                }
                produtos[0] = produtoDAO.getAllProdutos(bundle.getInt("ID"), ordem[0]);
                adapter.updateData(produtos[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ordem[0] = DatabaseHelper.COLUMN_PRODUTO_DESCRICAO + " ASC";
            }
        });
    }
}

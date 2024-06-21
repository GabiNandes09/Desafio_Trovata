package com.gabrielfernandes.desafio_trovata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import DAO.ProdutoDAO;
import Database.DatabaseHelper;
import Models.Produto;

public class ProductListActivity extends AppCompatActivity {

    TextView txtNomeEmpresa;
    RecyclerView rvProdutos;
    Spinner spFiltro;

    ProdutoDAO produtoDAO;
    ListaAdapter adapter;
    String[] ordem = {DatabaseHelper.COLUMN_PRODUTO_DESCRICAO + " ASC"};
    Button btnAdicionar;

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle bundle = getIntent().getExtras();
        new DatabaseTasks().execute(bundle.getInt("ID"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        spFiltro = findViewById(R.id.spFiltro);
        txtNomeEmpresa = findViewById(R.id.txtNomeEmpresa);
        rvProdutos = findViewById(R.id.rvProdutos);
        btnAdicionar = findViewById(R.id.btnAdicionar);


        String[] filtros = {"A - Z", "Z - A", "Código"};
        ArrayAdapter<String> adapterFiltro = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, filtros);
        adapterFiltro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFiltro.setAdapter(adapterFiltro);

        adapter = new ListaAdapter(new ArrayList<>());

        Bundle bundle = getIntent().getExtras();
        new DatabaseTasks().execute(bundle.getInt("ID"));
        if (bundle != null){
            txtNomeEmpresa.setText(bundle.getString("Nome"));
            new DatabaseTasks().execute(bundle.getInt("ID"));
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
                new DatabaseTasks().execute(bundle.getInt("ID"));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ordem[0] = DatabaseHelper.COLUMN_PRODUTO_DESCRICAO + " ASC";
                new DatabaseTasks().execute(bundle.getInt("ID"));
            }

        });
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this,
                        cadastrar_produto_activity_p1.class);
                intent.putExtra("ID", bundle.getInt("ID"));
                intent.putExtra("Nome", bundle.getString("Nome"));
                startActivity(intent);
            }
        });
    }

    class DatabaseTasks extends AsyncTask<Integer, Void, List<Produto>>{

        private ProgressDialog progressDialog;

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductListActivity.this);
            progressDialog.setMessage("Carregando produtos...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected List<Produto> doInBackground(Integer... params) {
            int empreaID = params[0];
            produtoDAO = new ProdutoDAO(ProductListActivity.this);
            return produtoDAO.getAllProdutos(empreaID, ordem[0]);
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
            super.onPostExecute(produtos);
            progressDialog.dismiss();
            adapter.updateData(produtos);
            rvProdutos.setAdapter(adapter);
            rvProdutos.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));
        }
    }
}

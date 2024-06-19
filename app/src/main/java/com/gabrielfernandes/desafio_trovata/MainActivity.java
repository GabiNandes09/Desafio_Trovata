package com.gabrielfernandes.desafio_trovata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;

import DAO.EmpresaDAO;
import Database.DatabaseHelper;
import Models.Empresa;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerEmpresas;
    private Button btnContinuar;
    private EmpresaDAO empresaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinnerEmpresas = findViewById(R.id.spinnerEmpresas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new DatabaseTask().execute();


        btnContinuar = findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                Empresa selectedEmpresa = (Empresa) spinnerEmpresas.getSelectedItem();
                intent.putExtra("ID", selectedEmpresa.getId());
                intent.putExtra("Nome", selectedEmpresa.getNomeFantasia());
                startActivity(intent);
            }
        });
    }
    class DatabaseTask extends AsyncTask<Void, Void, List<Empresa>> {

        private ProgressDialog progressDialog;

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Carregando empresas...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<Empresa> doInBackground(Void... voids) {

            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            try {
                databaseHelper.createDatabase();
                databaseHelper.openDatabase();
            } catch (IOException e) {
                throw new Error("Erro ao copiar database");
            }
            databaseHelper.close();
            empresaDAO = new EmpresaDAO(MainActivity.this);

            return empresaDAO.getAllEmpresas();
        }

        @Override
        protected void onPostExecute(List<Empresa> empresas) {
            progressDialog.dismiss();
            super.onPostExecute(empresas);
            ArrayAdapter<Empresa> adapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_spinner_item, empresas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEmpresas.setAdapter(adapter);
        }
    }

}

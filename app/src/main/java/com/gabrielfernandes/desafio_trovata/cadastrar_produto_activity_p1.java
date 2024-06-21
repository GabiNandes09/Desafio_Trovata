package com.gabrielfernandes.desafio_trovata;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import DAO.ProdutoDAO;
import Database.DatabaseHelper;

public class cadastrar_produto_activity_p1 extends AppCompatActivity {
    Button btnProximo;
    Button btnCancelar;
    EditText etxtApelido;
    TextView txtCodigoEmpresa;
    EditText etxtDecricao;
    Spinner spGrupo;
    Spinner spSituacao;
    ProdutoDAO produtoDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadatrar_produto_p1);

            btnProximo = findViewById(R.id.btnProximo);
            btnCancelar = findViewById(R.id.btnCancelar);
            txtCodigoEmpresa = findViewById(R.id.txtCodigoEmpresa);
            spGrupo = findViewById(R.id.spGrupo);
            spSituacao = findViewById(R.id.spSituacao);
            etxtApelido = findViewById(R.id.etxtApelido);
            etxtDecricao = findViewById(R.id.etxtDecricao);

            Bundle bundle = getIntent().getExtras();
            txtCodigoEmpresa.setText(bundle.getString("Nome"));

            new PuxarGrupos().execute();
            new Puxarsituacao().execute();

            btnProximo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((!String.valueOf(etxtApelido.getText()).isEmpty())&&(!String.valueOf(etxtDecricao).isEmpty())){
                        Intent intent = new Intent(cadastrar_produto_activity_p1.this,
                                cadastrar_produto_activity_p2.class);
                        intent.putExtra("Empresa", bundle.getInt("ID"));
                        intent.putExtra("Produto", Integer.parseInt(String.valueOf(etxtApelido.getText())));
                        intent.putExtra("Descricao", String.valueOf(etxtDecricao.getText()));
                        intent.putExtra("Grupo", Integer.parseInt(spGrupo.getSelectedItem().toString()));
                        intent.putExtra("Situacao", spSituacao.getSelectedItem().toString());
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(cadastrar_produto_activity_p1.this);
                        builder.setTitle("INFORMACOES FALTANDO");
                        builder.setMessage("Todos os campos devem ser preenchidos.");
                        builder.setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            });
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

    }

    class PuxarGrupos extends AsyncTask<Void, Void, List<Integer>>{
        private ProgressDialog progressDialog;
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(cadastrar_produto_activity_p1.this);
            progressDialog.setMessage("Carregando Grupos...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<Integer> doInBackground(Void... voids) {
            DatabaseHelper databaseHelper = new DatabaseHelper(cadastrar_produto_activity_p1.this);
            try {
                databaseHelper.createDatabase();
                databaseHelper.openDatabase();
            } catch (IOException e) {
                throw new Error("Erro ao copiar database");
            }
            databaseHelper.close();
            produtoDAO = new ProdutoDAO(cadastrar_produto_activity_p1.this);
            return produtoDAO.getAllGrupos();
        }

        @Override
        protected void onPostExecute(List<Integer> grupos) {
            progressDialog.dismiss();
            super.onPostExecute(grupos);
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(cadastrar_produto_activity_p1.this,
                    android.R.layout.simple_spinner_item, grupos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrupo.setAdapter(adapter);
        }
    }
    class Puxarsituacao extends AsyncTask<Void, Void, List<String>>{
        private ProgressDialog progressDialog;
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(cadastrar_produto_activity_p1.this);
            progressDialog.setMessage("Carregando Situacao...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            DatabaseHelper databaseHelper = new DatabaseHelper(cadastrar_produto_activity_p1.this);
            try {
                databaseHelper.createDatabase();
                databaseHelper.openDatabase();
            } catch (IOException e) {
                throw new Error("Erro ao copiar database");
            }
            databaseHelper.close();
            produtoDAO = new ProdutoDAO(cadastrar_produto_activity_p1.this);
            return produtoDAO.getAllSituacao();
        }

        @Override
        protected void onPostExecute(List<String> situacao) {
            progressDialog.dismiss();
            super.onPostExecute(situacao);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(cadastrar_produto_activity_p1.this,
                    android.R.layout.simple_spinner_item, situacao);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSituacao.setAdapter(adapter);
        }
    }
}
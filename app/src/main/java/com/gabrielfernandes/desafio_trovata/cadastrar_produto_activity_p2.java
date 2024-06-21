package com.gabrielfernandes.desafio_trovata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import DAO.ProdutoDAO;
import Models.Produto;

public class cadastrar_produto_activity_p2 extends AppCompatActivity {
    Button btnCancelar;
    Button btnCadastrar;
    EditText etxtApelido;
    EditText etxtSubGrupo;
    EditText etxtPeso;
    EditText etxtClasFiscal;
    EditText etxtCodigoBarras;
    EditText etxtColecao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_produto_p2);
        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        etxtApelido = findViewById(R.id.etxtApelido);
        etxtSubGrupo = findViewById(R.id.etxtSubGrupo);
        etxtPeso = findViewById(R.id.etxtPeso);
        etxtClasFiscal = findViewById(R.id.etxtClasFiscal);
        etxtCodigoBarras = findViewById(R.id.etxtCodigoBarras);
        etxtColecao = findViewById(R.id.etxtColecao);
        final ProgressDialog[] progressDialog = new ProgressDialog[1];
        Bundle bundle = getIntent().getExtras();




        btnCadastrar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Produto produto = new Produto(
                        bundle.getInt("Empresa"),
                        bundle.getInt("Produto"),
                        bundle.getString("Descricao"),
                        intOrNull(etxtApelido),
                        bundle.getInt("Grupo"),
                        intOrNull(etxtSubGrupo),
                        bundle.getString("Situacao"),
                        doubleOrNull(etxtPeso),
                        intOrNull(etxtClasFiscal),
                        intOrNull(etxtCodigoBarras),
                        intOrNull(etxtColecao), null
                );
                try {
                    produtoDAO.cadastrarProduto(produto);
                    progressDialog[0] = new ProgressDialog(cadastrar_produto_activity_p2.this);
                    progressDialog[0].setMessage("Empresa cadastrada com sucesso");
                    progressDialog[0].setCancelable(true);
                    progressDialog[0].show();
                } catch (Error e){
                    Log.i("Fatal", "Deu ruim");
                }

                Intent intent = new Intent(cadastrar_produto_activity_p2.this, ProductListActivity.class);
                intent.putExtra("ID", bundle.getInt("Empresa"));
                startActivity(intent);


            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private Integer intOrNull(EditText editText){
        return editText.getText().toString().isEmpty() ? null : Integer.parseInt(etxtApelido.getText().toString());
    }
    private Double doubleOrNull(EditText editText){
        return editText.getText().toString().isEmpty() ? null : Double.parseDouble(etxtApelido.getText().toString());
    }
}
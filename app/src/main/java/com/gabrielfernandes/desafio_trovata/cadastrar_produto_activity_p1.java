package com.gabrielfernandes.desafio_trovata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class cadastrar_produto_activity_p1 extends AppCompatActivity {
    Button btnProximo;
    Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadatrar_produto_p1);

            btnProximo = findViewById(R.id.btnProximo);
            btnCancelar = findViewById(R.id.btnCancelar);

            btnProximo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(cadastrar_produto_activity_p1.this,
                            cadastrar_produto_activity_p2.class);
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
}
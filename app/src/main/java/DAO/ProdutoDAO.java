package DAO;


import static Database.DatabaseHelper.TABLE_PRODUTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHelper;
import Models.Produto;

public class ProdutoDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ProdutoDAO (Context context){
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDatabase();
            dbHelper.openDatabase();
        } catch (SQLException | IOException e){
            throw new RuntimeException("Erro ao inicializar database", e);
        }
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    //CRIAR - CREATE
    public void cadatrarProduto (Produto produto){
        ContentValues values = new ContentValues();
        values.put("produto", produto.getProduto());
        values.put("descricao", produto.getDescricao());
        values.put("apelido", produto.getApelido());
        values.put("grupo", produto.getGrupo());
        values.put("subgrupo", produto.getSubgrupo());
        values.put("situacao", produto.getSituacao());
        values.put("peso", produto.getPeso());
        values.put("classificacao", produto.getClassificacao());
        values.put("codigoDeBarras", produto.getCodigoDeBarras());
        values.put("colecao", produto.getColecao());

        database.insert(TABLE_PRODUTO, null, values);
    }

    //LISTAR - READ
    public List<Produto> getAllProdutos(int id, String ordem){
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = database.query(TABLE_PRODUTO, null, DatabaseHelper.COLUMN_PRODUTO_EMPRESA +"=" + id,
                null, null, null, ordem);

        if (cursor.moveToFirst()){
            Log.i("NTESTE", "Entrou no if");
            do {
                Produto produto = new Produto();
                produto.setEmpresa(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_EMPRESA)));
                produto.setProduto(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_PRODUTO)));
                produto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_DESCRICAO)));
                produto.setApelido(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_APELIDO)));
                produto.setGrupo(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_GRUPO)));
                produto.setSubgrupo(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_SUBGRUPO)));
                produto.setSituacao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_SITUACAO)));
                produto.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_PESO)));
                produto.setClassificacao(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_CLASSIFICACAO)));
                produto.setCodigoDeBarras(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_CODIGOBARRAS)));
                produto.setColecao(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUTO_COLECAO)));
                produtos.add(produto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return produtos;
    }

    //ATUALIZAR - UPDATE
    public void atualizarProduto (Produto produto){

    }

    //DELETAR - DELETE
    public void deletarProduto (Produto produto){

    }
}

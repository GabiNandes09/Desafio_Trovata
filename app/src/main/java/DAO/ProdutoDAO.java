package DAO;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public List<Produto> getAllProdutos(String id){
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUTO, DatabaseHelper.COLUMN_PRODUTO_EMPRESAARRAY, id,
                null, null, null, null);

        if (cursor.moveToFirst()){
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

}

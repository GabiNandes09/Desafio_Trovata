package DAO;


import static Database.DatabaseHelper.*;

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
    public void cadastrarProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUTO_EMPRESA, produto.getEmpresa());
        values.put(COLUMN_PRODUTO_PRODUTO, produto.getProduto());
        values.put(COLUMN_PRODUTO_DESCRICAO, produto.getDescricao());
        values.put(COLUMN_PRODUTO_APELIDO, produto.getApelido());
        values.put(COLUMN_PRODUTO_GRUPO, produto.getGrupo());
        values.put(COLUMN_PRODUTO_SUBGRUPO, produto.getSubgrupo());
        values.put(COLUMN_PRODUTO_SITUACAO, produto.getSituacao());
        values.put(COLUMN_PRODUTO_PESO, produto.getPeso());
        values.put(COLUMN_PRODUTO_CLASSIFICACAO, produto.getClassificacao());
        values.put(COLUMN_PRODUTO_CODIGOBARRAS, produto.getCodigoDeBarras());
        values.put(COLUMN_PRODUTO_COLECAO, produto.getColecao());

        database.insert(TABLE_PRODUTO, null, values);
    }

    //LISTAR - READ
    public List<Produto> getAllProdutos(int id, String ordem){
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = database.query(TABLE_PRODUTO, null, COLUMN_PRODUTO_EMPRESA +"=" + id,
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

    public List<Integer> getAllGrupos(){
        List<Integer> grupos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_PRODUTO,
                    new String[]{COLUMN_PRODUTO_GRUPO},
                    null, null,
                    COLUMN_PRODUTO_GRUPO,
                    null,
                    COLUMN_PRODUTO_GRUPO + " ASC");
            if (cursor.moveToFirst()){
                do{
                    Log.i("Cursor", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUTO_GRUPO))));
                    grupos.add(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUTO_GRUPO)));
                } while ((cursor.moveToNext()));

            }
        } catch (Exception e){
            Log.i("Fatal", "Erro em puxar grupos");
        } finally {
            if (cursor!= null) {cursor.close();}
        }

        return grupos;
    }
    public List<String> getAllSituacao(){
        List <String> situacao = new ArrayList<>();

        try (Cursor cursor = database.query(true,
                TABLE_PRODUTO,
                new String[]{COLUMN_PRODUTO_SITUACAO},
                null,
                null,
                null,
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                do {
                    situacao.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUTO_SITUACAO)));
                    Log.i("Situacao", "Agora e: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUTO_SITUACAO)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("Fatal", "Erro ao puxar situacao: " + e);
        }
        return situacao;
    }
}

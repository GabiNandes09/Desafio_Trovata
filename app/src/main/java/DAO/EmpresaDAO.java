package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHelper;
import Models.Empresa;

public class EmpresaDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public EmpresaDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDatabase();
            dbHelper.openDatabase();
        } catch (SQLException | IOException e){
            throw new RuntimeException("Erro ao inicializar database", e);
        }
        database = dbHelper.getWritableDatabase();
    }
    public EmpresaDAO(){}

    public void close() {
        dbHelper.close();
    }

    //CADASTRAR - CREATE
    public void cadastrarEmpresa(Empresa empresa){}

    //LISTAR - READ
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresas = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EMPRESA, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()){
            do{
                Empresa empresa = new Empresa();
                empresa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                empresa.setNomeFantasia(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME_FANTASIA)));
                empresa.setRazaoSocial(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RAZAO_SOCIAL)));
                empresa.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENDERECO)));
                empresa.setBairro(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BAIRRO)));
                empresa.setCep(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CEP)));
                empresa.setCidade(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CIDADE)));
                empresa.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONE)));
                empresa.setFax(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FAX)));
                empresa.setCnpj(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CNPJ)));
                empresa.setIe(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IE)));
                empresas.add(empresa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return empresas;
    }
    //ATUALIZAR - UPDATE
    public void atualizarEmpresa(Empresa empresa){}
    //DELETAR - DELETE
    public void deletarEmpresa(Empresa empresa){}

}

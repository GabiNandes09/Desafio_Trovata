package Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Trovata.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_EMPRESA = "EMPRESA";
    public static final String COLUMN_ID = "EMPRESA";
    public static final String COLUMN_NOME_FANTASIA = "NOME_FANTASIA";
    public static final String COLUMN_RAZAO_SOCIAL = "RAZAO_SOCIAL";
    public static final String COLUMN_ENDERECO = "ENDERECO";
    public static final String COLUMN_BAIRRO = "BAIRRO";
    public static final String COLUMN_CEP = "CEP";
    public static final String COLUMN_CIDADE = "CIDADE";
    public static final String COLUMN_TELEFONE = "TELEFONE";
    public static final String COLUMN_FAX = "FAX";
    public static final String COLUMN_CNPJ = "CNPJ";
    public static final String COLUMN_IE = "IE";
    private static String DATABASE_PATH = "";
    private final Context mContext;

    private SQLiteDatabase mDataBase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        DATABASE_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Banco de dados ja existente
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //usado apenas quando há atualizações no banco
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (!dbExist){
            this.getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (IOException e){
                throw new Error("Erro ao copiar database");
            }
        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DATABASE_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e){
        }

        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DATABASE_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();;
        myOutput.close();
        myInput.close();
    }

    public  void openDatabase() throws SQLException{
        String myPath = DATABASE_PATH;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }
}

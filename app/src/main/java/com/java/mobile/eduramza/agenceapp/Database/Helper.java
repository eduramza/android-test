package com.java.mobile.eduramza.agenceapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.java.mobile.eduramza.agenceapp.model.Usuario;

import java.util.concurrent.CopyOnWriteArrayList;

public class Helper extends SQLiteOpenHelper {

    // Versão Banco
    private static final int VERSION = 1;

    // Banco de Dados
    private static final String DATABASE_NAME = "Agence.db";

    // Tabela
    private static final String ENTIDADE = "usuario";

    //Colunas
    private static final String COL_ID = "id";
    private static final String COL_USUARIO = "usuario";
    private static final String COL_SENHA = "senha";

    //Criando a tabela
    private String CREATE_TABLE = "CREATE TABLE " + ENTIDADE + " ("+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_USUARIO + " TEXT, " + COL_SENHA + " TEXT )";

    //Dropando tabela
    private String DROP_TABLE = "DROP TABLE IF EXISTS " + ENTIDADE;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL(DROP_TABLE);

        onCreate(db);
    }

    /*************** METODOS DML E DE CONSULTA *********************/
    public void adicionarUsuario(Usuario user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_USUARIO, user.getUsuario());
        values.put(COL_SENHA, user.getSenha());

        //Inserindo
        db.insert(ENTIDADE, null, values);
        db.close();

    }

    /**
     * This method to check user exist or not
     *
     * @param usuario
     * @param senha
     * @return true/false
     */
    public boolean verificarLogin(String usuario, String senha) {

        // array para o retorno
        String[] columns = {
                COL_ID
        };
        //abrindo o banco para leitura
        SQLiteDatabase db = this.getReadableDatabase();

        // Clausula Where
        String where = COL_USUARIO + " = ?" + " AND " + COL_SENHA + " = ?";

        // Dados digitados do usuário
        String[] argumentos = {usuario, senha};

        // Fazendo o filtro no banco de dados
        Cursor cursor = db.query(ENTIDADE,
                columns,
                where,
                argumentos,
                null,
                null,
                null);

        int contarRes = cursor.getCount();

        cursor.close();

        db.close();
        //Se tiver encontrado um ID
        if (contarRes > 0) {
            return true;
        }
        //caso não tenha encontrado
        return false;
    }
}

package com.example.apppassword.activity.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME_DB = "db_password";
    public static String TABLE_PASSWORD = "tbpassword";
    public static String TABLE_USER = "tbuser";

    public DBHelper(@Nullable Context context ) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Script para criar a tabela de password
        String sql = "CREATE TABLE IF NOT EXISTS "  + TABLE_PASSWORD
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " descricao TEXT NOT NULL,   " +
                " codigo TEXT NOT NULL, " +
                " senha  TEXT NOT NULL," +
                " idUser Long NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB","Sucesso ao criar a senha " );

        } catch(Exception e){
            Log.i("INFO DB","Erro ao criar a senha " + e.getMessage());
        }

        //Script para criar a tabela tbuser
        String sqlUser = "CREATE TABLE IF NOT EXISTS "  + TABLE_USER
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nmuser  TEXT NOT NULL, " +
                " dssenha TEXT NOT NULL, " +
                " dsemail TEXT NOT NULL, " +
                " nrcpf   VARCHAR(11) NOT NULL);";

        try {
            db.execSQL(sqlUser);
            Log.i("INFO DB","Sucesso ao criar o usuário " );

        } catch(Exception e){
            Log.i("INFO DB","Erro ao criar o usuário " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}

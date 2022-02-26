package com.example.apppassword.activity.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apppassword.activity.activity.MainActivity;
import com.example.apppassword.activity.model.Password;
import com.example.apppassword.activity.model.User;

import java.util.ArrayList;
import java.util.List;


public class PasswordDao implements IPasswordDao {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public PasswordDao(Context context) {
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Password password) {

        ContentValues cvDesPassword = new ContentValues();

        cvDesPassword.put("descricao",password.getDescricao());

        ContentValues cvCodigo = new ContentValues();
        cvDesPassword.put("codigo",password.getCodigo());

        ContentValues cvSenha = new ContentValues();
        cvDesPassword.put("senha",password.getSenha());

        ContentValues cvIdUser = new ContentValues();
        cvDesPassword.put("idUser",MainActivity.idUser);
        Long idTeste = MainActivity.idUser;
       // Long idTeste = MainActivity.idUser;

        try{
            escreve.insert(DBHelper.TABLE_PASSWORD,null,cvDesPassword);
            Log.i("INFO DB","Senha salva com sucesso ");
        }catch(Exception e){
            Log.i("INFO DB","Erro ao informar a senha " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Password password) {

        ContentValues cvDesPassword = new ContentValues();
        cvDesPassword.put("descricao",password.getDescricao());

        ContentValues cvCodigo = new ContentValues();
        cvDesPassword.put("codigo",password.getCodigo());

        ContentValues cvSenha = new ContentValues();
        cvDesPassword.put("senha",password.getSenha());

        try{
            String[] args = {password.getId().toString()};
            System.out.println(args);
            escreve.update(DBHelper.TABLE_PASSWORD,cvDesPassword,"id=?",args);
            Log.i("INFO DB","Senha atualizada com sucesso ");
        }catch(Exception e){
            Log.i("INFO DB","Erro ao atualizar a senha " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Password password) {

        ContentValues cvDesPassword = new ContentValues();

        try{
            String[] args = {password.getId().toString()};
            System.out.println(args);
            escreve.delete(DBHelper.TABLE_PASSWORD,"id=?",args);
            Log.i("INFO DB","Senha excluída com sucesso ");
        }catch(Exception e){
            Log.i("INFO DB","Erro ao excluir a senha " + e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    public List<Password> listar() {

        List<Password> passwords = new ArrayList<>();
        //String sql = "SELECT * FROM " + DBHelper.TABLE_PASSWORD + " ;";
        String sql = "SELECT * FROM " + DBHelper.TABLE_PASSWORD + " "  +
                     "WHERE idUser = " + MainActivity.idUser    +  " " +
                    "ORDER BY descricao" +";";
        Cursor c = le.rawQuery(sql,null);

        while(c.moveToNext()){
            Password password = new Password();
            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeDescricao = c.getString(c.getColumnIndex("descricao"));
            String nomeCodigo = c.getString(c.getColumnIndex("codigo"));
            String nomeSenha = c.getString(c.getColumnIndex("senha"));

            password.setId(id);
            password.setDescricao(nomeDescricao);
            password.setCodigo(nomeCodigo);
            password.setSenha(nomeSenha);
            passwords.add(password);
        }

        return passwords;

    }

}

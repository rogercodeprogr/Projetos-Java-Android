package com.example.apppassword.activity.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apppassword.activity.model.User;

import java.util.InputMismatchException;

public class UserDao implements IUserDao {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public UserDao(Context context) {
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    //@Override
    public boolean salvar(User user) {

        ContentValues cvDesUser = new ContentValues();
        cvDesUser.put("nmuser",user.getNome());

        ContentValues cvCodigo = new ContentValues();
        cvDesUser.put("dsemail",user.getEmail());

        ContentValues cvSenha = new ContentValues();
        cvDesUser.put("dssenha",user.getSenha());

        ContentValues cvCpf = new ContentValues();
        cvDesUser.put("nrcpf",user.getCpf());

        try{
            escreve.insert(DBHelper.TABLE_USER,null,cvDesUser);
            Log.i("INFO DB","Usuário salvo com sucesso ");
        }catch(Exception e){
            Log.i("INFO DB","Erro ao informar o usuário " + e.getMessage());
            return false;
        }
        return true;
    }

    //Função que verifica se encontrou usuário
    public Long foundUser(User user) {
        long id = 0;

        String nmUser = user.getNome();
        String dsSenha = user.getSenha();

        String sql = "SELECT * FROM " +
                DBHelper.TABLE_USER +
                " WHERE nmuser = '"  + nmUser + "'" ;

        Cursor c = le.rawQuery(sql,null);
        while(c.moveToNext()) {
            id = c.getLong(c.getColumnIndex("id"));
        }
        return id;

    }

    //Função que verifica se já existe usuário cadastrado
    public boolean UserExits(User user){

        String nmUser = user.getNome();
        String dsSenha = user.getSenha();
        String dsEmail = user.getEmail();
        String dsCpf   = user.getCpf();

        String sql = " SELECT * FROM " +
                DBHelper.TABLE_USER +
                " WHERE nmuser = '"  + nmUser + "' " +
                " OR   dssenha = '" + dsSenha + "' " +
                " OR   dsemail = '" + dsEmail + "' " +
                " OR   nrcpf   = '" + dsCpf   + "' ";

        Cursor c = le.rawQuery(sql,null);

        if(c.getCount() > 0){
            return true;
        }
        else
            return false;

    }

    //Validação de cpf
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

}





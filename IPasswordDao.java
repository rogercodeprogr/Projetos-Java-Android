package com.example.apppassword.activity.helper;

import com.example.apppassword.activity.model.Password;

import java.util.List;

public interface IPasswordDao {
    public boolean salvar(Password password);
    public boolean atualizar(Password password);
    public boolean deletar(Password password);
    public List<Password> listar();

}

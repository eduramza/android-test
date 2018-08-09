package com.java.mobile.eduramza.agenceapp.model;

import android.database.sqlite.SQLiteDatabase;

import com.java.mobile.eduramza.agenceapp.Database.Helper;

public class Usuario {
    private int id;
    private String usuario;
    private String senha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

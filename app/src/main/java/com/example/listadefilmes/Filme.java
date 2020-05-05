package com.example.listadefilmes;

import android.content.Context;

import java.io.Serializable;

public class Filme implements Serializable {

    private String titulo;
    private String anoLancamento;
    private String genero;
    private Pessoa diretor;
    private Pessoa protagonista;
    private int capaId;

    public Filme(Context context, String titulo, String anoLancamento, String genero, Pessoa diretor, Pessoa protagonista, String capaId) {
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.diretor = diretor;
        this.protagonista = protagonista;
        this.capaId = context.getResources().getIdentifier(capaId,"drawable", context.getPackageName());
    }

    public Filme(Context context, String titulo, String anoLancamento, String genero, Pessoa diretor, Pessoa protagonista) {
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.diretor = diretor;
        this.protagonista = protagonista;
        this.capaId = context.getResources().getIdentifier("movie_placeholder", "drawable", context.getPackageName());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Pessoa getDiretor() {
        return diretor;
    }

    public void setDiretor(Pessoa diretor) {
        this.diretor = diretor;
    }

    public Pessoa getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(Pessoa protagonista) {
        this.protagonista = protagonista;
    }

    public int getCapaId() {
        return capaId;
    }
}

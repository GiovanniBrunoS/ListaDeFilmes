package com.example.listadefilmes;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Pessoa implements Parcelable {

    private String nome;
    private Date dataNascimento;
    private int fotoId;

    public Pessoa(Context context, String nome, Date dataNascimento, String fotoId) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.fotoId = context.getResources().getIdentifier(fotoId,"drawable", context.getPackageName());
    }

    public Pessoa(Context context, String nome, Date dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.fotoId = context.getResources().getIdentifier("portrait_placeholder","drawable", context.getPackageName());
    }

    protected Pessoa(Parcel in) {
        nome = in.readString();
    }

    public static final Creator<Pessoa> CREATOR = new Creator<Pessoa>() {
        @Override
        public Pessoa createFromParcel(Parcel in) {
            return new Pessoa(in);
        }

        @Override
        public Pessoa[] newArray(int size) {
            return new Pessoa[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getFotoId() {
        return fotoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeLong(dataNascimento.getTime());
    }
}


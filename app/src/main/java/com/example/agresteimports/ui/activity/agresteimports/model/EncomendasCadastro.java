package com.example.agresteimports.ui.activity.agresteimports.model;

import java.util.Date;

public class EncomendasCadastro {



    private Date dataCadastro;
    private String userId;
    private String codigoDeRastreio;
    private String nomeDoPacote;
    private float valorTotal;
    private float valorRecebido;


    public EncomendasCadastro(Date dataCadastro, String userId, String codigoDeRastreio, String nomeDoPacote,
                              float valorTotal, float valorRecebido){

        this.dataCadastro = dataCadastro;
        this.userId = userId;
        this.codigoDeRastreio = codigoDeRastreio;
        this.nomeDoPacote = nomeDoPacote;
        this.valorTotal = valorTotal;
        this.valorRecebido = valorRecebido;

    }
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCodigoDeRastreio() {
        return codigoDeRastreio;
    }

    public void setCodigoDeRastreio(String codigoDeRastreio) {
        this.codigoDeRastreio = codigoDeRastreio;
    }

    public String getNomeDoPacote() {
        return nomeDoPacote;
    }

    public void setNomeDoPacote(String nomeDoPacote) {
        this.nomeDoPacote = nomeDoPacote;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(float valorRecebido) {
        this.valorRecebido = valorRecebido;
    }
}

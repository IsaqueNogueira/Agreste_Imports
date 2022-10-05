package com.example.agresteimports.ui.activity.agresteimports.model;

import java.math.BigDecimal;

public class EncomendasCadastro {


    private String userId;
    private String codigoDeRastreio;
    private String nomeDoPacote;
    private float valorTotal;
    private float valorRecebido;

    public EncomendasCadastro(String userId, String codigoDeRastreio, String nomeDoPacote,
                      float valorTotal, float valorRecebido){

        this.userId = userId;
        this.codigoDeRastreio = codigoDeRastreio;
        this.nomeDoPacote = nomeDoPacote;
        this.valorTotal = valorTotal;
        this.valorRecebido = valorRecebido;

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

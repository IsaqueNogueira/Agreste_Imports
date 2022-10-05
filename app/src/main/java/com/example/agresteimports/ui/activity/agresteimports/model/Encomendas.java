package com.example.agresteimports.ui.activity.agresteimports.model;

import java.math.BigDecimal;

public class Encomendas {



    private String userId;
    private String codigoDeRastreio;
    private String nomeDoPacote;
    private BigDecimal valorTotal;
    private BigDecimal valorRecebido;

    public Encomendas(String userId, String codigoDeRastreio, String nomeDoPacote,
                      BigDecimal valorTotal, BigDecimal valorRecebido){

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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }
}

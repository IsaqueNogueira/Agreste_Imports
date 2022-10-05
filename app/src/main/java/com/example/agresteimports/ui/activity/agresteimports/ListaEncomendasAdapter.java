package com.example.agresteimports.ui.activity.agresteimports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agresteimports.R;
import com.example.agresteimports.ui.activity.agresteimports.model.Encomendas;

import java.util.List;

import util.MoedaUtil;

public class ListaEncomendasAdapter extends BaseAdapter {

    private final List<Encomendas> encomendas;
    private final Context context;

    public ListaEncomendasAdapter(List<Encomendas> encomendas, Context context) {
        this.encomendas = encomendas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return encomendas.size();
    }

    @Override
    public Encomendas getItem(int posicao) {
        return encomendas.get(posicao);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.encomendas_lista, parent, false);

        Encomendas encomenda = encomendas.get(posicao);
        mostrarNomeDaEncomenda(viewCriada, encomenda);
        mostrarValorTotal(viewCriada, encomenda);
        mostrarCodigoDeRastreio(viewCriada, encomenda);
        mostrarValorRecebido(viewCriada, encomenda);
        mostrarValorQuandoChegar(viewCriada, encomenda);

        return viewCriada;
    }

    private void mostrarNomeDaEncomenda(View viewCriada, Encomendas encomendas) {
        TextView nomeDaEncomenda = viewCriada.findViewById(R.id.encomendas_nome_da_encomenda);
        nomeDaEncomenda.setText(encomendas.getNomeDoPacote());
    }

    private void mostrarCodigoDeRastreio(View viewCriada, Encomendas encomendas) {
        TextView codigoDeRastreio = viewCriada.findViewById(R.id.encomendas_codigo_rastreio);
        codigoDeRastreio.setText(encomendas.getCodigoDeRastreio());
    }

    private void mostrarValorTotal(View viewCriada, Encomendas encomendas) {
        TextView valorTotal = viewCriada.findViewById(R.id.encomendas_valor_total);
        String moedaBrasileira = MoedaUtil.formataParaBrasileiro(encomendas.getValorTotal());
        valorTotal.setText(moedaBrasileira);

    }

    private void mostrarValorRecebido(View viewCriada, Encomendas encomendas) {
        TextView valorRecebido = viewCriada.findViewById(R.id.encomendas_valor_recebido);
        String moedaBrasileira = MoedaUtil.formataParaBrasileiro(encomendas.getValorRecebido());
        valorRecebido.setText(moedaBrasileira);
    }
    private void mostrarValorQuandoChegar(View viewCriada, Encomendas encomendas){
        TextView valorQuandoChegar = viewCriada.findViewById(R.id.encomendas_valor_quando_chegar);
        String moedaBrasileira = MoedaUtil.formataParaBrasileiro(encomendas.getValorTotal().subtract(encomendas.getValorRecebido()));
        valorQuandoChegar.setText(moedaBrasileira);
    }
}

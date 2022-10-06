package com.example.agresteimports.ui.activity.agresteimports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agresteimports.R;
import com.example.agresteimports.ui.activity.agresteimports.model.Encomendas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EncomendasActivity extends AppCompatActivity {
    private TextView nomeDoUsuario;
    private String usuarioId;
    private View menuListaEncomendas;
    private  ListView listaDeEncomendas;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  List<Encomendas> encomendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encomendas);
        getSupportActionBar().hide();
        iniciarCampos();
        clicouMenuListaEncomendas();
        configuraLista();


    }

    private void configuraLista() {
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Encomendas")
                .whereEqualTo("userId", usuarioId).orderBy("dataCadastro", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(task -> {
         if(task.isSuccessful()){
             encomendas = new ArrayList<Encomendas>();
             for (QueryDocumentSnapshot document : task.getResult()) {
                 String userId = document.getString("userId");
                 String codigoDeRastreio = document.getString("codigoDeRastreio");
                 String nomeDoPacote = document.getString("nomeDoPacote");
                 float valorRecebido = document.getLong("valorRecebido");
                 float valorTotal = document.getLong("valorTotal");
                encomendas.add(new Encomendas(userId,"Código de rastreio: "+codigoDeRastreio, nomeDoPacote, new BigDecimal(valorTotal), new BigDecimal(valorRecebido)));

             }


             listaDeEncomendas.setAdapter(new ListaEncomendasAdapter(encomendas, this));
         }
        });
    }

    private void clicouMenuListaEncomendas() {
        menuListaEncomendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EncomendasActivity.this, ListaEncomendasActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mostraNomeDoUsuario();

    }

    public void mostraNomeDoUsuario() {
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    String nome = documentSnapshot.getString("nome");
                    nomeDoUsuario.setText(nome);

                }
            }
        });

    }
    public void iniciarCampos(){
        nomeDoUsuario = findViewById(R.id.encomendas_nome_usuario);
        menuListaEncomendas = findViewById(R.id.encomendas_view_menu_nova_encomenda);
        listaDeEncomendas = findViewById(R.id.encomendas_textview);

    }
}
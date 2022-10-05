package com.example.agresteimports.ui.activity.agresteimports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agresteimports.R;
import com.example.agresteimports.ui.activity.agresteimports.model.Encomendas;
import com.example.agresteimports.ui.activity.agresteimports.model.EncomendasCadastro;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.math.BigDecimal;


public class ListaEncomendasActivity extends AppCompatActivity {

    private EditText codigoDeRastreio;
    private EditText nomeDoPacote;
    private EditText valorTotal;
    private EditText valorRecebido;
    private TextView nomeDoUsuario;
    private View menuEncomendas;
    private Button botaoCadastrarEncomenda;
    private ProgressBar loading;
    private String usuarioId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_encomendas_activity);
        getSupportActionBar().hide();
        inicializacaoDosCampos();
        clicouMenuEncomendas();
        clicouBotaoCadastrarEncomenda();



    }



    private void clicouBotaoCadastrarEncomenda() {
        botaoCadastrarEncomenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String codigoDoRastreio = codigoDeRastreio.getText().toString();
                String nomeDaEncomenda = nomeDoPacote.getText().toString();
                String valorTotalDaEncomenda = valorTotal.getText().toString();
                String valorRecebidoDaEncomenda = valorRecebido.getText().toString();
                if (!TextUtils.isEmpty(codigoDoRastreio) && !TextUtils.isEmpty(nomeDaEncomenda) &&
                        !TextUtils.isEmpty(valorTotalDaEncomenda) && !TextUtils.isEmpty(valorRecebidoDaEncomenda)) {
                    loading.setVisibility(View.VISIBLE);
                    EncomendasCadastro encomendas = new EncomendasCadastro(usuarioId, codigoDoRastreio, nomeDaEncomenda, Float.parseFloat(valorTotalDaEncomenda), Float.parseFloat(valorRecebidoDaEncomenda));
                    DocumentReference documentReference = db.collection("Encomendas").document();
                    documentReference.set(encomendas).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loading.setVisibility(View.INVISIBLE);
                            codigoDeRastreio.setText("");
                            nomeDoPacote.setText("");
                            valorTotal.setText("");
                            valorRecebido.setText("");

                            Snackbar snackbar = Snackbar.make(view, "Sucesso", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("db_error", "Erros so salvar os dados" + e.toString());
                        }
                    });
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void clicouMenuEncomendas() {
        menuEncomendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaEncomendasActivity.this, EncomendasActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
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

    private void inicializacaoDosCampos() {
        codigoDeRastreio = findViewById(R.id.lista_encomendas_edittext_codigo_rastreio);
        nomeDoPacote = findViewById(R.id.lista_encomendas_edittext_nome_pacote);
        valorTotal = findViewById(R.id.lista_encomendas_valor_total);
        valorRecebido = findViewById(R.id.lista_encomendas_valor_recebido);
        botaoCadastrarEncomenda = findViewById(R.id.lista_encomendas_botao_cadastrar_encomenda);
        nomeDoUsuario = findViewById(R.id.lista_encomendas_nome_usuario);
        menuEncomendas = findViewById(R.id.lista_encomendas_view_menu_encomendas);
        loading = findViewById(R.id.lista_encomendas_progessbar);
    }
}
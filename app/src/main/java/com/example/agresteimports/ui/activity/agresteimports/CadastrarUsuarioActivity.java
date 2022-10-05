package com.example.agresteimports.ui.activity.agresteimports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agresteimports.R;
import com.example.agresteimports.ui.activity.agresteimports.model.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    private EditText cadastrarNome, cadastrarEmail, cadastrarSenha, cadastrarRepitaSenha;
    private Button cadastrarUsuario, botaoLogin;
    private ProgressBar loading;
    private Usuarios usuarios;
    private String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        getSupportActionBar().hide();
        iniciaCampos();
        clicouBotaoCadastrarUsuario();
        clicouBotaoLogin();

    }

    private void salvarDadosDoUsuario() {
        String nome = cadastrarNome.getText().toString();
        usuarios.setNome(nome);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("nome", usuarios.getNome());
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erros so salvar os dados" +e.toString());
            }
        });
    }

    private void clicouBotaoLogin() {
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaLogin();
            }
        });
    }

    private void clicouBotaoCadastrarUsuario() {
        cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = cadastrarNome.getText().toString();
                String email = cadastrarEmail.getText().toString();
                String senha = cadastrarSenha.getText().toString();
                String repitaSenha = cadastrarRepitaSenha.getText().toString();
                if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(repitaSenha)) {
                    if (senha.equals(repitaSenha)) {
                        loading.setVisibility(View.VISIBLE);
                        cadastrarUsuario(view, nome, email, senha);

                    } else {
                        Snackbar snackbar = Snackbar.make(view, "As senhas não são iguais", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }

            }
        });
    }

    private void cadastrarUsuario(View view, String nome, String email, String senha) {
        usuarios = new Usuarios(nome, email, senha);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        usuarios.getEmail(), usuarios.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            salvarDadosDoUsuario();
                            Snackbar snackbar = Snackbar.make(view, "Cadastro realizado", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                            loading.setVisibility(View.INVISIBLE);
                             vaiParaListaEncomendas();

                        } else {
                            erroAoCadastrar(task, view);
                        }
                    }

                });
    }

    private void erroAoCadastrar(@NonNull Task<AuthResult> task, View view) {
        String erro;
        try {
            throw task.getException();

        } catch (FirebaseAuthWeakPasswordException e) {
            erro = "Digite uma senha com no mínimo 6 caracteres";
        } catch (FirebaseAuthUserCollisionException e) {
            erro = "E-mail já cadastrado";
        } catch (FirebaseAuthInvalidCredentialsException e) {
            erro = "E-mail inválido";
        } catch (Exception e) {
            erro = "Erro ao cadastrar usuário";
        }
        Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void vaiParaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void vaiParaListaEncomendas(){
        Intent intent = new Intent(this, ListaEncomendasActivity.class);
        startActivity(intent);
        finish();
    }

    private void iniciaCampos() {
        cadastrarNome = findViewById(R.id.cadastrar_campo_nome);
        cadastrarEmail = findViewById(R.id.cadastrar_campo_email);
        cadastrarSenha = findViewById(R.id.cadastrar_campo_senha);
        cadastrarRepitaSenha = findViewById(R.id.cadastrar_campo_repita_senha);
        cadastrarUsuario = findViewById(R.id.cadastrar_botao_cadastrar_usuario);
        botaoLogin = findViewById(R.id.cadastrar_botao_login);
        loading = findViewById(R.id.cadastrar_progessbar);
    }
}
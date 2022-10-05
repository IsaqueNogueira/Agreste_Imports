package com.example.agresteimports.ui.activity.agresteimports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agresteimports.R;
import com.example.agresteimports.ui.activity.agresteimports.model.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoLogin, botaoCadastrar;
    private ProgressBar loading;
    private Usuarios usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        iniciarCampos();
        clicouBotaoCadastrar();
        clicouBotaoLogin();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null) {
            vaiParaListaEncomendas();
        }
    }

    private void clicouBotaoLogin() {
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {
                    loading.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loading.setVisibility(View.INVISIBLE);
                                        vaiParaListaEncomendas();

                                    } else {
                                        String erro;
                                        try {
                                            throw task.getException();
                                        } catch (Exception e) {
                                            erro = "Erro a logar usuário";
                                        }
                                        Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.WHITE);
                                        snackbar.setTextColor(Color.BLACK);
                                        snackbar.show();
                                    }
                                }
                            });
                }else{
                    Snackbar snackbar = Snackbar.make(view, "Preencha todos os dados", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void vaiParaListaEncomendas() {
        Intent intent = new Intent(this, ListaEncomendasActivity.class);
        startActivity(intent);
        finish();
    }

    private void clicouBotaoCadastrar() {
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastrarUsuarioActivity.class);
                startActivity(intent);

            }
        });
    }


    private void iniciarCampos() {
        campoEmail = findViewById(R.id.login_campo_email);
        campoSenha = findViewById(R.id.login_campo_senha);
        botaoLogin = findViewById(R.id.login_botao_login);
        botaoCadastrar = findViewById(R.id.login_botao_cadastrar_usuario);
        loading = findViewById(R.id.login_progessbar);
    }
}
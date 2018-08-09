package com.java.mobile.eduramza.agenceapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.java.mobile.eduramza.agenceapp.Database.Helper;
import com.java.mobile.eduramza.agenceapp.R;
import com.java.mobile.eduramza.agenceapp.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    //ATRIBUTOS
    private EditText etUsuario, etSenha;
    private Button btLogin;
    private TextInputLayout tiUsuario, tiSenha;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INSTANCIANDO OS COMPONENTES
        etUsuario = findViewById(R.id.et_username);
        etSenha = findViewById(R.id.et_senha);
        tiUsuario = findViewById(R.id.ti_usuario);
        tiSenha = findViewById(R.id.ti_senha);
        btLogin = findViewById(R.id.bt_login);

        //Iniciando o banco
        helper = new Helper(LoginActivity.this);

    }

    /********************** METODOS *****************************/
    public void logar(View v){
        //Validando preenchimento
        if(validarCampos()){
            //Validando Login
            if (helper.verificarLogin(etUsuario.getText().toString().trim(), etSenha.getText().toString().trim())){
                abrirMain();

            } else{
                buildDialog();
            }
        }
    }

    private void abrirMain(){
        Bundle bundle = new Bundle();
        bundle.putString("user", etUsuario.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos(){

        if (etUsuario.getText().length() == 0 ||
                etSenha.getText().length() == 0){

            Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    private void buildDialog(){
        //Alerta avisando cadastro ou senha incorretos
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Erro Login!");
        alert.setMessage("Usuário não cadastrado ou senha Incorreta! \nDeseja cadastrar um novo?");
        alert.setCancelable(false);

        alert.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Cadastrando novo usuário
                novoUsuario();

            }
        }).setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();
    }

    private void novoUsuario(){
        //Gerando a gravação no banco de dados
        Usuario usuario = new Usuario();
        usuario.setSenha(etSenha.getText().toString());
        usuario.setUsuario(etUsuario.getText().toString());
        helper.adicionarUsuario(usuario);

        abrirMain();
    }
}

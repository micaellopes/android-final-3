package br.unipe.borracheiro.borracheiro;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.unipe.borracheiro.borracheiro.core.Constants;
import br.unipe.borracheiro.borracheiro.factory.SharedPreferencesActivity;
import br.unipe.borracheiro.borracheiro.model.LoginResult;
import br.unipe.borracheiro.borracheiro.model.Professional;
import br.unipe.borracheiro.borracheiro.model.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    EditText etSenha;
    EditText etEmail;
    Button btnEntrar;
    CheckBox checkConectado;
    private String result;
    Login login;
    public boolean conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        login = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etSenha = (EditText) findViewById(R.id.et_senha);
        etEmail = (EditText) findViewById(R.id.et_email);
        btnEntrar = (Button) findViewById(R.id.btn_entrar);
        checkConectado = (CheckBox) findViewById(R.id.check_conectado);

        if (SharedPreferencesActivity.get(Login.this, Constants.TOKEN, null) != null ) {
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }

        checkConnection();
        if(!conexao){
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onResume(){
        super.onResume();
        checkConnection();
        if(!conexao){
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }
    }

    /* Verifica Conexão com a Internet
     */
    public void checkConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {

            conexao = true;

        } else {

            conexao = false;

        }
    }

    public String md5Generator(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte vetor[] = messageDigest.digest(password.getBytes("UTF-8"));
        StringBuilder hashString = new StringBuilder();
        for (byte b : vetor) {
            hashString.append(String.format("%02X", 0xFF & b));
        }
        String passwordEncrypt = hashString.toString();
        return passwordEncrypt;
    }

    public String parseUserToJSON(){
        String email = etEmail.getText().toString();
        String password = etSenha.getText().toString();
        User usuario = new User();

        String passwordEncrypt = null;
        try {
            passwordEncrypt = md5Generator(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        usuario.setEmail(email);
        usuario.setPassword(passwordEncrypt);

        Gson gson = new Gson();
        String usuarioJSON = gson.toJson(usuario);

        Log.d("CORPO_REQUISIÇAO -> ", usuarioJSON);

        return usuarioJSON;
    }

    public void fazerLogin(View v){
        checkConnection();

        if(!conexao){
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        } else{
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            Request request = new Request.Builder()
                    .url(Constants.URL_LOGIN)
                    .post(RequestBody.create(JSON,parseUserToJSON()))
                    .build();

            client.newCall(request).enqueue(new Callback(){

                @Override
                public void onFailure(Call call, IOException e) {
                    String result = e.toString();
                    Log.i(Constants.LOG_TAG, result);
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    login.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                result = response.body().string();
                                Log.d("CORPO_RESPOSTA -> ", result);
                            } catch(IOException e) {
                                e.printStackTrace();
                            }

                            responseLogin();
                        }
                    });

                }
            });
        }
    }

    private void responseLogin(){
        Gson gson = new Gson();
        LoginResult json = gson.fromJson(this.result, LoginResult.class);

        if(json.getToken().equals("")){
            Toast.makeText(Login.this, "Login ou senha inválidos!", Toast.LENGTH_LONG).show();
        }else{
            if(checkConectado.isChecked()){
                SharedPreferencesActivity.set(Login.this, Constants.TOKEN, json.getToken());
                String savedToken = SharedPreferencesActivity.get(Login.this, Constants.TOKEN, "defaultValue");
                Log.d("TOKEN SALVO!", savedToken);
            }
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }

    }

};
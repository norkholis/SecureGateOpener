package com.example.norkholis.securegateopener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.norkholis.securegateopener.helper.APIUtil;
import com.example.norkholis.securegateopener.helper.BaseAPIService;
import com.example.norkholis.securegateopener.helper.SharedPrefManager;
import com.example.norkholis.securegateopener.model.DataUserLogin;
import com.example.norkholis.securegateopener.model.ListLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btn_toRegister, btn_login;
    EditText lgn_username, lgn_password;

    SharedPrefManager sharedPrefManager;
    BaseAPIService baseAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSpSudahLogin()){
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }

        btn_toRegister = (Button)findViewById(R.id.btn_toRegister);
        btn_login = (Button)findViewById(R.id.btn_login);
        lgn_username = (EditText)findViewById(R.id.lgn_username);
        lgn_password = (EditText)findViewById(R.id.lgn_password);


        baseAPIService = APIUtil.getAPIService();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });

        btn_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void requestLogin(){
        baseAPIService.loginRequest(lgn_username.getText().toString(), lgn_password.getText().toString()).enqueue(new Callback<ListLogin>() {
            @Override
            public void onResponse(Call<ListLogin> call, Response<ListLogin> response) {
                if (response.isSuccessful()){
                    List dataUsers = response.body().getData();
                    if (dataUsers != null){
                        DataUserLogin user = (DataUserLogin) dataUsers.get(0);
                        String nama = user.getNamaLengkap().toString();
                        String token = user.getToken();
                        int id = user.getId();

                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, token);
                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID, id);
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                        Intent i = new Intent(LoginActivity.this, DashboardActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal mendapat response", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

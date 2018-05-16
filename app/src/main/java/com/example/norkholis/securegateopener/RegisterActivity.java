package com.example.norkholis.securegateopener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.norkholis.securegateopener.helper.APIUtil;
import com.example.norkholis.securegateopener.helper.BaseAPIService;
import com.example.norkholis.securegateopener.model.DataRegistrasi;
import com.example.norkholis.securegateopener.model.ListRegistrasi;
import com.example.norkholis.securegateopener.model.MessageLogin;
import com.example.norkholis.securegateopener.model.MessageRegistrasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText inNama, inUsername, inPassword, inEmail, inAlamat, inNotelp;
    Button btn_register;

    String disabled_key = "no";
    String verifikasi_pengguna= "no";

    BaseAPIService baseAPIService;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");

        inNama = (EditText)findViewById(R.id.inNama);
        inUsername = (EditText)findViewById(R.id.inUsername);
        inPassword = (EditText)findViewById(R.id.inPassword);
        inEmail = (EditText)findViewById(R.id.inEmail);
        inAlamat = (EditText)findViewById(R.id.inAlamat);
        inNotelp = (EditText)findViewById(R.id.inNotelp);
        btn_register = (Button)findViewById(R.id.btn_register);

        baseAPIService = APIUtil.getAPIService();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRequest();
            }
        });


    }

    private void registerRequest(){
        baseAPIService.requestRegister(inNama.getText().toString(),
                inEmail.getText().toString(),
                inAlamat.getText().toString(),
                inNotelp.getText().toString(),
                inUsername.getText().toString(),
                inPassword.getText().toString(),
                verifikasi_pengguna, disabled_key).enqueue(new Callback<ListRegistrasi>() {
            @Override
            public void onResponse(Call<ListRegistrasi> call, Response<ListRegistrasi> response) {
                if (response.isSuccessful()){
                    DataRegistrasi dataRegistrasi = response.body().getData();

                    if (dataRegistrasi != null){
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Register gagal dilakukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListRegistrasi> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register gagal dilakukan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

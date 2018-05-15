package com.example.norkholis.securegateopener;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {
    private EditText noTelp, verifKode;
    private Button btnSendVerifKode, btnResendVerifKode, btnVerifKode;

    private String phoneVerifId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verifCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        noTelp = (EditText)findViewById(R.id.noTelp);
        verifKode = (EditText)findViewById(R.id.verifKode);
        btnSendVerifKode = (Button)findViewById(R.id.btnSendVerifKode);
        btnResendVerifKode = (Button)findViewById(R.id.btnResendVerifKode);
        btnVerifKode = (Button)findViewById(R.id.btnVerifKode);

        btnResendVerifKode.setEnabled(false);
        btnVerifKode.setEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSendVerifKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode(view);
            }
        });

        btnVerifKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(view);
            }
        });

        btnResendVerifKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode(view);
            }
        });
    }

    public void sendCode(View view){
        String mNoTelp = noTelp.getText().toString();
        setupCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mNoTelp,
                60,
                TimeUnit.SECONDS,
                this,
                verifCallbacks
        );
    }

    private void setupCallbacks(){
        verifCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(PhoneAuthActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                }else if(e instanceof FirebaseTooManyRequestsException){
                    Toast.makeText(PhoneAuthActivity.this, "Quota SMS untuk layanan ini sudah habis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                phoneVerifId = s;
                resendingToken = forceResendingToken;

                btnVerifKode.setEnabled(true);
                btnResendVerifKode.setEnabled(true);
                btnSendVerifKode.setEnabled(false);
            }
        };
    }

    public void verifyCode(View view){
        String getVerifKode = verifKode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerifId, getVerifKode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    Intent i = new Intent(PhoneAuthActivity.this, LoginActivity.class)
                            .putExtra("phoneNumber", user.getPhoneNumber().toString());
                    startActivity(i);
                    finish();
                }else{
                    if (task.getException()instanceof FirebaseAuthInvalidCredentialsException);
                }
            }
        });
    }

    public void resendCode(View view){
        String mNoTelp = noTelp.getText().toString();
        setupCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mNoTelp,
                60,
                TimeUnit.SECONDS,
                this,
                verifCallbacks,
                resendingToken
        );
    }
}

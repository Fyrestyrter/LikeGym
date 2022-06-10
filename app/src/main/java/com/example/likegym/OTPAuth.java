package com.example.likegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPAuth extends AppCompatActivity {

    EditText countryCode,phoneNumber, enterOTPField;
    Button sendOTPbtn,verifyOTP,resendOTP;
    String userPhoneNumber, verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseAuth fAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpauth);

        countryCode = findViewById(R.id.cCode);
        phoneNumber = findViewById(R.id.PhoneNumber);
        sendOTPbtn = findViewById(R.id.sendOTpbtn);

        enterOTPField = findViewById(R.id.enterOTPfield);
        verifyOTP = findViewById(R.id.verifybtn);
        resendOTP = findViewById(R.id.resendOTPbtn);
        resendOTP.setEnabled(false);

        fAuth = FirebaseAuth.getInstance();

        sendOTPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countryCode.getText().toString().isEmpty()){
                    countryCode.setError("Введите код страны");
                    return;
                }

                if(phoneNumber.getText().toString().isEmpty()){
                    phoneNumber.setError("Введите номер телефона");
                    return;
                }
                userPhoneNumber = "+"+countryCode.getText().toString()+phoneNumber.getText().toString();
                verifyPhoneNumber(userPhoneNumber);
                Toast.makeText(OTPAuth.this,userPhoneNumber,Toast.LENGTH_SHORT).show();
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumber(userPhoneNumber);
                resendOTP.setEnabled(false);
            }
        });

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the otp

                if(enterOTPField.getText().toString().isEmpty()){
                    enterOTPField.setError("Сначала введите код из смс");
                    return;
                }

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,enterOTPField.getText().toString());
                authenticateUser(credential);
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                authenticateUser(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OTPAuth.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                token = forceResendingToken;

                countryCode.setVisibility(View.GONE);
                phoneNumber.setVisibility(View.GONE);
                sendOTPbtn.setVisibility(View.GONE);

                enterOTPField.setVisibility(View.VISIBLE);
                verifyOTP.setVisibility(View.VISIBLE);
                resendOTP.setVisibility(View.VISIBLE);
                resendOTP.setEnabled(false);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOTP.setEnabled(true);
            }
        };


    }

    public void verifyPhoneNumber(String phoneNum){
        // send OTP

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(fAuth)
                .setActivity(this)
                        .setPhoneNumber(phoneNum)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                        .setCallbacks(callbacks)
                                                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public void authenticateUser(PhoneAuthCredential credential){
        fAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(OTPAuth.this, "Успешно", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OTPAuth.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),logacc.class));
            finish();
        }
    }
}
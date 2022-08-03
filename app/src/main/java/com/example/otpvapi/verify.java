package com.example.otpvapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class verify extends AppCompatActivity {

    private EditText edtOTP;
    private String otp_text;
    private Button verifyOTPBtn;
   // int randonnumber;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);

        otp = getIntent().getStringExtra("otp");
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP(otp);
            }
        });
    }

    public void verifyOTP(String randomnumber) {
        //Toast.makeText(verify.this,"Verify Button",Toast.LENGTH_LONG).show();
        otp_text = edtOTP.getText().toString().trim();
        if(otp_text.equals(String.valueOf(randomnumber)))
        {
            Toast.makeText(verify.this,"user login in successfully",Toast.LENGTH_LONG).show();
            finish();
            Intent mainactivity = new Intent(verify.this,login.class);
            startActivity(mainactivity);
        }
        else{
            Toast.makeText(verify.this,"Invalid OTP, Please Try Again",Toast.LENGTH_LONG).show();
        }
    }
}
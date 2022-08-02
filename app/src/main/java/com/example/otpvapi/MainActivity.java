package com.example.otpvapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private EditText edtPhone;
    private Button generateOTPBtn;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);


        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhone.getText().toString().trim().isEmpty())
                {
                    edtPhone.setError("Enter Phone No");
                }else{

                    int randomPin   =(int) (Math.random()*9000)+1000;
                     otp  = String.valueOf(randomPin);
                   // postDataUsingVolley(edtPhone.getText().toString(), otp);
                    sendOTP(otp);

                    Intent i = new Intent(MainActivity.this, verify.class);
                    i.putExtra("otp",otp.trim());
                    startActivity(i);
                    finish();


                }
            }
        });

    }

    private void sendOTP(String otp) {
        try {
            // Construct data
            String apiKey = "apikey=" + "hkvjTUuwqXgMHsZ1";
            //Random random = new Random();
            //randonnumber=random.nextInt(99999);
            String message = "&message=" + "Your OTP-One Time Password is"+otp + "to authenticate your login with" +edtPhone+ "Powered By mTalkz";
            String senderid = "&senderid=" + "MTAMOI";
            String numbers = "&numbers="+edtPhone +","+ otp;
            String format = "&format=" +"json";
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("http://msg.mtalkz.com/V2/http-api-post.php").openConnection();
            String data = apiKey + senderid + numbers + message + format;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
        }
    }

}
package com.example.otpvapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class MainActivity extends AppCompatActivity {

    private EditText edtPhone;
    private Button generateOTPBtn;
    String otp;
    //int number;

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
                }
                else{
                    int randomPin   = (int) (Math.random()*9000)+1000;
                     otp  = String.valueOf(randomPin);
                    sendingPostRequest(otp);
                    Intent i = new Intent(MainActivity.this, verify.class);
                    i.putExtra("otp",otp.trim());
                    startActivity(i);
                    finish();


                }
            }
        });

    }

    private void sendingPostRequest(String otp) {

        try{
           String url = "http://msg.mtalkz.com/V2/http-api-post.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();

            // Setting basic post request
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept","application/json");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("apikey","nPdi9M05nYZKEN6C");
            jsonParam.put("senderid","MTAMOI");
            jsonParam.put("number","edtPhone");
            jsonParam.put("OTP","otp");
            jsonParam.put("message","Your otp is here");
            jsonParam.put("format","json");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonParam.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("nSending 'POST' request to URL : " + url);
            System.out.println("Post Data : " + jsonParam);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            //printing result from response
            System.out.println(response.toString());
        }
        catch (Exception e){
            System.out.println("Error SMS" + e);
        }
    }

}
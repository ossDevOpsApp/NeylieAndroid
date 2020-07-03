package com.slt.devops.neylie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.slt.devops.neylie.R;
import com.slt.devops.neylie.activity.gpsupdate.GpsUpdateActivity;
import com.slt.devops.neylie.api.ApiClient;
import com.slt.devops.neylie.models.gpsupdate.LoginResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText editTextSid,editTextPassword;
    private TextView textViewVersion;
    private  String appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextSid = findViewById(R.id.editTextSid);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewVersion = findViewById(R.id.versionId);

        appVersion = getResources().getString(R.string.app_version);
        textViewVersion.setText("App by OSS-DevOps. SV"+ appVersion);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(haveNetworkConnection()){
            userLogin();
        }else {
            Toast.makeText(LoginActivity.this, "Please Check the Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void userLogin() {

        String sid = editTextSid.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (sid.isEmpty()) {
            editTextSid.setError("Service ID is required");
            editTextSid.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = ApiClient
                .getInstance().getApi().userLogin(sid, password,appVersion);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {

                    /*SharedPrefManager.getInstance(LoginActivity.this)
                            .saveUser(loginResponse.getUser());*/
                    SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("RTOM", loginResponse.getUser());
                    editor.putString("SID", sid);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, GpsUpdateActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        android.net.ConnectivityManager cm = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
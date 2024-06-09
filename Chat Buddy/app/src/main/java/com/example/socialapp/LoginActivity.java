package com.example.socialapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView toRegister;
    private Button buttonLogin;
    private EditText inputEmail;
    private EditText inputPw;
    private LoginData logindata;
    private final AppCompatActivity activity = LoginActivity.this;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_login);
        initViews();
        initListeners();
        logindata = new LoginData(activity);
    }

    private void initViews(){
        toRegister = findViewById(R.id.toRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        inputEmail = findViewById(R.id.inputEmail);
        inputPw = findViewById(R.id.inputPw);
    }
    private void initListeners(){
        buttonLogin.setOnClickListener(this::onClick);
        toRegister.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                if(verifyInputLogin()){//depois alterar isto
                    String lgEmail = inputEmail.getText().toString().trim();
                    String lgpw = inputPw.getText().toString().trim();
                    if(verifyInDB(lgEmail,lgpw)){//depois alterar isto
                       Person p1 = logindata.getPerson(lgEmail,lgpw);
                        SaveInstant.getInstance().setId(p1.getId());
                        SaveInstant.getInstance().setAge(p1.getAge());
                        SaveInstant.getInstance().setPhone(p1.getPhone());
                        SaveInstant.getInstance().setN(p1.getName());
                        SaveInstant.getInstance().setE(p1.getEmail());
                        SaveInstant.getInstance().setPwd(p1.getPassword());
                        Intent intent2 = new Intent(this, ProfileActivity.class);
                        startActivity(intent2);
                    }else{
                        Toast.makeText(context, "Wrong Email or Password.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.toRegister:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
    public boolean verifyInDB(String lgEmail,String lgpw){
        if(logindata.checkPerson(lgEmail, lgpw)){
            Toast.makeText(context, "Logged In Sucefully.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    public boolean verifyInputLogin(){
        boolean login, pw;
        login = pw = false;
        if(inputEmail.getText().toString().equals("")) login = true;
        if (inputPw.getText().toString().equals(""))   pw    = true;
        if(login && pw){
            Toast.makeText(context, "Login and password can't be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(login){
            Toast.makeText(context, "Login can't be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pw){
            Toast.makeText(context, "Password can't be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean atsign = false;
        if(!login){
            for (int i = 0; i < inputEmail.getText().toString().length(); i++){
                if(inputEmail.getText().toString().charAt(i) == '@') atsign = true;
            }
        }
        if(!atsign && !login && !pw){
            Toast.makeText(context, "Not a valid email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
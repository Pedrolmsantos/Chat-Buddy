package com.example.socialapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPw;
    private EditText txtAge;
    private EditText txtPhone;
    private Button register;
    private LoginData logindata;
    private Person user;
    private final AppCompatActivity activity = RegisterActivity.this;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews(){
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPw = findViewById(R.id.txtPw);
        register = findViewById(R.id.register);
        txtPhone = findViewById(R.id.txttele);
        txtAge = findViewById(R.id.txtage);
    }
    private void initListeners(){
        register.setOnClickListener(this::onClick);
    }
    private void initObjects(){
        logindata = new LoginData(activity);
        user = new Person();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                if(buttonRegister()){
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    public void verifyInputRegister(){
        boolean fsname, lsname, email, pw, age, phone;
        fsname = lsname = email = pw = age = phone = false;
        if(txtFirstName.getText().toString().equals("")) fsname = true;
        if(txtLastName.getText().toString().equals("")) lsname = true;
        if(txtEmail.getText().toString().equals("")) email = true;
        if (txtPw.getText().toString().equals(""))   pw    = true;
        if (txtAge.getText().toString().equals(""))   age    = true;
        if (txtPhone.getText().toString().equals(""))   phone    = true;
        if(fsname || lsname || email || pw || age || phone) Toast.makeText(getBaseContext(), "Fields can't be empty.", Toast.LENGTH_SHORT).show();


        boolean atsign = false;
        if(!email){
            for (int i = 0; i < txtEmail.getText().toString().length(); i++){
                if(txtEmail.getText().toString().charAt(i) == '@') atsign = true;
            }
        }
        if(!atsign && !email) Toast.makeText(getBaseContext(), "Not a valid email.", Toast.LENGTH_SHORT).show();
    }
    public boolean buttonRegister(){
        verifyInputRegister();
        if (!logindata.checkPerson(txtEmail.getText().toString().trim())) {
            user.setName(txtFirstName.getText().toString().trim().concat(" ").concat(txtLastName.getText().toString().trim()));
            user.setEmail(txtEmail.getText().toString().trim());
            user.setPassword(txtPw.getText().toString().trim());
            user.setAge(txtAge.getText().toString().trim());
            user.setPhone(txtPhone.getText().toString().trim());
            logindata.addPerson(user);

            //Person person = new Person(txtFirstName.getText().toString(), txtLastName.getText().toString(), txtEmail.getText().toString(), txtPw.getText().toString());
            Toast.makeText(getBaseContext(), "Register sucessfull.", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getBaseContext(), "Email already exists!.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

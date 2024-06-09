package com.example.socialapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {
    private Context context;
    private TextView Name;
    private TextView idade;
    private TextView email;
    private TextView telefone;
    private ImageView img;
    private Person user;
    private LoginData logindata;
    private final AppCompatActivity activity = ProfileActivity.this;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.profile);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                startActivity(intent);
            }
        });
        initObjects();
        Name = findViewById(R.id.name);
        idade = findViewById(R.id.textidade);
        email = findViewById(R.id.textemail);
        telefone = findViewById(R.id.textelefone);
        img = findViewById(R.id.imgU);
        String name = SaveInstant.getInstance().getN();
        Bitmap bmp = null;
            byte[] bte = ImageConversion.loadFile(context,SaveInstant.getInstance().getE());
            if(bte!=null){
                bmp = ImageConversion.getImage(bte);
                bmp=Bitmap.createScaledBitmap(bmp, pxFromDp(context,120),pxFromDp(context,120), true);
            }
        if(bmp!=null){
            img.setImageBitmap(bmp);
        }
        Name.setText(SaveInstant.getInstance().getN());
        idade.setText(SaveInstant.getInstance().getAge());
        email.setText(SaveInstant.getInstance().getE());
        telefone.setText(SaveInstant.getInstance().getPhone());

    }
    public static int pxFromDp(final Context context, final int dp) {
        return (int) ( dp * context.getResources().getDisplayMetrics().density);
    }
    private void initObjects(){
        logindata = new LoginData(activity);
        user = new Person();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

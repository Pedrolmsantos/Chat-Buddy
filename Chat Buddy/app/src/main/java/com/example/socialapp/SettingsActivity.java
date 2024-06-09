package com.example.socialapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {
    //public String filename = "imagem";
    Context context;
    private Button ChPN;
    private Button ChEA;
    private Button ChA;
    private Button ChPP;
    private TextView novoP;
    private TextView novoE;
    private TextView novoA;
    private Button btn;
    private LoginData logindata;
    private Person user;
    private final AppCompatActivity activity = SettingsActivity.this;
    Bitmap bmp;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initObjects();
        context = this;
        ChPN = findViewById(R.id.EditPhone);
        novoP = findViewById(R.id.editTextPhone);
        ChPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person p1 = new Person();
                p1.setId(SaveInstant.getInstance().getId());
                p1.setPhone(novoP.getText().toString().trim());
                p1.setPassword(SaveInstant.getInstance().getPwd());
                p1.setAge(SaveInstant.getInstance().getAge());
                p1.setEmail(SaveInstant.getInstance().getE());
                p1.setName(SaveInstant.getInstance().getN());
                logindata.updateDetails(p1,1);
                SaveInstant.getInstance().setPhone(novoP.getText().toString().trim());
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
            }
        });
        ChEA = findViewById(R.id.ChangeEmail);
        novoE = findViewById(R.id.editTextTextEmailAddress);
        ChEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person p1 = new Person();
                if(verify()) {
                    byte[] h = ImageConversion.loadFile(context,SaveInstant.getInstance().getE());
                    ImageConversion.writeToFile(context,novoE.getText().toString().trim(),h);
                    for(File f :context.getFilesDir().listFiles()){
                        if(f.getName().equals(SaveInstant.getInstance().getE())){
                            f.delete();
                        }
                    }
                    p1.setId(SaveInstant.getInstance().getId());
                    p1.setPhone(SaveInstant.getInstance().getPhone());
                    p1.setPassword(SaveInstant.getInstance().getPwd());
                    p1.setAge(SaveInstant.getInstance().getAge());
                    p1.setEmail(novoE.getText().toString().trim());
                    p1.setName(SaveInstant.getInstance().getN());
                    logindata.updateDetails(p1, 1);
                    SaveInstant.getInstance().setE(novoE.getText().toString().trim());
                    Intent intent = new Intent(context, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        ChA = findViewById(R.id.Age);
        novoA = findViewById(R.id.editage);
        ChA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person p1 = new Person();
                    p1.setId(SaveInstant.getInstance().getId());
                    p1.setPhone(SaveInstant.getInstance().getPhone());
                    p1.setPassword(SaveInstant.getInstance().getPwd());
                    p1.setAge(novoA.getText().toString().trim());
                    p1.setEmail(SaveInstant.getInstance().getE());
                    p1.setName(SaveInstant.getInstance().getN());
                    logindata.updateDetails(p1, 1);
                    SaveInstant.getInstance().setAge(novoA.getText().toString().trim());
                    Intent intent = new Intent(context, ProfileActivity.class);
                    startActivity(intent);
                }

        });
        ChPP = findViewById(R.id.changeProfilePicture);
        ChPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallary = new Intent();
                openGallary.setType("image/*");
                openGallary.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(openGallary, "Select Picture"), 1);
            }
        });
    }
    private void initObjects(){
        logindata = new LoginData(activity);
        user = new Person();
    }
    private boolean verify(){
    boolean atsign = false;
        for (int i = 0; i < novoE.getText().toString().length(); i++){
            if(novoE.getText().toString().charAt(i) == '@') atsign = true;
        }
        if(!atsign){
        Toast.makeText(context, "Not a valid email.", Toast.LENGTH_SHORT).show();
        return false;
    }
        return true;
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {
                bmp = (Bitmap) data.getExtras().get("data");
                SaveInstant.getInstance().setImage(bmp);
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
            }
        }
        else {
            Uri selectedImage = data.getData();
            assert selectedImage != null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                SaveInstant.getInstance().setImage(bmp);
                ImageConversion.writeToFile(context,SaveInstant.getInstance().getE(),ImageConversion.getBytes(bmp));
            } catch (FileNotFoundException e) {
                Log.e("Result", "Image not found!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(context, ProfileActivity.class);
            startActivity(intent);
        }

    }

}

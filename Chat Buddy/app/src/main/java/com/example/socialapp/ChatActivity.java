package com.example.socialapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private Context context;

    private ListView listMainChat;
    private EditText edCreateMessage;
    private AppCompatImageButton btnSendMessage;
    private ArrayAdapter<String> adapterMainChat;
    private final int LOCATION_PERMISSION_REQUEST = 101;
    private final int SELECT_DEVICE = 102;
    public static final int MESSAGE_STATE_CHANGED = 0;
    public static final int MESSAGE_READ = 1;
    public static final int MESSAGE_WRITE = 2;
    public static final int MESSAGE_DEVICE_NAME = 3;
    public static final int MESSAGE_TOAST = 4;
    public static final String DEVICE_NAME = "deviceName";
    public static final String str = "toast";


    private void setState(CharSequence subTitle) {
        getSupportActionBar().setSubtitle(subTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        context = this;
        init();
        adapterMainChat.add("Assistance: Hi There! What can i help you with?");
    }
    private void init() {
        listMainChat = findViewById(R.id.list_msg);
        edCreateMessage = findViewById(R.id.ed_enter_message);
        btnSendMessage = findViewById(R.id.btn_send_msg);

        adapterMainChat = new ArrayAdapter<String>(context, R.layout.message_layout);
        listMainChat.setAdapter(adapterMainChat);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new Thread(messages).start();
                String message = edCreateMessage.getText().toString();
                if (!message.isEmpty()) {
                    edCreateMessage.setText("");
                    adapterMainChat.add("Eu: " + message);
                    try {
                        new SendMessageTask(ChatActivity.this, adapterMainChat).execute(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  chatUtils.write(message.getBytes());
                    // chatUtils.write("message.getBytes()".getBytes());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.UK);
        return dateFormat.format(new Date());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

class SendMessageTask extends AsyncTask<String, String, String> {

    Context context;
    ArrayAdapter<String> adapterMainChat;

    public SendMessageTask(Context context, ArrayAdapter<String> adapterMainChat){
        this.context = context;
        this.adapterMainChat = adapterMainChat;
    }
    private static final String API_URL = "http://172.20.10.8:5000/";

    protected String doInBackground(String... messages) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Set up the request with the message as a JSON object
            String requestBody = "{\"message\": \"" + messages[0]+ "\"}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    input = requestBody.getBytes(StandardCharsets.UTF_8);
                }
                os.write(input, 0, input.length);
            }

            // Get the response
            StringBuilder response = new StringBuilder();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
            }

            // Print the response text
            System.out.println(response.toString());
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    protected void onPostExecute(String res) {
        adapterMainChat.add("Assistance: " + res);
    }
}



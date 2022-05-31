package com.example.blooddonar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SendEmailActivity extends AppCompatActivity {

    private TextInputEditText etTo, etSubject,etMessage;
    private Button button_back, button_send;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        etTo = findViewById(R.id.etTo);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);
        button_back = findViewById(R.id.button_back);
        button_send = findViewById(R.id.button_send);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendEmailActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail(){
        String senderList = etTo.getText().toString();
        String[] senders = senderList.split(",");

        String subject = etSubject.getText().toString();
        String message = etMessage.getText().toString();

        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.putExtra(Intent.EXTRA_EMAIL, senders);
        intent2.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent2.putExtra(Intent.EXTRA_TEXT, message);

        intent2.setType("message/rfc822");
        startActivity(Intent.createChooser(intent2, "Choose email client"));
    }
}
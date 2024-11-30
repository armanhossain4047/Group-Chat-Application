package com.example.numanenterprize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class PrintActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_print);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
try{
    TextInputEditText ipEditText = findViewById(R.id.ipeditText);
    TextInputEditText portEditText = findViewById(R.id.porteditText);

    ImageView button = findViewById(R.id.signin_id);

    button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Chat.IPAddress = ipEditText.getText().toString();
            Chat.PortNumber = portEditText.getText().toString();
            Intent intent = new Intent(PrintActivity.this,Chat.class);
            startActivity(intent);
            finish();
        }
    });
}catch (Exception e){
    Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
}
    }
}
package com.example.numanenterprize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Sign_In_Activity extends Group_Chat_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
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
            Intent intent = new Intent(Sign_In_Activity.this,Chat.class);
            startActivity(intent);
            finish();
        }
    });
}catch (Exception e){
    Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
}
    }
}
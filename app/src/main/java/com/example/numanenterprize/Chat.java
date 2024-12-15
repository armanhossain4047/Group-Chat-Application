package com.example.numanenterprize;

 import static android.view.View.*;

 import android.content.Intent;
 import android.icu.text.CaseMap;
 import android.os.Build;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.RelativeLayout;
 import android.widget.Toast;

 import androidx.activity.EdgeToEdge;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.graphics.Insets;
 import androidx.core.view.ViewCompat;
 import androidx.core.view.WindowInsetsCompat;
 import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import java.io.BufferedReader;
 import java.io.DataInputStream;
 import java.io.DataOutputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.time.LocalTime;
 import java.time.format.DateTimeFormatter;
 import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {
    public static List<Socket> clientList = new ArrayList<>();

    public static String IPAddress = "192.168.0.104", PortNumber = "1122";
    private DataOutputStream write;
    private DataInputStream read;
    private Socket client;
    private boolean isConnected = false; // To track the connection status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        // Ensure proper padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> titles = new ArrayList<>();
        List<String> subtitles = new ArrayList<>();
        List<Boolean> isSentByMe = new ArrayList<>();

        MyAdapter adapter = new MyAdapter(titles, subtitles, isSentByMe);
        recyclerView.setAdapter(adapter);

        // Initialize UI components
        ImageButton send = findViewById(R.id.sendButton);
        EditText text = findViewById(R.id.messageInput);


        // Establish connection with the server in a background thread
        new Thread(() -> {
            try {
                client = new Socket(IPAddress, Integer.parseInt(PortNumber));
                write = new DataOutputStream(client.getOutputStream());
                read = new DataInputStream(client.getInputStream());
                isConnected = true; // Connection established

                runOnUiThread(() -> Toast.makeText(Chat.this, "Connected to Server!", Toast.LENGTH_SHORT).show());

                // Start a thread to listen for incoming messages from the server
               // startListeningForMessages(adapter, titles, subtitles, recyclerView);

                new Thread(() -> {
                    Socket ThisClient = client;
                    try {
                        while (isConnected) {
                            // Read the incoming message
                            String serverMessage = read.readUTF();

                            // Update the RecyclerView with the received message
                            runOnUiThread(() -> {
                                isSentByMe.add(false);
                                titles.add(CurrentTime()); // Add sender as "Server"
                                subtitles.add(serverMessage); // Add the message to the data list
                                adapter.notifyItemInserted(subtitles.size() - 1); // Notify adapter of the new item
                                recyclerView.scrollToPosition(subtitles.size() - 1); // Scroll to the latest message
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(Chat.this, "Connection Lost!", Toast.LENGTH_SHORT).show());
                        closeConnection();
                    }
                }).start();

            } catch (UnknownHostException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Chat.this, "Unknown Host!", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Chat.this, "Connection Error!", Toast.LENGTH_SHORT).show());
            }
        }).start();

        // Handle the Send Button Click
        send.setOnClickListener(v -> {
            String message = text.getText().toString();
            if (!message.isEmpty() && isConnected) {
                new Thread(() -> {
                    try {
                        // Send the message to the server
                        write.writeUTF(message);
                        write.flush();

                        // Update the RecyclerView
                        runOnUiThread(() -> {
                            isSentByMe.add(true);
                            titles.add(CurrentTime());
                            subtitles.add(message);
                            adapter.notifyItemInserted(subtitles.size() - 1); // Notify adapter of the new item
                            recyclerView.scrollToPosition(subtitles.size() - 1); // Scroll to the latest message
                            text.setText(""); // Clear the input field
                        });

                        // Close connection if "bye" is sent
                        if (message.equalsIgnoreCase("bye")) {
                            closeConnection();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(Chat.this, "Failed to send message!", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }
        });
    }

    // Method to listen for incoming messages from the server
    private void startListeningForMessages(MyAdapter adapter, List<String> titles, List<String> subtitles, RecyclerView recyclerView) {

    }

    // Method to close the connection
    private void closeConnection() {
        try {
            if (write != null) write.close();
            if (read != null) read.close();
            if (client != null) client.close();
            isConnected = false;
            runOnUiThread(() -> Toast.makeText(Chat.this, "Connection Closed!", Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(Chat.this, "Error Closing Connection!", Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConnection(); // Ensure connection is closed when the activity is destroyed
    }

    public String CurrentTime() {
        LocalTime currentTime = null;
        DateTimeFormatter formatter = null;
        String formattedTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentTime = LocalTime.now();
            formatter = DateTimeFormatter.ofPattern("hh:mm a");
            formattedTime = currentTime.format(formatter);
        }
        return formattedTime;
    }

}

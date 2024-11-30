package com.example.numanenterprize;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NewDebice extends Thread{
    String ip;
    int port;

    public NewDebice(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket client = new Socket(ip, port);
            Group_Chat_Activity.connection=true;
            DataOutputStream write = new DataOutputStream(new DataOutputStream(client.getOutputStream()));
            DataInputStream read = new DataInputStream(new DataInputStream(client.getInputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        } catch (IOException e) {
        }
    }
}

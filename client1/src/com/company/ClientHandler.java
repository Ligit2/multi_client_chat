package com.company;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    volatile String message;
    private String username;
    private final Socket s;

    public ClientHandler(Socket s, String username) {
        this.s = s;
        this.username = username;
    }
    public void readMessage(){

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.s.getInputStream());
            message =  (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public synchronized void answerMessage(){

        BufferedReader bufferedReader;
        try (InputStream inputStream = s.getInputStream()) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            String input = bufferedReader.readLine();
            objectOutputStream.writeObject(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
     //  readMessage();
       answerMessage();
    }
}

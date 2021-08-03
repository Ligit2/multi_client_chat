package com.company;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Handler implements Runnable{
    private Socket socket;
    List<Handler> list;
    private DataInputStream objectInputStream;
    private DataOutputStream objectOutputStream;
    private SimpleDateFormat date = new SimpleDateFormat( "HH:mm:ss");
    private boolean flag=true;
    private String username;
    Handler(Socket socket, List<Handler> list){
        this.socket = socket;
        this.list = list;
        this.list.add(this);
        try {
            objectInputStream = new DataInputStream(socket.getInputStream());
            objectOutputStream = new DataOutputStream(socket.getOutputStream());
            String name = objectInputStream.readUTF();
            username = name;
            System.out.println("[" + date.format(new Date()) + "] " + username + ": Join this chat");
            send("[" + date.format(new Date()) + "] "+username+": Join this chat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void send(String s) {
        list.forEach((handler)-> {
            try {
                if(handler.socket!=socket) {
                    handler.objectOutputStream.writeUTF(s);
                    handler.objectOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void receive(){
        while (flag){
            try {
                String answer = (String)objectInputStream.readUTF();

                if(!answer.equalsIgnoreCase("bye")){
                    System.out.println("[" + date.format(new Date()) + "] "+username + ": " + answer);
                    send("[" + date.format(new Date()) + "]"+username +": " + answer);
                }
                else{
                    System.out.println("[" + date.format(new Date()) + "] "+username + ": Leave this chat");
                    send("[" + date.format(new Date()) + "] "+username + ": Leave this chat");
                    objectOutputStream.flush();
                    list.remove(this);
                    flag=false;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void run() {
        while(flag){
            receive();
        }

    }
}

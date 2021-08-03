package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        Client1 client1 = new Client1();
        client1.startUp();
    }
}

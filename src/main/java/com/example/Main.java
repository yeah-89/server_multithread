package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.Port;


public class Main {
    private static final int  PORT=3000;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public static void main(String[] args){
        
        try (ServerSocket ss = new ServerSocket(PORT)){
            while(true){
            Socket s = ss.accept();
            System.out.println("connessione riuscita");
            threadPool.execute(new SocketHandler(s));

        }
            
        } catch (IOException e) {
            System.err.println("Errore nel server");
        }finally{
            threadPool.shutdown();
        }
        
    }
}
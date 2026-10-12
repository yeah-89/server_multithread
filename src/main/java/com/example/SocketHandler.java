package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private Socket s;

    public SocketHandler(Socket so) {
        s = so;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            Socket autoCloseableSocket = s
        ) {
            boolean var = true;
            String str = "";
            Double num1 = 0.0;
            Double num2 = 0.0;

            while (var) {
                boolean datiValidi = false;

                do {
                    try {
                        str = in.readLine();
                        if (str == null) {
                            var = false;
                            break;
                        }

                        if (str.equals("exit")) {
                            datiValidi = true;
                            break; 
                        }

                        if (str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*")) {
                            num1 = Double.parseDouble(in.readLine());
                            num2 = Double.parseDouble(in.readLine());
                            datiValidi = true;
                        } else {
                            out.println("opertatore non valido");
                        }

                    } catch (NumberFormatException e) {
                        out.println("Errore: inserimento numeri sbagliato");
                        datiValidi = false;
                    }
                } while (!datiValidi);

                if (!var) {
                    break;
                }
                    
                if (num1 == 0 && num2 == 0 && !str.equals("exit")) {
                    break;
                }
                
                double ris = 0;
                boolean inviaRisposta = true;

                switch (str) {
                    case "+":
                        ris = num1 + num2;
                        break;
                    case "-":
                        ris = num1 - num2;
                        break;
                    case "*":
                        ris = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            out.println("ERR-0:divided_by_zero");
                            inviaRisposta = false;
                            break;
                        } else {
                            ris = num1 / num2;
                        }
                        break;
                    case "exit":
                        System.out.println("Disconnesso");
                        var = false;
                        inviaRisposta = false;
                        break;
                    default:
                        inviaRisposta = false;
                        break;
                }
                
                if (inviaRisposta) {
                    out.println("Risposta: " + ris);
                }
            } 
        } catch (IOException e) {
            System.out.println("Errore di I/O");
        }
    }
}




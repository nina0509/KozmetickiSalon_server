/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.niti.ObradaKlijentskihZahteva;


/**
 *
 * @author ninic
 */
public class Server extends Thread {

    boolean kraj = false;
    ServerSocket serverSoket;
    List<ObradaKlijentskihZahteva> klijenti;
  
    public Server() {
        klijenti = new ArrayList<>();
    }

    @Override
    public void run() {

        try {
            serverSoket = new ServerSocket(9000);
            while (!kraj) {
                
                 Socket s = serverSoket.accept();
                 
                System.out.println("Klijent je povezan");
                ObradaKlijentskihZahteva okz = new ObradaKlijentskihZahteva(s);
                klijenti.add(okz);
                okz.start();
                 
               

            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void zaustaviServer() {
        kraj = true;
        try {
            for (ObradaKlijentskihZahteva k : klijenti) {
                k.prekini();
            }
            serverSoket.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    

}

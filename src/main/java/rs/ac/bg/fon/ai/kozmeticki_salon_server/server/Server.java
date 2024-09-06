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
 * Klasa koja predstavlja server aplikacije i upravlja konekcijama sa klijentima.
 * 
 * Nasleđuje Thread klasu kako bi mogla da radi u posebnoj niti, omogućavajući
 * serveru da istovremeno prihvata više klijenata.
 */
public class Server extends Thread {
    
    /**
     * Indikator koji označava da li je server aktivan tj. da li prihvata nove klijente.
     */
    boolean kraj = false;
     /**
     * Objekat (serverSocket) koji se koristi za prihvatanje dolaznih konekcija klijenata.
     */
    ServerSocket serverSoket;
    
    /**
     * Lista koja čuva objekte klase ObradaKlijentskih zahteva kreirane za povezane klijente.
     * Ovi objekti upravljaju obradom zahteva koje klijenti šalju serveru.
     */
    List<ObradaKlijentskihZahteva> klijenti;
  
     /**
     * Konstruktor koji inicijalizuje listu za čuvanje objekata koji obrađuju zahteve klijenata bez elemenata.
     */
    public Server() {
        klijenti = new ArrayList<>();
    }

     /**
     * Metoda koja se pokreće kada se server pokrene. Ova metoda čeka da klijenti uspostave konekciju
     * i za svakog klijenta kreira novu instancu klase ObradaKlijentskihZahteva, koja se zatim pokreće
     * u novoj niti.
     */
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

    /**
     * Metoda koja zaustavlja server i zatvara sve postojeće konekcije sa klijentima.
     * Takođe zatvara ServerSocket koji je korišćen za prihvatanje konekcija.
     */
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

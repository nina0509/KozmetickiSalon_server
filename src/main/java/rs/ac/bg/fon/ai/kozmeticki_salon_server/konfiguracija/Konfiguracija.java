/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.konfiguracija;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa koja upravlja učitavanjem, čuvanjem i manipulacijom konfiguracionih
 * postavki aplikacije koje se nalaze u konfiguracionom fajlu.
 *
 * Ova klasa koristi Properties objekat za čuvanje konfiguracionih podataka i
 * omogućava pristup i modifikaciju tih podataka. Takođe, omogućava čuvanje
 * izmena u konfiguracionom fajlu.
 *
 * Koristi Singleton kako bi se obezbedilo da samo jedan primerak Konfiguracije
 * postoji tokom celokupne aplikacije.
 */
public class Konfiguracija {

    /**
     * Privatna statička promenljiva koja čuva jedinu instancu Konfiguracija
     * klase.
     */
    private static Konfiguracija instance;
    /**
     * Properties objekat koji sadrži parove kljuceva i vrednosti i u kome
     * cuvamo konfiguraciona podesavanja.
     */
    private Properties konfiguracija;

    /**
     * Privatni konstruktor koji učitava konfiguraciju iz konfiguracionog fajla.
     * Ovaj konstruktor učitava konfiguraciju iz fajla smještenog na putanji
     * C:\\Users\\ninic\\Documents\\NetBeansProjects\\SEMINARSKI_SERVER\\config\\config.properties
     *
     * Ako učitavanje ne uspe, ispisuje grešku na konzolu i zapisuje grešku u
     * log.
     *
     */
    private Konfiguracija() {

        konfiguracija = new Properties();
        try {
            konfiguracija.load(new FileInputStream("C:\\Users\\ninic\\Documents\\NetBeansProjects\\SEMINARSKI_SERVER\\config\\config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Vraća jedinu instancu Konfiguracija klase. Ako ona ne postoji, kreira je.
     *
     * @return Jedina instanca Konfiguracija klase.
     */
    public static Konfiguracija getInstance() {
        if (instance == null) {
            instance = new Konfiguracija();
        }
        return instance;
    }

    /**
     * Vraća vrednost za dati ključ.
     *
     * @param key Ključ za koji se traži vrednost.
     * @return Vrednost za dati kljuc, ili "n/a" ako ključ ne postoji.
     */
    public String getProperty(String key) {
        return konfiguracija.getProperty(key, "n/a");
    }

    /**
     * Postavlja vrednost za dati ključ.
     *
     * @param key Ključ za koji se postavlja vrednost.
     * @param value Vrednost koja se postavlja za dati ključ.
     */
    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }

    /**
     * Čuva izmene u konfiguracionom fajlu.
     *
     * Ova metoda čuva trenutne izmene u konfiguracionom fajlu koji se nalazi na
     * putanji
     * C:\\Users\\ninic\\Documents\\NetBeansProjects\\SEMINARSKI_SERVER\\config\\config.properties.
     * Ako dođe do greške tokom čuvanja, ispisuje grešku na konzolu i zapisuje
     * grešku u log.
     *
     */
    public void sacuvajIzmene() {
        try {
            konfiguracija.store(new FileOutputStream("C:\\Users\\ninic\\Documents\\NetBeansProjects\\SEMINARSKI_SERVER\\config\\config.properties"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

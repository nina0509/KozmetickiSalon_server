/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje podataka o usluzi.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i izvršenje operacije.
 * 
 * @author Nikolina Baros
 */
public class UcitajUsluguSO extends OpstaSO {
    
    /**
     * Objekat klase Usluga koji se koristi za cuvanje rezultata učitavanja.
     */
     Usluga u;
     
     /**
     * Proverava preduslove za izvršenje operacije. 
     * 
     * @param param Objekat koji predstavlja uslugu za učitavanje.
     * @throws Exception ako je parametar null ili nije instanca klase Usluga.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        Usluga parametar = (Usluga) param;
        if (parametar == null) {
            throw new Exception("Sistem ne moze da ucita uslugu");
        }    }

    
    /**
     * Izvršava operaciju učitavanja usluge sa iz baze podataka. 
     * Postavlja u na ucitanu uslugu ili null ako usluga nije pronađena.
     * 
     * @param param Objekat koji predstavlja usluga za učitavanje.
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
     
         Usluga par = (Usluga) param;
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId WHERE usluga.uslugaId="+par.getUslugaId();
        
         List<Usluga> usluge=broker.vratiSve(new Usluga(), uslov);
         
          if (usluge.isEmpty()) {
            u = null;
        } else {
            u = usluge.get(0);
        }
    }

    public Usluga getU() {
        return u;
    }

    public void setU(Usluga u) {
        this.u = u;
    }
    
    
    
    
}

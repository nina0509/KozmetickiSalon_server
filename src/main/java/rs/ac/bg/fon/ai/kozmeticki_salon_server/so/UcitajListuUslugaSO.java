/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje liste svih usluga iz baze.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i izvršenje operacije.
 * 
 * @author Nikolina Baros
 */
public class UcitajListuUslugaSO extends OpstaSO{

     /**
     * Lista svih usluge ucitanih iz baze.
     */
     List<Usluga> Usluge;
    
     /**
     * Proverava preduslove za izvršenje operacije.  
     * Kod ove sistemske operacije, nema nikakvih preduslova pa je telo metode prazno.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

    }

     /**
     * Izvršava operaciju učitavanja liste svih usluge iz baze podataka. 
     * 
     * @param param Objekat koji predstavlja uslugu za učitavanje(u ovom slucaju je null jer ucitavamo sve usluge).
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        Usluge = broker.vratiSve(new Usluga(), uslov);

    }

    
    /**
     * Vraca ucitane usluge.
     * 
     * @return Lista ucitanih usluga.
     */
    public List<Usluga> getUsluge() {
        return Usluge;
    }

    /**
     * Postavlja ucitane usluge.
     * 
     * @param lista Nove ucitane usluge.
     */
    public void setUsluge(List<Usluga> Usluge) {
        this.Usluge = Usluge;
    }
    
}

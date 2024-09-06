/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;


/**
 * Klasa koja predstavlja sistemsku operaciju za brisanje klijenta iz sistema.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i izvršenje operacije.
 * 
 * @author Nikolina Baros
 */
public class IzbrisiKlijentaSO extends OpstaSO {

    /**
     * Proverava preduslove za izvršenje operacije brisanja klijenta.
     * 
     * @param param Objekat koji predstavlja klijenta koji treba da se izbriše.
     * @throws Exception ako param nije instanca klase Klijent ili je null.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Klijent)) {
            throw new Exception("Sistem ne moze da izbrise klijenta");
        }

    }

      /**
     * Izvršava operaciju brisanja klijenta iz baze podataka.
     * 
     * @param param Objekat koji predstavlja klijenta koji treba da se izbriše.
     * @throws Exception Ako dođe do greške pri brisanju klijenta.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        broker.izbrisi((Klijent) param);
    }

}

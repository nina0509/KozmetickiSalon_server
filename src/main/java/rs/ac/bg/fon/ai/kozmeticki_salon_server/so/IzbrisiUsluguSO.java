/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja sistemsku operaciju za brisanje usluge iz sistema. Ova
 * klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru
 * preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class IzbrisiUsluguSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * IzbrisiUsluguSO.
     */
    public IzbrisiUsluguSO() {

    }

    /**
     * Proverava preduslove za izvršenje operacije brisanja usluge.
     *
     * @param param Objekat koji predstavlja uslugu koja treba da se izbriše.
     * @throws Exception ako param nije instanca klase Usluga ili je null.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Usluga)) {
            throw new Exception("Sistem ne moze da izbrise uslugu");
        }

    }

    /**
     * Izvršava operaciju brisanja usluge iz baze podataka.
     *
     * @param param Objekat koji predstavlja uslugu koji treba da se izbriše.
     * @throws Exception Ako dođe do greške pri brisanju usluge.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        broker.izbrisi((Usluga) param);
    }
}

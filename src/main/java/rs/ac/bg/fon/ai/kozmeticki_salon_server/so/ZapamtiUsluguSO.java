/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja sistemsku operaciju za čuvanje usluge u bazi podataka.
 * Nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i
 * izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class ZapamtiUsluguSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * ZapamtiUsluguSO.
     */
    public ZapamtiUsluguSO() {
    }

    /**
     * Proverava preduslove za čuvanje usluge. Ako usluga nije validna ili ne
     * ispunjava preduslove, baca izuzetak.
     *
     * @param param Objekat koji predstavlja uslugu koja treba da se sačuva ili
     * ažurira.
     * @throws Exception Ako je usluga null, naziv usluge je null ili prazan
     * string, trajanje je manje od 0 i vece od 72000, cena je negativan broj
     * ili je tip usluge null.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        Usluga u = (Usluga) param;
        if (u.getNaziv().isBlank() || u.getNaziv() == null || u.getTrajanje() < 0 || u.getTrajanje() > 72000 || u.getCena() < 0 || u.getTip() == null) {
            throw new Exception("Sistem ne moze da zapamti klijenta");
        }

    }

    /**
     * Izvršava operaciju čuvanja usluge u bazi podataka.
     *
     * @param param Objekt koji predstavlja uslugu koja treba da se sačuva.
     * @throws Exception Ako dođe do greške prilikom čuvanja usluge.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        broker.sacuvaj((Usluga) param);
    }

}

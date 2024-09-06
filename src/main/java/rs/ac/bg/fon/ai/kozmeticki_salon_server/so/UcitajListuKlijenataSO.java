/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;

/**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje liste svih klijenata
 * iz baze. Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za
 * proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class UcitajListuKlijenataSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * UcitajListuKlijenataSO.
     */
    public UcitajListuKlijenataSO() {
    }
    /**
     * Lista svih klijenata ucitanih iz baze.
     */
    List<Klijent> klijenti;

    /**
     * Proverava preduslove za izvršenje operacije. Kod ove sistemske operacije,
     * nema nikakvih preduslova pa je telo metode prazno.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
    }

    /**
     * Izvršava operaciju učitavanja liste svih klijenta iz baze podataka.
     *
     * @param param Objekat koji predstavlja klijenta za učitavanje(u ovom
     * slucaju je null jer ucitavamo sve klijente).
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        klijenti = broker.vratiSve(new Klijent(), null);

    }

    /**
     * Vraca ucitane klijente.
     *
     * @return Lista ucitanih klijenata.
     */
    public List<Klijent> getKlijenti() {
        return klijenti;
    }

    /**
     * Postavlja ucitane klijente.
     *
     * @param klijenti Novi ucitani klijenti.
     */
    public void setKlijenti(List<Klijent> klijenti) {
        this.klijenti = klijenti;
    }

}

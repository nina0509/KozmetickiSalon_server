/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;

/**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje podataka o klijentu.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru
 * preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class UcitajKlijentaSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * UcitajKlijentaSO.
     */
    public UcitajKlijentaSO() {
    }

    /**
     * Objekat klase Klijent koji se koristi za cuvanje rezultata učitavanja.
     */
    Klijent k;

    /**
     * Proverava preduslove za izvršenje operacije.
     *
     * @param param Objekat koji predstavlja klijenta za učitavanje.
     * @throws Exception ako je parametar null ili nije instanca klase Klijent.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        Klijent parametar = (Klijent) param;
        if (parametar == null) {
            throw new Exception("Sistem ne moze da ucita klijenta");
        }

    }

    /**
     * Izvršava operaciju učitavanja klijenta iz baze podataka. Postavlja k na
     * pronađenog klijenta ili null ako klijent nije pronađen.
     *
     * @param param Objekat koji predstavlja klijenta za učitavanje.
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Klijent parametar = (Klijent) param;
        List<Klijent> klijenti = broker.vratiSve(new Klijent(), " WHERE klijent.klijentId=" + parametar.getKlijentId());
        if (klijenti.isEmpty()) {
            k = null;
        } else {
            k = klijenti.get(0);
        }

    }

    /**
     * Vraca ucitanog klijenta.
     *
     * @return Ucitani klijent.
     */
    public Klijent getK() {
        return k;
    }

    /**
     * Postavlja ucitanog klijenta.
     *
     * @param k Novi ucitani klijent.
     */
    public void setK(Klijent k) {
        this.k = k;
    }

}

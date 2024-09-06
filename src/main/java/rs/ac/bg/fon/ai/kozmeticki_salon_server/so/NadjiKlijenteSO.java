/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;

/**
 * Klasa koja predstavlja sistemsku operaciju za pretragu klijenata u sistemu.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru
 * preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class NadjiKlijenteSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * NadjiKlijenteSO.
     */
    public NadjiKlijenteSO() {
    }
    /**
     * Lista klijenata koja je rezultat pretrage.
     */
    List<Klijent> klijenti;

    /**
     * Proverava preduslove za izvršenje operacije pretrage klijenata.
     *
     * @param param Objekat koji predstavlja klijenta za pretragu.
     * @throws Exception ako param nije null a nije instanca klase Klijent.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param != null && !(param instanceof Klijent)) {
            throw new Exception("Sistem ne moze da nadje klijente po zadatoj vrednosti");
        }

    }

    /**
     * Izvršava operaciju pretrage klijenata u bazi podataka.
     *
     * Ako je param null, pretražuju se svi klijenti. Ako nije null, koristi se
     * kao filter za pretragu klijenata na osnovu imena i prezimena.
     *
     * @param param Objekat koji predstavlja klijenta za pretragu.
     * @throws Exception ako dođe do greške pri pretrazi klijenata.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Klijent k = (Klijent) param;
        String uslov = null;
        if (k == null) {
            klijenti = broker.vratiSve(new Klijent(), null);
            return;
        }

        if (!k.getIme().isBlank() && k.getIme() != null) {
            uslov = " WHERE klijent.ime LIKE '%" + k.getIme() + "%'";
        }

        if (!k.getPrezime().isBlank() && k.getPrezime() != null) {

            if (uslov != null) {
                uslov += " AND klijent.prezime LIKE '%" + k.getPrezime() + "%'";
            } else {
                uslov = " WHERE klijent.prezime LIKE '%" + k.getPrezime() + "%'";
            }
        }
        System.out.println(uslov);
        klijenti = broker.vratiSve(new Klijent(), uslov);

    }

    /**
     * Vraca listu klijenata koji su rezultat pretrage.
     *
     * @return Lista klijenata koji su rezultat pretrage.
     */
    public List<Klijent> getKlijenti() {
        return klijenti;
    }

    /**
     * Postavlja listu klijenata koji su rezultat pretrage.
     *
     * @param klijenti Nova lista klijenata koji su rezultat pretrage.
     */
    public void setKlijenti(List<Klijent> klijenti) {
        this.klijenti = klijenti;
    }

}

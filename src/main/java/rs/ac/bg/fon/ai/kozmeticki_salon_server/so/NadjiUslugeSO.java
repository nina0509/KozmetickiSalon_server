/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja sistemsku operaciju za pretragu usluga u sistemu. Ova
 * klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru
 * preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class NadjiUslugeSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * NadjiUslugeSO.
     */
    public NadjiUslugeSO() {
    }

    /**
     * Lista usluga koja je rezultat pretrage.
     */
    List<Usluga> usluge;

    /**
     * Proverava preduslove za izvršenje operacije pretrage usluga.
     *
     * @param param Objekat koji predstavlja uslugu za pretragu.
     * @throws Exception ako param nije null a nije instanca klase Usluga.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param != null && !(param instanceof Usluga)) {
            throw new Exception("Sistem ne moze da nadje usluge po zadatoj vrednosti");
        }

    }

    /**
     * Izvršava operaciju pretrage usluga u bazi podataka. Koristi za pretragu
     * tip usluge i naziv.
     *
     * @param param Objekat koji predstavlja uslugu za pretragu.
     * @throws Exception Ako dođe do greške pri pretrazi usluga.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Usluga u = (Usluga) param;
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        String uslov1 = null;

        if (u != null && !u.getNaziv().isBlank() && u.getNaziv() != null) {
            uslov1 = " WHERE usluga.naziv LIKE '%" + u.getNaziv() + "%'";
        }

        if (u != null && u.getTip() != null) {

            if (uslov1 != null) {
                uslov1 += " AND usluga.tipId=" + u.getTip().getTipId();
            } else {
                uslov1 = " WHERE usluga.tipId=" + u.getTip().getTipId();
            }
        }

        if (uslov1 != null) {
            uslov += uslov1;
        }
        System.out.println(uslov);
        usluge = broker.vratiSve(new Usluga(), uslov);

    }

    /**
     * Vraca listu usluga koje su rezultat pretrage.
     *
     * @return Lista usluga koje su rezultat pretrage.
     */
    public List<Usluga> getUsluge() {
        return usluge;
    }

    /**
     * Postavlja listu usluga koje su rezultat pretrage.
     *
     * @param usluge Nova lista usluga koje su rezultat pretrage.
     */
    public void setUsluge(List<Usluga> usluge) {
        this.usluge = usluge;
    }

}

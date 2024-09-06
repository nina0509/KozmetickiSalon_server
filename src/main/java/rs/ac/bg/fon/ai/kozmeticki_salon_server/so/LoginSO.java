/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Menadzer;

/**
 * Klasa koja predstavlja sistemsku operaciju za prijavljivanje menadzera na
 * sistem. Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za
 * proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class LoginSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * LoginSO.
     */
    public LoginSO() {

    }
    /**
     * Menadzer koji se uspesno ulogovao. Ako prijava nije uspesna, ima vrednost
     * null.
     */
    Menadzer menadzer;

    /**
     * Proverava preduslove za izvršenje operacije brisanja usluge.
     *
     * @param param Objekat koji predstavlja menadzera koji se prijavljuje.
     * @throws Exception ako param nije instanca klase Menadzer ili je null.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        Menadzer m = (Menadzer) param;
        if (param == null || !(param instanceof Menadzer)) {
            throw new Exception("Sistem nije ulogovao menadzera");
        }

    }

    /**
     * Izvršava operaciju prijave menadzera na sistem. Postavlja vrednost
     * odgovarajuceg atributa (menadzer) na vrednost prijavljenog menadzera ili
     * null ako je neuspesna prijava.
     *
     *
     * @param param Objekat koji predstavlja menadzera koji pokusava da se
     * prijavi.
     * @throws Exception Ako dođe do greške pri prijavi menadzera.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        List<Menadzer> sviMenadzeri = broker.vratiSve((Menadzer) param, null);

        for (Menadzer m : sviMenadzeri) {

            if (m.equals((Menadzer) param)) {

                menadzer = m;
                return;
            }

        }
        menadzer = null;

    }

    /**
     * Vraca ulogovanog menadzera.
     *
     * @return Ulogovani menadzer.
     */
    public Menadzer getMenadzer() {
        return menadzer;
    }

    /**
     * Postavlja ulogovanog menadzera.
     *
     * @param menadzer Novi ulogovani menadzer.
     */
    public void setMenadzer(Menadzer menadzer) {
        this.menadzer = menadzer;
    }

}

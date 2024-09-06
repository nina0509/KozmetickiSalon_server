/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;

/**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje liste svih tipova
 * usluga iz baze. Ova klasa nasleđuje klasu OpstaSO i implementira njene metode
 * za proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class UcitajListuTipovaUslugaSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * UcitajListuTipovaUslugaSO.
     */
    public UcitajListuTipovaUslugaSO() {
    }
    /**
     * Lista svih tipova usluge ucitanih iz baze.
     */
    List<TipUsluge> tipoviUsluge;

    /**
     * Proverava preduslove za izvršenje operacije. Kod ove sistemske operacije,
     * nema nikakvih preduslova pa je telo metode prazno.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    /**
     * Izvršava operaciju učitavanja liste svih tipova usluge iz baze podataka.
     *
     * @param param Objekat koji predstavlja tip usluge za učitavanje(u ovom
     * slucaju je null jer ucitavamo sve tipove usluga).
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        tipoviUsluge = broker.vratiSve(new TipUsluge(), null);

    }

    /**
     * Vraca ucitane tipove usluge.
     *
     * @return Lista ucitanih tipova usluga.
     */
    public List<TipUsluge> getTipoviUsluge() {
        return tipoviUsluge;
    }

    /**
     * Postavlja ucitane tipove usluge.
     *
     * @param tipoviUsluge Nove ucitani tipovi usluge.
     */
    public void setTipoviUsluge(List<TipUsluge> tipoviUsluge) {
        this.tipoviUsluge = tipoviUsluge;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;

/**
 * Klasa koja predstavlja sistemsku operaciju za pretragu rezervacija u sistemu.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru
 * preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class NadjiRezervacijeSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * NadjiRezervacijeSO.
     */
    public NadjiRezervacijeSO() {
    }
 /**
     * Konstruktor sa parametrima, kreira novu instancu klase
     * NadjiRezervacijeSO i postavlja broker na zadatu vrednost.
     * 
     * @param broker Novi broker baze podataka.
     */
   
     public NadjiRezervacijeSO(Repozitorijum broker) {
         this.broker=broker;
    }
    /**
     * Lista rezervacija koja je rezultat pretrage.
     */
    List<Rezervacija> rezervacije;

    /**
     * Proverava preduslove za izvršenje operacije pretrage rezervacija.
     *
     * @param param Objekat koji predstavlja rezervaciju za pretragu.
     * @throws Exception ako param nije null a nije instanca klase Rezervacija.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param != null && !(param instanceof Rezervacija)) {
            throw new Exception("Sistem ne moze da nadje rezervacije po zadatoj vrednosti");
        }

    }

    /**
     * Izvršava operaciju pretrage rezervacija u bazi podataka. Koristi za
     * pretragu ime klijenta koji je kreirao rezervaciju i datum rezervacije.
     *
     * @param param Objekat koji predstavlja rezervaciju za pretragu.
     * @throws Exception Ako dođe do greške pri pretrazi rezervacija.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Rezervacija r = (Rezervacija) param;

        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId";
        String uslov1 = null;

        if (r != null && r.getKlijent() != null && r.getKlijent().getIme()!=null && !r.getKlijent().getIme().isBlank()) {
            uslov1 = " WHERE klijent.ime LIKE '%" + r.getKlijent().getIme() + "%'";
        }

        if (r != null && r.getDatum() != null) {
            java.sql.Date datum1 = new java.sql.Date(r.getDatum().getTime());
            if (uslov1 != null) {

                uslov1 += " AND rezervacija.datum='" + datum1 + "'";
            } else {
                uslov1 = " WHERE rezervacija.datum='" + datum1 + "'";
            }
        }

        if (uslov1 != null) {
            uslov += uslov1;
        }
        uslov += " ORDER BY rezervacija.datum DESC";
        
        rezervacije = broker.vratiSve(new Rezervacija(), uslov);

        for (Rezervacija nova : rezervacije) {
            List<StavkaRezervacije> stavke = broker.vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + nova.getRezervacijaId());
            nova.setStavke(stavke);

        }

    }

    /**
     * Vraca listu rezervacija koje su rezultat pretrage.
     *
     * @return Lista rezervacija koje su rezultat pretrage.
     */
    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    /**
     * Postavlja listu rezervacija koji su rezultat pretrage.
     *
     * @param rezervacije Nova lista rezervacija koji su rezultat pretrage.
     */
    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

}

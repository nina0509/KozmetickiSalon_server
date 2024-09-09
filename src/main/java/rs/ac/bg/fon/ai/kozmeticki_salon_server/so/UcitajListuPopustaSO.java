/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;

/**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje liste svih popusta
 * iz baze. Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za
 * proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class UcitajListuPopustaSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * UcitajListuPopustaSO.
     */
    public UcitajListuPopustaSO() {
    }
    
    /**
     * Konstruktor sa parametrima, kreira novu instancu klase
     * UcitajListuPopustaSO i postavlja broker na zadatu vrednost.
     * 
     * @param broker Novi broker baze podataka.
     */
   
     public UcitajListuPopustaSO(Repozitorijum broker) {
         this.broker=broker;
    }
    /**
     * Lista svih popusta ucitanih iz baze.
     */
    List<Popust> popusti;

    /**
     * Proverava preduslove za izvršenje operacije. Kod ove sistemske operacije,
     * nema nikakvih preduslova pa je telo metode prazno.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Klijent)) {
            throw new Exception("Sistem ne moze da ucita listu popusta");
        }

    }

    /**
     * Izvršava operaciju učitavanja liste svih popusta iz baze podataka.
     *
     * @param param Objekat koji predstavlja klijenta za kog ucitavamo popuste.
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Klijent k = (Klijent) param;
        popusti = broker.vratiSve(new Popust(), " JOIN klijent ON klijent.klijentId=popust.klijentId JOIN usluga ON usluga.uslugaId=popust.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + k.getKlijentId());
    }

    /**
     * Vraca ucitane popuste.
     *
     * @return Lista ucitanih popusta.
     */
    public List<Popust> getPopusti() {
        return popusti;
    }

    /**
     * Postavlja ucitane popuste.
     *
     * @param popusti Novi ucitani popusti.
     */
    public void setPopusti(List<Popust> popusti) {
        this.popusti = popusti;
    }

}

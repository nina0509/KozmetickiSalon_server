/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.DbRepozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;


/**
 * Apstraktna klasa koja predstavlja opšti šablon za sve sistemske operacije
 * u sistemu. 
 * 
 * Ova klasa sadrži metodu za izvršenje operacije i upravlja transakcijama i povezivanjem
 * sa bazom podataka.
 * 
 * @author Nikolina Baros
 */
public abstract class OpstaSO {

    /**
     * Repozitorijum koji se koristi za pristup bazi podataka.
     */
    protected final Repozitorijum broker;

    /**
     * Konstruktor koji inicijalizuje broker kao instancu klase DbRepozitorijumGenericki.
     */
    public OpstaSO() {
        this.broker = new DbRepozitorijumGenericki();
    }

    /**
     * Izvršava operaciju koristeći zadati objekat. 
     * 
     * Ova metoda upravlja celokupnim tokom izvrsenja sistemske operacije,
     * uključujući preduslove, transakcije i konekciju sa bazom podataka.
     * 
     * @param objekat Objekat koji se koristi za izvršenje operacije.
     * @throws Exception ako dođe do greške tokom izvršenja operacije.
     */
    public final void izvrsi(Object objekat) throws Exception {

        try {

            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat);
            potvrdiTransakciju();
        } catch (Exception ex) {
            ponistiTransakciju();
            throw ex;
        } finally {
            ugasiKonekciju();
        }

    }

     /**
     * Proverava preduslove za izvršenje operacije. 
     * Ovo je apstraktna metoda koja mora biti implementirana u konkretnim sistemskim operacijama.
     * 
     * @param param Objekat koji predstavlja parametar za proveru preduslova.
     * @throws Exception ako preduslovi nisu ispunjeni.
     */
    protected abstract void preduslovi(Object param) throws Exception;

     /**
     * Izvršava konkretnu sistemsku operaciju. Ovo je apstraktna metoda koja mora biti implementirana
     * u konkretnik sistemskim operacijama.
     * 
     * @param param Objekat koji predstavlja parametar za izvršenje operacije.
     * @throws Exception ako dođe do greške tokom izvršenja operacije.
     */
    protected abstract void izvrsiOperaciju(Object param) throws Exception;

    /**
     * Započinje transakciju tako što uspostavlja konekciju sa bazom podataka.
     * 
     * @throws Exception ako dođe do greške prilikom uspostavljanja konekcije.
     */
    private void zapocniTransakciju() throws Exception {
        ((DbRepozitorijum) broker).uspostaviKonekciju();
    }

     /**
     * Potvrđuje transakciju tako što izvršava commit na bazi podataka.
     * 
     * @throws Exception ako dođe do greške prilikom potvrđivanja transakcije.
     */
    private void potvrdiTransakciju() throws Exception {
        ((DbRepozitorijum) broker).potvrdiTransakciju();
    }

     /**
     * Poništava transakciju tako što izvršava rollback na bazi podataka.
     * 
     * @throws Exception ako dođe do greške prilikom poništavanja transakcije.
     */
    private void ponistiTransakciju() throws Exception {
        ((DbRepozitorijum) broker).ponistiTransakciju();
    }

      /**
     * Gaši konekciju sa bazom podataka.
     * 
     * @throws Exception ako dođe do greške prilikom gašenja konekcije.
     */
    private void ugasiKonekciju() throws Exception {

        ((DbRepozitorijum) broker).raskiniKonekciju();
    }

}

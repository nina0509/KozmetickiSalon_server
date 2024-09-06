/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;

 /**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje podataka o rezervaciji.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i izvršenje operacije.
 * 
 * @author Nikolina Baros
 */
public class UcitajRezervacijuSO extends OpstaSO {
    
     /**
     * Objekat klase Rezervacija koji se koristi za cuvanje rezultata učitavanja.
     */
    Rezervacija r;
    
    /**
     * Proverava preduslove za izvršenje operacije. 
     * 
     * @param param Objekat koji predstavlja rezervaciju za učitavanje.
     * @throws Exception ako je parametar null ili nije instanca klase Rezervacija.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
       Rezervacija parametar = (Rezervacija) param;
        if (parametar == null) {
            throw new Exception("Sistem ne moze da ucita rezervaciju");
        }    }

    
    /**
     * Izvršava operaciju učitavanja rezervacije sa njenim stavkama iz baze podataka. 
     * Postavlja r na ucitanu rezervaciju ili null ako rezervacija nije pronađena.
     * 
     * @param param Objekat koji predstavlja rezervaciju za učitavanje.
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

         
        Rezervacija r1=(Rezervacija)param;
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId="+r1.getRezervacijaId();
        
        List<Rezervacija> lista=broker.vratiSve(new Rezervacija(), uslov);
        
        if(lista.isEmpty())
        {
        r=null;
        }
        else
        {
        
            r=lista.get(0);
            List<StavkaRezervacije> stavke = broker.vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId());
            r.setStavke(stavke);
            

        }
        
 

    }

     /**
     * Vraca ucitanu rezervaciju.
     * 
     * @return Ucitana rezervacija.
     */
    public Rezervacija getR() {
        return r;
    }

    
      /**
     * Postavlja ucitanu rezervaciju.
     * 
     * @param r Nova ucitana rezervacija.
     */
    public void setR(Rezervacija r) {
        this.r = r;
    }
    
    
    
 }

    


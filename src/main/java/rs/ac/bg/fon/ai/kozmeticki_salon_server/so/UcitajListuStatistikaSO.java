/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Statistika;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaStatistike;

 /**
 * Klasa koja predstavlja sistemsku operaciju za učitavanje liste svih statistika iz baze.
 * Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i izvršenje operacije.
 * 
 * @author Nikolina Baros
 */
public class UcitajListuStatistikaSO extends OpstaSO {
    
    /**
     * Lista svih statistika ucitanih iz baze.
     */
    List<Statistika> lista=new ArrayList<>();

    /**
     * Proverava preduslove za izvršenje operacije.  
     * Kod ove sistemske operacije, nema nikakvih preduslova pa je telo metode prazno.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    
    /**
     * Izvršava operaciju učitavanja liste svih statistika iz baze podataka kao i njihovih stavki. 
     * 
     * @param param Objekat koji predstavlja statistiku za učitavanje(u ovom slucaju je null jer ucitavamo sve statistike).
     * @throws Exception ako dođe do greške tokom ucitavanja iz baze podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        lista=broker.vratiSve(new Statistika(), null);
        
         for (Statistika nova : lista) {
            List<StavkaStatistike> stavke = broker.vratiSve(new StavkaStatistike(), " JOIN statistika ON statistika.godina=stavkastatistike.godina JOIN usluga ON stavkastatistike.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE statistika.godina=" + nova.getGodina());
            nova.setStavke(stavke);

        }

    }

    /**
     * Vraca ucitane statistike.
     * 
     * @return Lista ucitanih statistika.
     */
    public List<Statistika> getLista() {
        return lista;
    }
    
    /**
     * Postavlja ucitane statistike.
     * 
     * @param lista Nove ucitani statistike.
     */

    public void setLista(List<Statistika> lista) {
        this.lista = lista;
    }
    
    
    
    
}

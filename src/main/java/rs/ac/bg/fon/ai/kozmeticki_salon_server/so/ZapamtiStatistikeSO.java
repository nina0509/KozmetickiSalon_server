/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Statistika;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaStatistike;

/**
 * Klasa koja predstavlja sistemsku operaciju za čuvanje ili azuriranje statistike za tekucu godinu u bazi podataka.
 * Nasleđuje klasu OpstaSO i implementira njene metode za proveru preduslova i izvršenje operacije.
 * 
 * @author Nikolina Baros
 */
public class ZapamtiStatistikeSO extends OpstaSO {

     /**
     * Proverava preduslove za izvršenje operacije.  
     * Kod ove sistemske operacije, nema nikakvih preduslova pa je telo metode prazno.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    
     /**
     * Kreira statistiku za tekucu godinu na osnovu rezervacija u bazi i izvršava operaciju čuvanja statistike i njenih stavki u bazi podataka. 
     * 
     * Ako statistika već postoji u bazi,ažurira njene podatke, u suprotnom čuva novu statistiku.
     * 
     * @param param Objekt koji predstavlja statistiku koja treba da se sačuva ili ažurira.
     * @throws Exception Ako dođe do greške prilikom čuvanja ili ažuriranja statistike.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

      
        List<StavkaRezervacije> stavke = broker.vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE EXTRACT(YEAR FROM rezervacija.datum)=EXTRACT(YEAR FROM current_date) ORDER BY usluga.uslugaId");
        System.out.println(stavke);

        int tekuca = LocalDate.now().getYear();
        int br = 1;
        Statistika stat = new Statistika(tekuca, stavke.size());
        stat.setStavke(new ArrayList<>());

        StavkaRezervacije stavka = stavke.get(0);

        StavkaStatistike s1 = new StavkaStatistike(stat, stavka.getUsluga(), br);

        for (int i = 1; i < stavke.size(); i++) {

            StavkaRezervacije trenutna = stavke.get(i);
            StavkaRezervacije prethodna = stavke.get(i - 1);

            if (prethodna.getUsluga().equals(trenutna.getUsluga())) {

                br++;
                s1.setBrojRezUsluge(br);

            } else {
                stat.getStavke().add(s1);
                br = 1;
                s1 = new StavkaStatistike(stat, trenutna.getUsluga(), br);

            }

        }
        stat.getStavke().add(s1);
       
        List<Statistika> statBaza=broker.vratiSve(new Statistika(), " WHERE statistika.godina="+tekuca);
        
        if(statBaza.isEmpty())
        {
        
            broker.sacuvaj(stat);
            for(StavkaStatistike ss:stat.getStavke())
            {
            ss.setStatistika(stat);
            broker.sacuvaj(ss);
            
            }
        
        }
        
        else
        {
            broker.izmeni(stat);
            for(StavkaStatistike ss:stat.getStavke())
            {
           broker.izbrisi(ss);
           broker.sacuvaj(ss);
            
            }
        
        }
        
        
        

    }
    
    
    
    

}

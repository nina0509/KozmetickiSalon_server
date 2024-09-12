/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;

/**
 * Klasa koja predstavlja sistemsku operaciju za čuvanje ili ažuriranje
 * rezervacije u bazi podataka. Nasleđuje klasu OpstaSO i implementira njene
 * metode za proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class ZapamtiRezervacijuSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * ZapamtiRezervacijuSO.
     */
    public ZapamtiRezervacijuSO() {
    }
/**
     * Konstruktor sa parametrima, kreira novu instancu klase
     * ZapamtiRezervacijuSO i postavlja broker na zadatu vrednost.
     * 
     * @param broker Novi broker baze podataka.
     */
   
     public ZapamtiRezervacijuSO(Repozitorijum broker) {
         this.broker=broker;
    }
    /**
     * Proverava preduslove za čuvanje ili ažuriranje rezervacije. Ako
     * rezervacija nije validna ili ne ispunjava preduslove, baca izuzetak.
     *
     * @param param Objekat koji predstavlja rezervaciju koja treba da se sačuva
     * ili ažurira.
     * @throws Exception Ako je rezervacija null, klijent koji je napravio
     * rezervaciju je null, rezervacija nema nijednu stavku ili ako je cena
     * negativan broj.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Rezervacija)) {
            throw new Exception("Sistem ne moze da zapamti rezervaciju");
        }
        
        Rezervacija r = (Rezervacija) param;
        
        if (r.getDatum()==null || r.getKlijent() == null || r.getStavke()==null || r.getStavke().isEmpty() || r.getDatum().before(new Date())) {
            throw new Exception("Sistem ne moze da zapamti rezervaciju");
        }

    }

    /**
     * Izvršava operaciju čuvanja ili ažuriranja rezervacije i njenih stavki u
     * bazi podataka.
     *
     * Ako rezervacija već postoji u bazi,ažurira njene podatke, u suprotnom
     * čuva novu rezervaciju.
     *
     * @param param Objekt koji predstavlja rezervaciju koja treba da se sačuva
     * ili ažurira.
     * @throws Exception Ako dođe do greške prilikom čuvanja ili ažuriranja
     * rezervacije.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Rezervacija r = (Rezervacija) param;

        if (r.getRezervacijaId() == 0) {
            izvrsiOperacijuZaDodavanje(r);
        } else {
            izvrsiOperacijuZaAzuriranje(r);
        }

    }

    /**
     * Izvršava operaciju čuvanja nove rezervacije i njenih stavki u bazi
     * podataka. Takodje vrsi i azuriranje tabele sa Popustima u skladu sa
     * promenama.
     *
     * @param param Objekt koji predstavlja rezervaciju koja treba da se sačuva.
     * @throws Exception Ako dođe do greške prilikom čuvanja rezervacije.
     */
    private void izvrsiOperacijuZaDodavanje(Rezervacija r) throws Exception {

        java.sql.Date datum = new java.sql.Date(r.getDatum().getTime());
        List<StavkaRezervacije> sveStavke = broker.vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.datum='" + datum + "'");
     
        for (StavkaRezervacije s : sveStavke) {
            for (StavkaRezervacije sr : r.getStavke()) {
                  
                if (sr.getUsluga().equals(s.getUsluga()) && s.getVremePocetka().isBefore(sr.getVremeZavrsetka()) && s.getVremeZavrsetka().isAfter(sr.getVremePocetka())) {
               
                    throw new Exception("Sistem ne moze da doda stavku rezervacije. Termini usluga se preklapaju!");
                }
            }
        }

        int key = broker.sacuvajVratiPK(r);
        List<StavkaRezervacije> stavke = r.getStavke();
        r.setRezervacijaId(key);
        for (StavkaRezervacije sr : stavke) {
            sr.setRezervacija(r);
            broker.sacuvaj((StavkaRezervacije) sr);

            List<Popust> popusti = broker.vratiSve(new Popust(), " JOIN usluga ON popust.uslugaId=usluga.uslugaId JOIN klijent ON klijent.klijentId=popust.klijentId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + r.getKlijent().getKlijentId() + " AND usluga.uslugaId=" + sr.getUsluga().getUslugaId());
            System.out.println(popusti);
            if (popusti.isEmpty()) {
                Popust p = new Popust(r.getKlijent(), sr.getUsluga(), 1, 0);
                broker.sacuvaj((Popust) p);

            } else {

                Popust p = popusti.get(0);
                p.setBrojRezUsluge(p.getBrojRezUsluge() + 1);
                int broj = p.getBrojRezUsluge();

                if (broj < 5) {
                    p.setPopust(0);
                }
                if (broj >= 5 && broj < 10) {
                    p.setPopust(5);
                }

                if (broj >= 10 && broj < 15) {
                    p.setPopust(10);
                }
                if (broj >= 15) {
                    p.setPopust(15);
                }

                broker.izmeni(p);

            }

        }
    }

    /**
     * Izvršava operaciju azuriranja rezervacije i njenih stavki u bazi
     * podataka. Takodje vrsi i azuriranje tabele sa Popustima u skladu sa
     * promenama.
     *
     * @param param Objekt koji predstavlja rezervaciju koja treba da se
     * azurira.
     * @throws Exception Ako dođe do greške prilikom azuriranja rezervacije.
     */
    private void izvrsiOperacijuZaAzuriranje(Rezervacija r) throws Exception {

      
        Rezervacija RezBaza = (Rezervacija) (broker.vratiSve(new Rezervacija(), " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId())).get(0);
        List<StavkaRezervacije> stavke = broker.vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + RezBaza.getRezervacijaId());
        RezBaza.setStavke(stavke);
        
        for (StavkaRezervacije s : RezBaza.getStavke()) {
            //ako nova lista ne sadrzi neku iz baze izbrisi je u bazi i smanji br rezervacija usluge
            if (!(r.getStavke().contains(s))) {
                
                broker.izbrisi((StavkaRezervacije) s);
                List<Popust> popusti = broker.vratiSve(new Popust(), " JOIN usluga ON popust.uslugaId=usluga.uslugaId JOIN klijent ON klijent.klijentId=popust.klijentId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + r.getKlijent().getKlijentId() + " AND usluga.uslugaId=" + s.getUsluga().getUslugaId());

                Popust p = popusti.get(0);
                p.setBrojRezUsluge(p.getBrojRezUsluge() - 1);
                if (p.getBrojRezUsluge() == 0) {
                    broker.izbrisi((Popust) p);
                } else {
                    prilagodiISacuvajPopust(p);

                }

            }
        }

        for (StavkaRezervacije s : r.getStavke()) {
            //nova lista sadrzi nesto a u bazi nema
            if (!(RezBaza.getStavke().contains(s))) {
                s.setRezervacija(r);
                broker.sacuvaj((StavkaRezervacije) s);

                List<Popust> popusti = broker.vratiSve(new Popust(), " JOIN usluga ON popust.uslugaId=usluga.uslugaId JOIN klijent ON klijent.klijentId=popust.klijentId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + r.getKlijent().getKlijentId() + " AND usluga.uslugaId=" + s.getUsluga().getUslugaId());
                if (popusti.isEmpty()) {
                    Popust p = new Popust(r.getKlijent(), s.getUsluga(), 1, 0);
                    broker.sacuvaj((Popust) p);

                } else {

                    Popust p = popusti.get(0);
                    p.setBrojRezUsluge(p.getBrojRezUsluge() + 1);

                    prilagodiISacuvajPopust(p);

                }
            }

        }

        if (r.isPojavljivanje() == false && RezBaza.isPojavljivanje() == true) {

            for (StavkaRezervacije s : r.getStavke()) {
                List<Popust> popusti = broker.vratiSve(new Popust(), " JOIN usluga ON popust.uslugaId=usluga.uslugaId JOIN klijent ON klijent.klijentId=popust.klijentId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + r.getKlijent().getKlijentId() + " AND usluga.uslugaId=" + s.getUsluga().getUslugaId());
                for (Popust p : popusti) {
                    if (p.getUsluga().equals(s.getUsluga())) {

                        p.setBrojRezUsluge(p.getBrojRezUsluge() - 1);
                        prilagodiISacuvajPopust(p);
                    }
                }
            }

        }

        if (r.isPojavljivanje() == true && RezBaza.isPojavljivanje() == false) {

            for (StavkaRezervacije s : r.getStavke()) {
                List<Popust> popusti = broker.vratiSve(new Popust(), " JOIN usluga ON popust.uslugaId=usluga.uslugaId JOIN klijent ON klijent.klijentId=popust.klijentId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + r.getKlijent().getKlijentId() + " AND usluga.uslugaId=" + s.getUsluga().getUslugaId());
                for (Popust p : popusti) {
                    if (p.getUsluga().equals(s.getUsluga())) {

                        p.setBrojRezUsluge(p.getBrojRezUsluge() + 1);
                        prilagodiISacuvajPopust(p);
                    }
                }
            }

        }

        broker.izmeni(r);

    }

    /**
     * Podesava iznos popusta na osnovu broja dolazaka.
     */
    private void prilagodiISacuvajPopust(Popust p) throws Exception {
        int broj = p.getBrojRezUsluge();
        if (broj < 5) {
            p.setPopust(0);
        }
        if (broj >= 5 && broj < 10) {
            p.setPopust(5);
        }

        if (broj >= 10 && broj < 15) {
            p.setPopust(10);
        }
        if (broj >= 15) {
            p.setPopust(15);
        }

        broker.izmeni(p);
    }

}

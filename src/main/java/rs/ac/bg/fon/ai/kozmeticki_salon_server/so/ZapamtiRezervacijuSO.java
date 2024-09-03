/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;

/**
 *
 * @author ninic
 */
public class ZapamtiRezervacijuSO extends OpstaSO {

    @Override
    protected void preduslovi(Object param) throws Exception {

        Rezervacija r = (Rezervacija) param;
        //r.getDatum().before(new Date()) ||
        if ( r.getKlijent() == null || r.getStavke().isEmpty() || r.getUkupnaCena() <= 0) {
            throw new Exception("Sistem ne moze da zapamti rezervaciju");
        }
        
        

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Rezervacija r = (Rezervacija) param;
       
        if (r.getRezervacijaId()==-1) {
            izvrsiOperacijuZaDodavanje(r);
        } else {
            izvrsiOperacijuZaAzuriranje(r);
        }

    }

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

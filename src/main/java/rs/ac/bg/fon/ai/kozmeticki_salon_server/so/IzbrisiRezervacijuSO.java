/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;

/**
 * Klasa koja predstavlja sistemsku operaciju za brisanje rezervacije iz
 * sistema. Ova klasa nasleđuje klasu OpstaSO i implementira njene metode za
 * proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class IzbrisiRezervacijuSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * IzbrisiRezervacijuSO.
     */
    public IzbrisiRezervacijuSO() {

    }

    /**
     * Proverava preduslove za izvršenje operacije brisanja rezervacije.
     *
     * @param param Objekat koji predstavlja rezervaciju koja treba da se
     * izbriše.
     * @throws Exception ako param nije instanca klase Rezervacija ili null
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Rezervacija)) {
            throw new Exception("Sistem ne moze da izbrise rezervaciju");
        }
    }

    /**
     * Izvršava operaciju brisanja rezervacije iz baze podataka. Ova metoda
     * takođe ažurira popuste povezane sa rezervacijom i briše stavke
     * rezervacije.
     *
     * @param param Objekat koji predstavlja rezervaciju koja treba da se
     * izbriše.
     * @throws Exception ako dođe do greške pri brisanju rezervacije ili
     * ažuriranju popusta.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Rezervacija r = (Rezervacija) param;
        List<StavkaRezervacije> sveStavke = r.getStavke();

        for (StavkaRezervacije s : sveStavke) {
            broker.izbrisi((StavkaRezervacije) s);
            List<Popust> popusti = broker.vratiSve(new Popust(), " JOIN usluga ON popust.uslugaId=usluga.uslugaId JOIN klijent ON klijent.klijentId=popust.klijentId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + r.getKlijent().getKlijentId() + " AND usluga.uslugaId=" + s.getUsluga().getUslugaId());
            Popust p = popusti.get(0);
            p.setBrojRezUsluge(p.getBrojRezUsluge() - 1);

            if (p.getBrojRezUsluge() == 0) {
                broker.izbrisi((Popust) p);
            } else {
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

        broker.izbrisi(r);

    }

}

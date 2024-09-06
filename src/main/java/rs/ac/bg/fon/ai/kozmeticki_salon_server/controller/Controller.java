/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.controller;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.forme.ServerskaForma;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.so.*;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.so.UcitajListuTipovaUslugaSO;

/**
 * Klasa koja upravlja operacijama vezanim za poslovnu logiku aplikacije servera
 * za kozmetički salon.
 *
 * Ova klasa koristi Singleton kako bi se obezbedilo da samo jedan primerak
 * Controller-a postoji tokom celokupne aplikacije.
 *
 * @autor Nikolina Baros
 */
public class Controller {

    /**
     * Privatna statička promenljiva koja čuva jedini primer Controller klase.
     */
    private static Controller instance;

    /**
     * Lista menadžera koji su trenutno prijavljeni na sistem.
     *
     */
    private List<Menadzer> menadzeri = new ArrayList<>();
    /**
     *
     * Serverska forma na kojoj se prikazuje status servera i lista prijavljenih
     * menadzera.
     *
     */
    ServerskaForma sf;

    /**
     * Privatni konstruktor za primenu Singleton-a.
     */
    private Controller() {

    }

    /**
     * Vraća jedini primerak klase kontroler tj. instancu. Ako ne postoji kreira
     * je.
     *
     * @return Jedini primerak Controller klase.
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Prijavljuje menadžera na sistem izvrsavajuci Login sistemsku operaciju a
     * zatim dodaje tog menadzera u listu aktivnih menadzera ako je prijava bila
     * uspesna.
     *
     * @param m Menadžer koji se prijavljuje.
     * @return Menadžer koji je prijavljen ili null ako prijava nije uspela.
     * @throws Exception ako dođe do greške tokom prijave.
     */
    public Menadzer login(Menadzer m) throws Exception {

        LoginSO operacija = new LoginSO();
        operacija.izvrsi(m);
        if (operacija.getMenadzer() != null) {

            if (menadzeri.contains(operacija.getMenadzer())) {
                operacija.setMenadzer(null);
            } else {
                menadzeri.add(operacija.getMenadzer());
                sf.ucitajUlogovaneMenadzere(menadzeri);
            }

        }
        return operacija.getMenadzer();

    }

    /**
     * Odjavljuje menadžera iz sistema.
     *
     * @param m Menadžer koji se odjavljuje.
     * @return True ako je odjava uspešna, u suprotnom false
     * @throws Exception ako dođe do greške tokom odjave.
     */
    public boolean logout(Menadzer m) throws Exception {

        if (m != null && menadzeri.contains(m)) {
            menadzeri.remove(m);
            sf.ucitajUlogovaneMenadzere(menadzeri);

            return true;
        }

        return false;

    }

    /**
     * Pronalazi klijente na osnovu zadanih kriterijuma izvrsavajuci sistemsku
     * operaciju NadjiKlijente.
     *
     * @param k Kriterijumi za pretragu klijenata.
     * @return Lista pronađenih klijenata.
     * @throws Exception ako dođe do greške tokom pretrage klijenata.
     */
    public List<Klijent> nadjiKlijente(Klijent k) throws Exception {

        NadjiKlijenteSO operacija = new NadjiKlijenteSO();
        operacija.izvrsi(k);

        return operacija.getKlijenti();

    }

    /**
     * Briše klijenta iz sistema, tj. izvrsava sistemsku operaciju
     * IzbrisiKlijenta.
     *
     * @param k Klijent koji se briše iz sistema.
     * @throws Exception ako dođe do greške tokom brisanja klijenta.
     */
    public void izbrisiKlijenta(Klijent k) throws Exception {
        IzbrisiKlijentaSO operacija = new IzbrisiKlijentaSO();
        operacija.izvrsi(k);

    }

    /**
     * Čuva informacije o klijentu u sistemu izvrsavajuci sistemsku operaciju
     * ZapamtiKlijenta.
     *
     * @param k Klijent koji se čuva.
     * @throws Exception ako dođe do greške tokom čuvanja klijenta.
     */
    public void sacuvajKlijenta(Klijent k) throws Exception {
        ZapamtiKlijentaSO operacija = new ZapamtiKlijentaSO();
        operacija.izvrsi(k);

    }

    /**
     * Pronalazi usluge na osnovu zadanih kriterijuma izvrsavajuci sistemsku
     * operaciju NadjiUsluge.
     *
     * @param u Kriterijumi za pretragu usluga.
     * @return Lista pronađenih usluga.
     * @throws Exception ako dođe do greške tokom pretrage usluga.
     */
    public List<Usluga> nadjiUsluge(Usluga u) throws Exception {

        NadjiUslugeSO operacija = new NadjiUslugeSO();
        operacija.izvrsi(u);

        return operacija.getUsluge();
    }

    /**
     * Briše prosledjenu uslugu iz sistema izvrsavajuci sistemsku operaciju
     * IzbrisiUslugu.
     *
     * @param u Usluga koja se briše.
     * @throws Exception ako dođe do greške tokom brisanja usluge.
     */
    public void izbrisiUslugu(Usluga u) throws Exception {
        IzbrisiUsluguSO operacija = new IzbrisiUsluguSO();
        operacija.izvrsi(u);
    }

    /**
     * Vraća sve tipove usluga iz sistema izvrsavajuci sistemsku operaciju
     * UcitajListuTipovaUsluga.
     *
     * @return Lista svih tipova usluga.
     * @throws Exception ako dođe do greške tokom vraćanja tipova usluga.
     */
    public List<TipUsluge> vratiSveTipoveUsluga() throws Exception {

        UcitajListuTipovaUslugaSO operacija = new UcitajListuTipovaUslugaSO();
        operacija.izvrsi(null);

        return operacija.getTipoviUsluge();

    }

    /**
     * Čuva informacije o usluzi u sistemu izvrsavajuci sistemsku operaciju
     * ZapamtiUslugu.
     *
     * @param u Usluga koja se čuva.
     * @throws Exception ako dođe do greške tokom čuvanja usluge.
     */
    public void sacuvajUslugu(Usluga u) throws Exception {
        ZapamtiUsluguSO operacija = new ZapamtiUsluguSO();
        operacija.izvrsi(u);
    }

    /**
     * Pronalazi rezervacije na osnovu zadatih kriterijuma izvrsavajuci
     * sistemsku operaciju NadjiRezervacije.
     *
     * @param r Kriterijumi za pretragu rezervacija.
     * @return Lista pronađenih rezervacija.
     * @throws Exception ako dođe do greške tokom pretrage rezervacija.
     */
    public List<Rezervacija> nadjiRezervacije(Rezervacija r) throws Exception {

        NadjiRezervacijeSO operacija = new NadjiRezervacijeSO();
        operacija.izvrsi(r);

        return operacija.getRezervacije();

    }

    /**
     * Učitava popuste za određenog klijenta izvrsavajuci sistemsku operaciju
     * UcitajListuPopusta.
     *
     * @param k Klijent za kojeg se učitavaju popusti.
     * @return Lista popusta za klijenta.
     * @throws Exception ako dođe do greške tokom učitavanja popusta.
     */
    public List<Popust> ucitajPopuste(Klijent k) throws Exception {

        UcitajListuPopustaSO operacija = new UcitajListuPopustaSO();
        operacija.izvrsi(k);

        return operacija.getPopusti();
    }

    /**
     * Dodaje novu rezervaciju u sistem izvrsavajuci sistemsku operaciju
     * ZapamtiRezervaciju.
     *
     * @param r Rezervacija koja se dodaje.
     * @throws Exception ako dođe do greške tokom dodavanja rezervacije.
     */
    public void dodajRezervaciju(Rezervacija r) throws Exception {

        ZapamtiRezervacijuSO operacija = new ZapamtiRezervacijuSO();
        operacija.izvrsi(r);

    }

    /**
     * Briše rezervaciju iz sistema izvrsavajuci sistemsku operaciju
     * IzbrisiRezervaciju.
     *
     * @param r Rezervacija koja se briše.
     * @throws Exception ako dođe do greške tokom brisanja rezervacije.
     */
    public void obrisiRezervaciju(Rezervacija r) throws Exception {
        IzbrisiRezervacijuSO operacija = new IzbrisiRezervacijuSO();
        operacija.izvrsi(r);
    }

    /**
     * Učitava informacije o klijentu izvrsavajuci sistemsku operaciju
     * UcitajKlijenta.
     *
     * @param k Klijent čije se informacije učitavaju.
     * @return Klijent sa učitanim informacijama.
     * @throws Exception ako dođe do greške tokom učitavanja klijenta.
     */
    public Klijent ucitajKlijenta(Klijent k) throws Exception {

        UcitajKlijentaSO operacija = new UcitajKlijentaSO();
        operacija.izvrsi(k);

        return operacija.getK();
    }

    /**
     * Učitava informacije o usluzi izvrsavajuci sistemsku operaciju
     * UcitajUslugu.
     *
     * @param u Usluga čije se informacije učitavaju.
     * @return Usluga sa učitanim informacijama.
     * @throws Exception ako dođe do greške tokom učitavanja usluge.
     */
    public Usluga ucitajUslugu(Usluga u) throws Exception {
        UcitajUsluguSO operacija = new UcitajUsluguSO();
        operacija.izvrsi(u);

        return operacija.getU();
    }

    /**
     * Vraća sve usluge iz sistema izvrsavajuci sistemsku operaciju
     * UcitajListuUsluga.
     *
     * @return Lista svih usluga.
     * @throws Exception ako dođe do greške tokom vraćanja usluga.
     */
    public List<Usluga> vratiSveUsluge() throws Exception {

        UcitajListuUslugaSO operacija = new UcitajListuUslugaSO();
        operacija.izvrsi(null);

        return operacija.getUsluge();

    }

    /**
     * Vraća sve klijente iz sistema izvrsavajuci sistemsku operaciju
     * VratiListuKlijenata.
     *
     * @return Lista svih klijenata.
     * @throws Exception ako dođe do greške tokom vraćanja klijenata.
     */
    public List<Klijent> vratiSveKlijente() throws Exception {

        UcitajListuKlijenataSO operacija = new UcitajListuKlijenataSO();
        operacija.izvrsi(null);

        return operacija.getKlijenti();

    }

    /**
     * Učitava informacije o rezervaciji izvrsavajuci sistemsku operaciju
     * UcitajRezervaciju.
     *
     * @param r Rezervacija čije se informacije učitavaju.
     * @return Rezervacija sa učitanim informacijama.
     * @throws Exception ako dođe do greške tokom učitavanja rezervacije.
     */
    public Rezervacija ucitajRezervaciju(Rezervacija r) throws Exception {
        UcitajRezervacijuSO operacija = new UcitajRezervacijuSO();
        operacija.izvrsi(r);

        return operacija.getR();

    }

    /**
     * Vraća listu svih menadžera koji su trenutno prijavljeni.
     *
     * @return Lista prijavljenih menadžera
     */
    public List<Menadzer> getMenadzeri() {
        return menadzeri;
    }

    /**
     * Postavlja listu menadžera koji su trenutno prijavljeni.
     *
     * @param menadzeri Nova lista menadžera koja se postavlja.
     */
    public void setMenadzeri(List<Menadzer> menadzeri) {
        this.menadzeri = menadzeri;
    }

    /**
     * Vraća serversku formu koja je povezana sa ovim kontrolerom kao instancu
     * klase ServerskaForma.
     *
     * @return ServerskaForma koja je povezana sa ovim kontrolerom.
     */
    public ServerskaForma getSf() {
        return sf;
    }

    /**
     * Postavlja serversku formu koja je povezana sa ovim kontrolerom.
     *
     * @param sf Nova serverska forma koja se postavlja kao instanca klase
     * ServerskaForma.
     */
    public void setSf(ServerskaForma sf) {
        this.sf = sf;
    }

    /**
     * Vraća sve statistike iz sistema izvrsavajuci sistemsku operaciju
     * UcitajListuStatistika.
     *
     * @return Lista svih statistika.
     * @throws Exception ako dođe do greške tokom učitavanja statistika.
     */
    public List<Statistika> vratiSveStatistike() throws Exception {

        UcitajListuStatistikaSO operacija = new UcitajListuStatistikaSO();
        operacija.izvrsi(null);

        return operacija.getLista();

    }

    /**
     * Generise i cuva podatke o statistici za trenutnu godinu izvrsavajuci
     * sistemsku operaciju ZapamtiStatistike.
     *
     *
     * @throws Exception ako dođe do greške tokom čuvanja statistika.
     */
    public void zapamtiStatistike() throws Exception {

        ZapamtiStatistikeSO operacija = new ZapamtiStatistikeSO();
        operacija.izvrsi(null);

    }

}

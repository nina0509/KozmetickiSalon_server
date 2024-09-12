/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;

/**
 * Klasa koja predstavlja sistemsku operaciju za čuvanje ili ažuriranje klijenta
 * u bazi podataka. Nasleđuje klasu OpstaSO i implementira njene metode za
 * proveru preduslova i izvršenje operacije.
 *
 * @author Nikolina Baros
 */
public class ZapamtiKlijentaSO extends OpstaSO {

    /**
     * Podrazumevani konstruktor bez parametara, kreira novu instancu klase
     * ZapamtiKlijentaSO.
     */
    public ZapamtiKlijentaSO() {
    }

    /**
     * Konstruktor sa parametrima, kreira novu instancu klase
     * ZapamtiKlijentaSO i postavlja broker na zadatu vrednost.
     * 
     * @param broker Novi broker baze podataka.
     */
   
     public ZapamtiKlijentaSO(Repozitorijum broker) {
         this.broker=broker;
    }
    
    /**
     * Proverava preduslove za čuvanje ili ažuriranje klijenta. Ako klijent nije
     * validan ili ne ispunjava preduslove, baca izuzetak.
     *
     * @param param Objekat koji predstavlja klijenta koji treba da se sačuva
     * ili ažurira.
     * @throws Exception Ako je parametar null ili je bilo sta od atributa
     * prosledjenog parametra null ili prazan string ili ako broj telefona nije
     * u dobrom formatu.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {

        if(param==null || !(param instanceof Klijent))throw new Exception("Sistem ne moze da zapamti klijenta");

        Klijent k = (Klijent) param;
        if ( k.getIme() == null || k.getPrezime() == null  || k.getBrTel() == null || k.getDatRodj() == null) {
            throw new Exception("Sistem ne moze da zapamti klijenta");
        }

        String regex = "^(\\+\\d{1,3})?0?6[0-9]\\d{6,7}$";
        k.setBrTel(k.getBrTel().replace(" ", ""));
        if (!k.getBrTel().matches(regex)) {
            throw new Exception("Sistem ne moze da zapamti klijenta");

        }

    }

    /**
     * Izvršava operaciju čuvanja ili ažuriranja klijenta u bazi podataka. Ako
     * klijent već postoji u bazi, ažurira njegove podatke, u suprotnom čuva
     * novog klijenta.
     *
     * @param param Objekt koji predstavlja klijenta koji treba da se sačuva ili
     * ažurira.
     * @throws Exception Ako dođe do greške prilikom čuvanja ili ažuriranja
     * klijenta.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {

        Klijent k = (Klijent) param;
        k.setBrTel(k.getBrTel().replace(" ", ""));
        List<Klijent> klijenti = broker.vratiSve(new Klijent(), " WHERE klijent.klijentId=" + k.getKlijentId());

        if (klijenti.isEmpty()) {
            broker.sacuvaj((Klijent) param);
        } else {

            broker.izmeni((Klijent) param);
        }

    }

}

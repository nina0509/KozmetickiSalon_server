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
 *
 * @author ninic
 */
public class Controller {

    private static Controller instance;
    private List<Menadzer> menadzeri=new ArrayList<>();
    ServerskaForma sf;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Menadzer login(Menadzer m) throws Exception {

        LoginSO operacija = new LoginSO();
        operacija.izvrsi(m, null);
        if(operacija.getMenadzer()!=null)
        {
            
            if(menadzeri.contains(operacija.getMenadzer()))
            {
            operacija.setMenadzer(null);
            }
            else {
                menadzeri.add(operacija.getMenadzer());
                sf.ucitajUlogovaneMenadzere(menadzeri);
            }
     
        }
        return operacija.getMenadzer();
        

    }
    
     public boolean logout(Menadzer m) throws Exception {

         if(m!=null && menadzeri.contains(m))
         {
         menadzeri.remove(m);
         sf.ucitajUlogovaneMenadzere(menadzeri);
       
         return true;
         }
        
        return false;

    }

    public List<Klijent> nadjiKlijente(Klijent k) throws Exception {

        NadjiKlijenteSO operacija = new NadjiKlijenteSO();
        operacija.izvrsi(k, null);

        return operacija.getKlijenti();

    }

    public void izbrisiKlijenta(Klijent k) throws Exception {
        IzbrisiKlijentaSO operacija = new IzbrisiKlijentaSO();
        operacija.izvrsi(k, null);

    }

    public void sacuvajKlijenta(Klijent k) throws Exception {
        ZapamtiKlijentaSO operacija = new ZapamtiKlijentaSO();
        operacija.izvrsi(k, null);

    }

    public List<Usluga> nadjiUsluge(Usluga u) throws Exception {

        NadjiUslugeSO operacija = new NadjiUslugeSO();
        operacija.izvrsi(u, null);

        return operacija.getUsluge();
    }

    public void izbrisiUslugu(Usluga u) throws Exception {
        IzbrisiUsluguSO operacija = new IzbrisiUsluguSO();
        operacija.izvrsi(u, null);
    }

    public List<TipUsluge> vratiSveTipoveUsluga() throws Exception {

        UcitajListuTipovaUslugaSO operacija = new UcitajListuTipovaUslugaSO();
        operacija.izvrsi(null, null);

        return operacija.getTipoviUsluge();

    }

    public void sacuvajUslugu(Usluga u) throws Exception {
        ZapamtiUsluguSO operacija = new ZapamtiUsluguSO();
        operacija.izvrsi(u, null);
    }

   

    public List<Rezervacija> nadjiRezervacije(Rezervacija r) throws Exception {

        NadjiRezervacijeSO operacija = new NadjiRezervacijeSO();
        operacija.izvrsi(r, null);

        return operacija.getRezervacije();

    }

    public List<Popust> ucitajPopuste(Klijent k) throws Exception {

        UcitajListuPopustaSO operacija = new UcitajListuPopustaSO();
        operacija.izvrsi(k, null);

        return operacija.getPopusti();
    }

    public void dodajRezervaciju(Rezervacija r) throws Exception {

        ZapamtiRezervacijuSO operacija = new ZapamtiRezervacijuSO();
        operacija.izvrsi(r, null);

    }

    public void obrisiRezervaciju(Rezervacija r) throws Exception {
        IzbrisiRezervacijuSO operacija = new IzbrisiRezervacijuSO();
        operacija.izvrsi(r, null);
    }

    public Klijent ucitajKlijenta(Klijent k) throws Exception {

        UcitajKlijentaSO operacija=new UcitajKlijentaSO();
        operacija.izvrsi(k,null);

        return operacija.getK();
    }

    public Usluga ucitajUslugu(Usluga u) throws Exception {
        UcitajUsluguSO operacija=new UcitajUsluguSO();
        operacija.izvrsi(u,null);

        return operacija.getU();    }

    public List<Usluga> vratiSveUsluge() throws Exception {

        UcitajListuUslugaSO operacija = new UcitajListuUslugaSO();
        operacija.izvrsi(null, null);

        return operacija.getUsluge();
        

    }

    public List<Klijent> vratiSveKlijente() throws Exception {

        UcitajListuKlijenataSO operacija = new UcitajListuKlijenataSO();
        operacija.izvrsi(null, null);

        return operacija.getKlijenti();


    }

    public Rezervacija ucitajRezervaciju(Rezervacija r) throws Exception {
    UcitajRezervacijuSO operacija=new UcitajRezervacijuSO();
    operacija.izvrsi(r,null);

        return operacija.getR(); 
    
    }

    public List<Menadzer> getMenadzeri() {
        return menadzeri;
    }

    public void setMenadzeri(List<Menadzer> menadzeri) {
        this.menadzeri = menadzeri;
    }

    public ServerskaForma getSf() {
        return sf;
    }

    public void setSf(ServerskaForma sf) {
        this.sf = sf;
    }

    public List<Statistika> vratiSveStatistike() throws Exception {

        UcitajListuStatistikaSO operacija = new  UcitajListuStatistikaSO();
        operacija.izvrsi(null, null);

        return operacija.getLista();


    }

    public void zapamtiStatistike() throws Exception {

        
        ZapamtiStatistikeSO operacija=new ZapamtiStatistikeSO();
         operacija.izvrsi(null, null);


    }
    
  
    
    

}

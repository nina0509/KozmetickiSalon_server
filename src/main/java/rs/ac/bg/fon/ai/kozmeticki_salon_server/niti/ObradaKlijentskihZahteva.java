/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.niti;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.controller.Controller;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.transfer.*;


/**
 *
 * @author ninic
 */
public class ObradaKlijentskihZahteva extends Thread {

    Socket socket;
    Primalac primalac;
    Posiljalac posiljalac;
    boolean kraj = false;
   

    public ObradaKlijentskihZahteva(Socket socket) {

        this.socket = socket;
        posiljalac = new Posiljalac(socket);
        primalac = new Primalac(socket);

    }

    @Override
    public void run() {

        while (!kraj) {
            try {
                Zahtev zahtev = (Zahtev) primalac.primi();
                Odgovor odgovor = new Odgovor();
                if (zahtev == null) {
                    continue;
                }

                switch (zahtev.getOperacija()) {

                    case LOGIN:
                        Menadzer m = (Menadzer) zahtev.getParametar();
                        m = Controller.getInstance().login(m);
                        odgovor.setOdgovor(m);
                        
                        break;

                    case UCITAJ_KLIJENTA:
                       try {
                        Klijent nadji = (Klijent) zahtev.getParametar();
                        nadji = Controller.getInstance().ucitajKlijenta(nadji);
                        odgovor.setOdgovor(nadji);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;
                    case NADJI_KLIJENTE:
                        Klijent nadji1 = (Klijent) zahtev.getParametar();
                        List<Klijent> klijenti = Controller.getInstance().nadjiKlijente(nadji1);
                        odgovor.setOdgovor(klijenti);
                        break;

                    case IZBRISI_KLIJENTA:
                        try {
                        Klijent k = (Klijent) zahtev.getParametar();
                        Controller.getInstance().izbrisiKlijenta(k);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;

                    case ZAPAMTI_KLIJENTA:
                         try {
                        Klijent k = (Klijent) zahtev.getParametar();
                        Controller.getInstance().sacuvajKlijenta(k);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;
                    case NADJI_USLUGE:
                        Usluga u = (Usluga) zahtev.getParametar();
                        List<Usluga> usluge = Controller.getInstance().nadjiUsluge(u);
                        odgovor.setOdgovor(usluge);
                        break;

                    case UCITAJ_USLUGU:
                       try {
                        Usluga nadjiU = (Usluga) zahtev.getParametar();
                        nadjiU = Controller.getInstance().ucitajUslugu(nadjiU);
                        odgovor.setOdgovor(nadjiU);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;
                    case IZBRISI_USLUGU:
                        try {
                        Usluga u1 = (Usluga) zahtev.getParametar();
                        Controller.getInstance().izbrisiUslugu(u1);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;

                    case UCITAJ_LISTU_TIPOVA_USLUGE:
                        List<TipUsluge> tipovi = Controller.getInstance().vratiSveTipoveUsluga();
                        odgovor.setOdgovor(tipovi);
                        break;
                        
                         case UCITAJ_SVE_STATISTIKE:
                        List<Statistika> stat = Controller.getInstance().vratiSveStatistike();
                        odgovor.setOdgovor(stat);
                        break;

                    case ZAPAMTI_USLUGU:
                         try {
                        Usluga u2 = (Usluga) zahtev.getParametar();
                        Controller.getInstance().sacuvajUslugu(u2);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;

                    case NADJI_REZERVACIJE:
                        Rezervacija pretraga = (Rezervacija) zahtev.getParametar();
                        List<Rezervacija> rezervacije = Controller.getInstance().nadjiRezervacije(pretraga);
                        odgovor.setOdgovor(rezervacije);
                        break;
                        
                    case UCITAJ_REZERVACIJU:
                       try {
                        Rezervacija nadjiRez = (Rezervacija) zahtev.getParametar();
                        nadjiRez = Controller.getInstance().ucitajRezervaciju(nadjiRez);
                        odgovor.setOdgovor(nadjiRez);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }
                       
                       break;
                    case UCITAJ_LISTU_POPUSTA:
                        Klijent k = (Klijent) zahtev.getParametar();
                        List<Popust> popusti = Controller.getInstance().ucitajPopuste(k);
                        odgovor.setOdgovor(popusti);
                        break;

                    case UCITAJ_LISTU_KLIJENATA:
                        List<Klijent> klijentiSve = Controller.getInstance().vratiSveKlijente();
                        odgovor.setOdgovor(klijentiSve);
                        break;

                    case UCITAJ_LISTU_USLUGA:
                        List<Usluga> uslugeSve = Controller.getInstance().vratiSveUsluge();
                        odgovor.setOdgovor(uslugeSve);
                        break;

                    case ZAPAMTI_REZERVACIJU:
                         try {
                        Rezervacija r = (Rezervacija) zahtev.getParametar();
                        System.out.println(r);
                        Controller.getInstance().dodajRezervaciju(r);

                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;

                    case IZBRISI_REZERVACIJU:
                        
                        try {
                        Rezervacija r = (Rezervacija) zahtev.getParametar();
                        Controller.getInstance().obrisiRezervaciju(r);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }

                    break;
                    
                    case LOGOUT:
                        
                        Menadzer m1 = (Menadzer) zahtev.getParametar();
                        boolean uspeh = Controller.getInstance().logout(m1);
                        odgovor.setOdgovor(uspeh);
                        if(uspeh)prekini();
                        break;
                        
                        
                         case ZAPAMTI_STATISTIKE:
                         try {
                        
                        Controller.getInstance().zapamtiStatistike();
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e.getMessage());
                    }

                    break;

                    default:
                        System.out.println("GRESKA, NEPOSTOJECA OPERACIJA");
                }

                posiljalac.posalji(odgovor);
            } catch (Exception ex) {
                Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void prekini() {
        try {
           
            kraj = true;
            interrupt(); 
            socket.close();
        } catch (IOException ex) {
          //  Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    

}

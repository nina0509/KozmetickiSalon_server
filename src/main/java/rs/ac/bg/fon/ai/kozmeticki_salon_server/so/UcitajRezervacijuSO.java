/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;

/**
 *
 * @author ninic
 */
public class UcitajRezervacijuSO extends OpstaSO {
    Rezervacija r;
    @Override
    protected void preduslovi(Object param) throws Exception {
       Rezervacija parametar = (Rezervacija) param;
        if (parametar == null) {
            throw new Exception("Sistem ne moze da ucita rezervaciju");
        }    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

         
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

    public Rezervacija getR() {
        return r;
    }

    public void setR(Rezervacija r) {
        this.r = r;
    }
    
    
    
 }

    


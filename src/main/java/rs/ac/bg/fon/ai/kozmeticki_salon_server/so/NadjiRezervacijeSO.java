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
public class NadjiRezervacijeSO extends OpstaSO {

    List<Rezervacija> rezervacije;

    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param != null && !(param instanceof Rezervacija)) {
            throw new Exception("Sistem ne moze da nadje rezervacije po zadatoj vrednosti");
        }
        
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Rezervacija r=(Rezervacija)param;
        
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId";
        String uslov1 = null;

        if (r != null && r.getKlijent()!=null && !r.getKlijent().getIme().isBlank()) {
            uslov1 = " WHERE klijent.ime LIKE '%" + r.getKlijent().getIme() + "%'";
        }

        if (r != null && r.getDatum()!=null) {
               java.sql.Date datum1 = new java.sql.Date(r.getDatum().getTime()); 
            if (uslov1 != null) {

                uslov1 += " AND rezervacija.datum='" +datum1+"'" ;
            } else {
                uslov1 = " WHERE rezervacija.datum='" +datum1+"'" ;
            }
        }

        if (uslov1 != null) {
            uslov += uslov1;
        }
        uslov+=" ORDER BY rezervacija.datum DESC";
        System.out.println(uslov);
       
       rezervacije = broker.vratiSve(new Rezervacija(), uslov);
        
        
        
        for (Rezervacija nova : rezervacije) {
            List<StavkaRezervacije> stavke = broker.vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + nova.getRezervacijaId());
            nova.setStavke(stavke);

        }

    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;

/**
 *
 * @author ninic
 */
public class NadjiKlijenteSO extends OpstaSO {

    List<Klijent> klijenti;

    @Override
    protected void preduslovi(Object param) throws Exception {

       
        if (param != null && !(param instanceof Klijent)) {
            throw new Exception("Sistem ne moze da nadje klijente po zadatoj vrednosti");
        }
        
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Klijent k=(Klijent)param;
        String uslov=null;
        if(k==null) { 
            klijenti = broker.vratiSve(new Klijent(), null);
            return;
        }
        
        if(!k.getIme().isBlank() && k.getIme()!=null)
        {
        uslov=" WHERE klijent.ime LIKE '%"+k.getIme()+"%'";
        }
        
        if(!k.getPrezime().isBlank() && k.getPrezime()!=null)
        {
        
        if(uslov!=null)
        {
         uslov+=" AND klijent.prezime LIKE '%"+k.getPrezime()+"%'"; 
        }
        else
        { 
         uslov=" WHERE klijent.prezime LIKE '%"+k.getPrezime()+"%'"; 
        }
         }
        System.out.println(uslov);
          klijenti = broker.vratiSve(new Klijent(), uslov);
        
    }

    public List<Klijent> getKlijenti() {
        return klijenti;
    }

    public void setKlijenti(List<Klijent> klijenti) {
        this.klijenti = klijenti;
    }

}

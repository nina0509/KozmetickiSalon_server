/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 *
 * @author ninic
 */
public class UcitajUsluguSO extends OpstaSO {
     Usluga u;
    @Override
    protected void preduslovi(Object param) throws Exception {
        Usluga parametar = (Usluga) param;
        if (parametar == null) {
            throw new Exception("Sistem ne moze da ucita uslugu");
        }    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
     
         Usluga par = (Usluga) param;
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId WHERE usluga.uslugaId="+par.getUslugaId();
        
         List<Usluga> usluge=broker.vratiSve(new Usluga(), uslov);
         
          if (usluge.isEmpty()) {
            u = null;
        } else {
            u = usluge.get(0);
        }
    }

    public Usluga getU() {
        return u;
    }

    public void setU(Usluga u) {
        this.u = u;
    }
    
    
    
    
}

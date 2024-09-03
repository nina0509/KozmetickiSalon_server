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
public class UcitajKlijentaSO extends OpstaSO {

    Klijent k;

    @Override
    protected void preduslovi(Object param) throws Exception {

        Klijent parametar = (Klijent) param;
        if (parametar == null) {
            throw new Exception("Sistem ne moze da ucita klijenta");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Klijent parametar = (Klijent) param;
        List<Klijent> klijenti = broker.vratiSve(new Klijent(), " WHERE klijent.klijentId=" + parametar.getKlijentId());
        if (klijenti.isEmpty()) {
            k = null;
        } else {
            k = klijenti.get(0);
        }

    }

    public Klijent getK() {
        return k;
    }

    public void setK(Klijent k) {
        this.k = k;
    }

}

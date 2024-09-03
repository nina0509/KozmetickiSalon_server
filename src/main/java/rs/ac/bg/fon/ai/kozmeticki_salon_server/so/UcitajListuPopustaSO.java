/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;

/**
 *
 * @author ninic
 */
public class UcitajListuPopustaSO extends OpstaSO {

    List<Popust> popusti;

    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Klijent k = (Klijent) param;
        popusti = broker.vratiSve(new Popust(), " JOIN klijent ON klijent.klijentId=popust.klijentId JOIN usluga ON usluga.uslugaId=popust.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + k.getKlijentId());
    }

    public List<Popust> getPopusti() {
        return popusti;
    }

    public void setPopusti(List<Popust> popusti) {
        this.popusti = popusti;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;

/**
 *
 * @author ninic
 */
public class UcitajListuTipovaUslugaSO extends OpstaSO {

    List<TipUsluge> tipoviUsluge;

    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        tipoviUsluge = broker.vratiSve(new TipUsluge(), null);

    }

    public List<TipUsluge> getTipoviUsluge() {
        return tipoviUsluge;
    }

    public void setTipoviUsluge(List<TipUsluge> tipoviUsluge) {
        this.tipoviUsluge = tipoviUsluge;
    }

}

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
public class NadjiUslugeSO extends OpstaSO {

    List<Usluga> usluge;

    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param != null && !(param instanceof Usluga)) {
            throw new Exception("Sistem ne moze da nadje usluge po zadatoj vrednosti");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        Usluga u = (Usluga) param;
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        String uslov1 = null;

        if (u != null && !u.getNaziv().isBlank() && u.getNaziv() != null) {
            uslov1 = " WHERE usluga.naziv LIKE '%" + u.getNaziv() + "%'";
        }

        if (u != null && u.getTip() != null) {

            if (uslov1 != null) {
                uslov1 += " AND usluga.tipId=" + u.getTip().getTipId();
            } else {
                uslov1 = " WHERE usluga.tipId=" + u.getTip().getTipId();
            }
        }

        if (uslov1 != null) {
            uslov += uslov1;
        }
        System.out.println(uslov);
        usluge = broker.vratiSve(new Usluga(), uslov);

    }

    public List<Usluga> getUsluge() {
        return usluge;
    }

    public void setUsluge(List<Usluga> usluge) {
        this.usluge = usluge;
    }

}

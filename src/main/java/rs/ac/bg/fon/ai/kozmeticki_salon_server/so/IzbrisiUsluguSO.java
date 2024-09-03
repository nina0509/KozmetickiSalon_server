/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;



/**
 *
 * @author ninic
 */
public class IzbrisiUsluguSO extends OpstaSO {

    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Usluga)) {
            throw new Exception("Sistem ne moze da izbrise uslugu");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        broker.izbrisi((Usluga) param);
    }
}

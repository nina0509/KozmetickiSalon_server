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
public class ZapamtiUsluguSO extends OpstaSO {

    @Override
    protected void preduslovi(Object param) throws Exception {

        Usluga u = (Usluga) param;
        if (u.getNaziv().isBlank() || u.getNaziv() == null || u.getTrajanje() < 0 || u.getTrajanje()>72000 || u.getCena() < 0 || u.getTip()==null) {
            throw new Exception("Sistem ne moze da zapamti klijenta");
        }
        
        

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        broker.sacuvaj((Usluga) param);
    }

}

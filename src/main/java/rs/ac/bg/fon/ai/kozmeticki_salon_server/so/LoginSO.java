/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Menadzer;

/**
 *
 * @author ninic
 */
public class LoginSO extends OpstaSO {

    Menadzer menadzer;

    @Override
    protected void preduslovi(Object param) throws Exception {

        Menadzer m = (Menadzer) param;
        if (param == null || !(param instanceof Menadzer)) {
            throw new Exception("Sistem nije ulogovao menadzera");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        List<Menadzer> sviMenadzeri = broker.vratiSve((Menadzer) param, null);

        for (Menadzer m : sviMenadzeri) {

            if (m.equals((Menadzer) param)) {

                menadzer = m;
                return;
            }

        }
        menadzer = null;

    }

    public Menadzer getMenadzer() {
        return menadzer;
    }

    public void setMenadzer(Menadzer menadzer) {
        this.menadzer = menadzer;
    }
    
    

}

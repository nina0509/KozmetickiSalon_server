/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.DbRepozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;


/**
 *
 * @author ninic
 */
public abstract class OpstaSO {

    protected final Repozitorijum broker;

    public OpstaSO() {
        this.broker = new DbRepozitorijumGenericki();
    }

    public final void izvrsi(Object objekat, String kljuc) throws Exception {

        try {

            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat, kljuc);
            potvrdiTransakciju();
        } catch (Exception ex) {
            ponistiTransakciju();
            throw ex;
        } finally {
            ugasiKonekciju();
        }

    }

    protected abstract void preduslovi(Object param) throws Exception;

    protected abstract void izvrsiOperaciju(Object param, String kljuc) throws Exception;

    private void zapocniTransakciju() throws Exception {
        ((DbRepozitorijum) broker).uspostaviKonekciju();
    }

    private void potvrdiTransakciju() throws Exception {
        ((DbRepozitorijum) broker).potvrdiTransakciju();
    }

    private void ponistiTransakciju() throws Exception {
        ((DbRepozitorijum) broker).ponistiTransakciju();
    }

    private void ugasiKonekciju() throws Exception {

        ((DbRepozitorijum) broker).raskiniKonekciju();
    }

}

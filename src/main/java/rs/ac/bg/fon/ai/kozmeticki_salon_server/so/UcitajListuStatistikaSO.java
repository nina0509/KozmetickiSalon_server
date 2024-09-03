/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;


import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Statistika;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaStatistike;

/**
 *
 * @author ninic
 */
public class UcitajListuStatistikaSO extends OpstaSO {
    
    List<Statistika> lista=new ArrayList<>();

    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        lista=broker.vratiSve(new Statistika(), kljuc);
        
         for (Statistika nova : lista) {
            List<StavkaStatistike> stavke = broker.vratiSve(new StavkaStatistike(), " JOIN statistika ON statistika.godina=stavkastatistike.godina JOIN usluga ON stavkastatistike.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE statistika.godina=" + nova.getGodina());
            nova.setStavke(stavke);

        }

    }

    public List<Statistika> getLista() {
        return lista;
    }

    public void setLista(List<Statistika> lista) {
        this.lista = lista;
    }
    
    
    
    
}

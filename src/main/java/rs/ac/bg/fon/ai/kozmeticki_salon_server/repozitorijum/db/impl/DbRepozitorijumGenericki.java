/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl;


import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.DbConnectionFactory;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.DbRepozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.OpstiDomenskiObjekat;

/**
 * Klasa koja pruža generičku implementaciju za rad sa podacima u bazi
 * koristeći opšti domenski objekat.
 * 
 * Ova klasa koristi Statement objekat za izvršavanje SQL upita za različite operacije
 * kao što su preuzimanje, čuvanje, izmena i brisanje podataka u bazi.
 * 
 * 
 * @author Nikolina Baros
 */
public class DbRepozitorijumGenericki implements DbRepozitorijum<OpstiDomenskiObjekat> {

    
     /**
     * Vraća sve podatke iz baze koji odgovaraju datom parametru i uslovu.
     * 
     * @param param Opšti domenski objekat koji sadrži informacije o tabeli i kolonama.
     * @param uslov Dodatni uslov za filtriranje rezultata (može biti null).
     * @return Lista objekata tipa OpstiDomenskiObjekat koji odgovaraju pretrazi.
     * @throws Exception Ako dođe do greške tokom pretrage.
     */
    @Override
    public List<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat param, String uslov) throws Exception {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        String upit = "SELECT * FROM " + param.vratiNazivTabele();

        if (uslov != null) { 
            upit += uslov;
        }
       
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);
        lista = param.vratiListu(rs);
        rs.close();
        st.close();

        return lista;

    }

      /**
     * Čuva dati objekat u bazi podataka.
     * 
     * @param param Opšti domenski objekat koji sadrži informacije o tabeli i kolonama.
     * @throws Exception Ako dođe do greške tokom čuvanja.
     */
    @Override
    public void sacuvaj(OpstiDomenskiObjekat param) throws Exception {

        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaInsert() + ") VALUES (" + param.vratiVrednostiZaInsert() + ")";
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();

    }

     /**
     * Menja podatke postojećeg objekta u bazi podataka.
     * 
     * @param param Opšti domenski objekat sa izmenjenim podacima.
     * @throws Exception Ako dođe do greške tokom izmene.
     */
    @Override
    public void izmeni(OpstiDomenskiObjekat param) throws Exception {

        String upit = "UPDATE " + param.vratiNazivTabele() + " SET " + param.vratiVrednostiZaUpdate() + " WHERE " + param.vratiPrimarniKljuc();
       
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();

    }

    /**
     * Briše dati objekat iz baze podataka.
     * 
     * @param param Opšti domenski objekat koji sadrži informacije o tabeli i primarnom ključu.
     * @throws Exception Ako dođe do greške tokom brisanja.
     */
    @Override
    public void izbrisi(OpstiDomenskiObjekat param) throws Exception {

        String upit = "DELETE FROM " + param.vratiNazivTabele() + " WHERE " + param.vratiPrimarniKljuc();
        
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    
    /**
     * Čuva dati objekat u bazi podataka i vraća njegov primarni ključ.
     * 
     * @param param Opšti domenski objekat koji sadrži informacije o tabeli i kolonama.
     * @return Primarni ključ sačuvanog objekta.
     * @throws Exception Ako dođe do greške tokom čuvanja.
     */
    @Override
    public int sacuvajVratiPK(OpstiDomenskiObjekat param) throws Exception {

        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaInsert() + ") VALUES (" + param.vratiVrednostiZaInsert() + ")";
        
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();
        int key = 0;
        if (rs != null && rs.next()) {
            key = (int) rs.getLong(1);
        }

        st.close();
        return key;
    }


  

}

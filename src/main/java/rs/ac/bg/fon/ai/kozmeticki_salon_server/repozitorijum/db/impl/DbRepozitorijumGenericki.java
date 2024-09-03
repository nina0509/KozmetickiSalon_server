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
 *
 * @author ninic
 */
public class DbRepozitorijumGenericki implements DbRepozitorijum<OpstiDomenskiObjekat> {

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

    @Override
    public void sacuvaj(OpstiDomenskiObjekat param) throws Exception {

        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaInsert() + ") VALUES (" + param.vratiVrednostiZaInsert() + ")";
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();

    }

    @Override
    public void izmeni(OpstiDomenskiObjekat param) throws Exception {

        String upit = "UPDATE " + param.vratiNazivTabele() + " SET " + param.vratiVrednostiZaUpdate() + " WHERE " + param.vratiPrimarniKljuc();
       
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();

    }

    @Override
    public void izbrisi(OpstiDomenskiObjekat param) throws Exception {

        String upit = "DELETE FROM " + param.vratiNazivTabele() + " WHERE " + param.vratiPrimarniKljuc();
        
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

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



    /**
     *
     * @param klijent
     * @return
     * @throws SQLException
     */
  


}

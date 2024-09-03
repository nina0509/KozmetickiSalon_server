/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db;

import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;


/**
 *
 * @author ninic
 */
public interface DbRepozitorijum<T> extends Repozitorijum<T> {

    default public void uspostaviKonekciju() throws Exception {
        if (DbConnectionFactory.getInstance().getConnection() == null || DbConnectionFactory.getInstance().getConnection().isClosed()) {
            DbConnectionFactory.getInstance().povezi();
        }
    }

    default public void raskiniKonekciju() throws Exception {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    default public void potvrdiTransakciju() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    default public void ponistiTransakciju() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }

}

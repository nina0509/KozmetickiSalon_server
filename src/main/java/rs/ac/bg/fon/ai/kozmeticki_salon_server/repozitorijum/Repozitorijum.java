/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum;


import java.util.List;

/**
 *
 * @author ninic
 * @param <T>
 */

public interface Repozitorijum<T> {

    List<T> vratiSve(T param, String uslov) throws Exception;

    void sacuvaj(T param) throws Exception;

    int sacuvajVratiPK(T param) throws Exception;

    void izmeni(T param) throws Exception;

    void izbrisi(T param) throws Exception;

}

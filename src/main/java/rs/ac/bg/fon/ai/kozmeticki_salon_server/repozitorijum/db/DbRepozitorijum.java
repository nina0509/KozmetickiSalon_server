/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db;

import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;

/**
 * Interfejs koji proširuje interfejs Repozitorijum i pruža dodatne metode za
 * upravljanje konekcijama i transakcijama u bazi podataka.
 *
 * Ovaj interfejs definiše metode za uspostavljanje i prekidanje konekcije sa
 * bazom podataka, kao i za potvrđivanje i poništavanje transakcija.
 *
 *
 * @param <T> Tip objekta sa kojim se radi u bazi podataka.
 * @author Nikolina Baros
 */
public interface DbRepozitorijum<T> extends Repozitorijum<T> {

    /**
     * Uspostavlja konekciju sa bazom podataka ako već nije uspostavljena.
     *
     * @throws Exception Ako dođe do greške tokom uspostavljanja konekcije.
     */
    default public void uspostaviKonekciju() throws Exception {
        if (DbConnectionFactory.getInstance().getConnection() == null || DbConnectionFactory.getInstance().getConnection().isClosed()) {
            DbConnectionFactory.getInstance().povezi();
        }
    }

    /**
     * Prekida konekciju sa bazom podataka.
     *
     * @throws Exception Ako dođe do greške tokom prekidanja konekcije.
     */
    default public void raskiniKonekciju() throws Exception {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    /**
     * Potvrđuje trenutnu transakciju u bazi podataka.
     *
     * @throws Exception Ako dođe do greške tokom potvrđivanja transakcije.
     */
    default public void potvrdiTransakciju() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    /**
     * Poništava trenutnu transakciju u bazi podataka.
     *
     * @throws Exception Ako dođe do greške tokom poništavanja transakcije.
     */
    default public void ponistiTransakciju() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }

}

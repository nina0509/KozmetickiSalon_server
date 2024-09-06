/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.konfiguracija.Konfiguracija;

/**
 * Klasa koja upravlja konekcijama ka bazi podataka.
 *
 * Ova klasa koristi singleton obrazac kako bi obezbedila jedinstvenu instancu
 * konekcije sa bazom podataka tokom trajanja aplikacije. Takođe, sadrzi metode
 * za pristupanje i konfiguraciju konekcije.
 *
 *
 * @author Nikolina Baros
 */
public class DbConnectionFactory {

    /**
     * Jedinstvena instanca DbConnectionFactory koja se koristi za pristup bazi
     * podataka. Ova instanca se koristi za implementaciju singleton obrasca
     * kako bi se obezbedilo da postoji samo jedna instanca klase tokom rada
     * aplikacije.
     */
    private static DbConnectionFactory instance;

    /**
     * Konekcija ka bazi podataka koja se koristi za izvršavanje SQL upita. Ova
     * konekcija se uspostavlja prilikom kreiranja instance klase i koristi se
     * tokom rada aplikacije za interakciju sa bazom podataka.
     */
    private Connection connection;

    /**
     * Privatni konstruktor koji se koristi za uspostavljanje konekcije sa bazom
     * podataka na osnovu podataka iz konfiguracionog fajla. Konekcija se
     * uspostavlja samo ako prethodna konekcija ne postoji ili je zatvorena.
     */
    private DbConnectionFactory() {

        try {
            if (connection == null || connection.isClosed()) {
                String url = Konfiguracija.getInstance().getProperty("url");
                String username = Konfiguracija.getInstance().getProperty("username");
                String password = Konfiguracija.getInstance().getProperty("password");
                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Vraća jedinstvenu instancu DbConnectionFactory. Ako instanca ne postoji,
     * kreira se nova.
     *
     * @return Jedinstvena instanca DbConnectionFactory-a
     */
    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    /**
     * Vraća trenutnu konekciju ka bazi podataka.
     *
     * @return Konekcija ka bazi podataka.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Postavlja novu konekciju ka bazi podataka.
     *
     * @param connection Nova konekcija ka bazi podataka.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Ponovno uspostavlja konekciju sa bazom podataka. Kreira novu instancu
     * DbConnectionFactory.
     */
    public void povezi() {
        instance = new DbConnectionFactory();
    }

}

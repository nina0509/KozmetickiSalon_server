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
 *
 * @author ninic
 */
public class DbConnectionFactory {

    private static DbConnectionFactory instance;
    private Connection connection;

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

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void povezi() {
        instance = new DbConnectionFactory();
    }

}

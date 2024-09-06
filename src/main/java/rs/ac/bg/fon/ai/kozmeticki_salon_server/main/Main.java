/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.main;

import rs.ac.bg.fon.ai.kozmeticki_salon_server.forme.ServerskaForma;

/**
 * Glavna klasa aplikacije koja pokreće serverski program i sadrzi main metodu.
 *
 * @author Nikolina Baros
 */
public class Main {

    /**
     * Podrazumevani konstruktor za klasu Main. Inicijalizuje klasu bez dodatnih
     * parametara.
     */
    public Main() {

    }

    /**
     *
     * Metod koji se prvi izvršava pri pokretanju aplikacije. Poziva se
     * Singleton instanca kontrolera serverske aplikacije i otvara se serverska
     * forma.
     *
     * @param args Argumenti komandne linije (ne koriste se u ovoj aplikaciji).
     * @throws Exception Ako dodje do greske pri ucitavanju serverske forme.
     */
    public static void main(String[] args) throws Exception {
        ServerskaForma sf = new ServerskaForma();
        sf.setVisible(true);

    }
}

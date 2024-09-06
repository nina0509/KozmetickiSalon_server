/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum;


import java.util.List;

/**
 * Interfejs koji definiše osnovne operacije za rad sa podacima u repozitorijumu tj. skladistu podataka.
 * 
 * Ovaj interfejs koristi generički tip T kako bi omogućio rad sa različitim tipovima podataka.
 * 
 * @param <T> Tip podataka sa kojim repozitorijum radi.
 * 
 * @author ninic
 */

public interface Repozitorijum<T> {
    
    /**
     * Vraća sve podatke koji odgovaraju datom parametru i uslovu.
     * 
     * @param param Parametar koji se koristi za pretragu.
     * @param uslov Dodatni uslov za filtriranje rezultata.
     * @return Lista objekata tipa T koji odgovaraju pretrazi.
     * @throws Exception Ako dođe do greške tokom pretrage.
     */
    List<T> vratiSve(T param, String uslov) throws Exception;

     /**
     * Čuva dati objekat u repozitorijumu.
     * 
     * @param param Objekat koji treba da se sačuva.
     * @throws Exception Ako dođe do greške tokom čuvanja.
     */
    void sacuvaj(T param) throws Exception;
 /**
     * Čuva dati objekat u repozitorijumu i vraća njegov primarni ključ.
     * 
     * @param param Objekat koji treba da se sačuva.
     * @return Primarni ključ sačuvanog objekta.
     * @throws Exception Ako dođe do greške tokom čuvanja.
     */
    int sacuvajVratiPK(T param) throws Exception;

     /**
     * Menja podatke postojećeg objekta u repozitorijumu.
     * 
     * @param param Objekat sa izmenjenim podacima.
     * @throws Exception Ako dođe do greške tokom izmene.
     */
    void izmeni(T param) throws Exception;

    /**
     * Briše dati objekat iz repozitorijuma.
     * 
     * @param param Objekat koji treba da se obriše.
     * @throws Exception Ako dođe do greške tokom brisanja.
     */
    void izbrisi(T param) throws Exception;

}

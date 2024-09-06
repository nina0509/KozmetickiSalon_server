/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.modeli;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Menadzer;
/**
 * Klasa koja predstavlja model tabele za prikaz podataka o menadzerima sa kolonama:  Ime i Prezime .
 * Nasleđuje AbstractTableModel i koristi se za prikaz liste menadzera
 * u tabelarnom obliku.
 * 
 * @author Nikolina Baros
 */
public class ModelTabeleMenadzer extends AbstractTableModel{
    /**
     * Lista menadzera koja se prikazuje u tabeli.
     */
    List<Menadzer> lista;
    
     /**
     * Nazivi kolona u tabeli.
     */
    String[] kolone = {"Ime", "Prezime"};
 
 /**
     * Vraća broj redova u tabeli, tj. veličinu liste klijenata.
     * 
     * @return Broj redova u tabeli.
     */
    @Override
    public int getRowCount() {
        return lista.size();
    }
  /**
     * Vraća broj kolona u tabeli.
     * 
     * @return Broj kolona.
     */
    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    /**
     * Konstruktor klase ModelTabeleMenadzer koji postavlja listu menadzera za prikaz.
     * 
     * @param lista Lista menadzera koja će biti prikazana u tabeli.
     */
    public ModelTabeleMenadzer(List<Menadzer> lista) {

        this.lista = lista;
    }

     /**
     * Vraća vrednost iz tabele na osnovu indeksa reda i kolone.
     * 
     * @param rowIndex Indeks reda.
     * @param columnIndex Indeks kolone.
     * @return Vrednost u datom redu i koloni.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Menadzer m = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return m.getIme();
            case 1:
                return m.getPrezime();
            
            default:
                return null;

        }

    }
 /**
     * Vraća naziv kolone na osnovu njenog indeksa.
     * 
     * @param column Indeks kolone.
     * @return Naziv kolone.
     */
    @Override
    public String getColumnName(int column) {

        return kolone[column];
    }
 /**
     * Vraća trenutnu listu menadzera.
     * 
     * @return Lista menadzera.
     */
    public List<Menadzer> getLista() {
        return lista;
    }

     /**
     * Postavlja novu listu menadzera u model tabele.
     * 
     * @param lista Nova lista menadzera.
     */
    public void setLista(List<Menadzer> lista) {
        this.lista = lista;
    }

    



}

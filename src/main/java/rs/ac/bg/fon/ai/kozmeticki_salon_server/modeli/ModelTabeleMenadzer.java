/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.modeli;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Menadzer;

/**
 *
 * @author ninic
 */
public class ModelTabeleMenadzer extends AbstractTableModel{
    
    List<Menadzer> lista;
    String[] kolone = {"Ime", "Prezime"};
 

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    public ModelTabeleMenadzer(List<Menadzer> lista) {

        this.lista = lista;
    }

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

    @Override
    public String getColumnName(int column) {

        return kolone[column];
    }

    public List<Menadzer> getLista() {
        return lista;
    }

    public void setLista(List<Menadzer> lista) {
        this.lista = lista;
    }

    



}

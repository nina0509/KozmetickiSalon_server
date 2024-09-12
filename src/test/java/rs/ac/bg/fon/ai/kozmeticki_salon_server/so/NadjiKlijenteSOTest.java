/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije NadjiKlijente
 *
 * @author Nikolina Baros
 */
public class NadjiKlijenteSOTest{

    private NadjiKlijenteSO nadjiKlijenteSO;
    Klijent k;
    private Repozitorijum mockRepozitorijum;

   
    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        k = new Klijent();
        nadjiKlijenteSO = new NadjiKlijenteSO(mockRepozitorijum);

    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        k = null;
        nadjiKlijenteSO = null;
    }

    @Test
    public void testGetSetKlijenti() {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);

        nadjiKlijenteSO.setKlijenti(klijenti);

        assertEquals(klijenti, nadjiKlijenteSO.getKlijenti());
    }

    @Test
    public void testPredusloviNijeNullIDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            nadjiKlijenteSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da nadje klijente po zadatoj vrednosti", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacijaPrazanUslovZaPretragu() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);
        when(mockRepozitorijum.vratiSve(new Klijent(), null)).thenReturn(klijenti);
        nadjiKlijenteSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), null);
        assertEquals(k, nadjiKlijenteSO.getKlijenti().get(0));

    }

    @Test
    public void testUspesnaOperacijaPretragaSamoPoImenu() throws Exception {

        k.setKlijentId(1);
        k.setIme("nina");
 
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);
        String uslov = " WHERE klijent.ime LIKE '%" + k.getIme() + "%'";
        when(mockRepozitorijum.vratiSve(new Klijent(), uslov)).thenReturn(klijenti);
        nadjiKlijenteSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(k, nadjiKlijenteSO.getKlijenti().get(0));

    }

    @Test
    public void testUspesnaOperacijaPretragaSamoPoPrezimenu() throws Exception {

        k.setKlijentId(1);
        k.setPrezime("nina");
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);
        String uslov = " WHERE klijent.prezime LIKE '%" + k.getPrezime() + "%'";
        when(mockRepozitorijum.vratiSve(new Klijent(), uslov)).thenReturn(klijenti);
        nadjiKlijenteSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(k, nadjiKlijenteSO.getKlijenti().get(0));

    }

    @Test
    public void testUspesnaOperacijaPretragaIPoImenuIPrezimenu() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);
        String uslov = " WHERE klijent.ime LIKE '%" + k.getIme() + "%'";
        uslov += " AND klijent.prezime LIKE '%" + k.getPrezime() + "%'";
        when(mockRepozitorijum.vratiSve(new Klijent(), uslov)).thenReturn(klijenti);
        nadjiKlijenteSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(k, nadjiKlijenteSO.getKlijenti().get(0));

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        String uslov = " WHERE klijent.ime LIKE '%" + k.getIme() + "%'";
        uslov += " AND klijent.prezime LIKE '%" + k.getPrezime() + "%'";
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Klijent(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            nadjiKlijenteSO.izvrsi(k);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(null, nadjiKlijenteSO.getKlijenti());

    }

}

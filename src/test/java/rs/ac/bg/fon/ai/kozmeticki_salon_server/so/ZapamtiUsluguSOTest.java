/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;

/**
 * Klasa koja predstavlja testove sistemske operacije ZapamtiUsluguSO
 *
 * @author Nikolina Baros
 */
public class ZapamtiUsluguSOTest {

    private ZapamtiUsluguSO zapamtiUsluguSO;
    Usluga u;
    private Repozitorijum mockRepozitorijum;

    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        u = new Usluga();
        zapamtiUsluguSO = new ZapamtiUsluguSO(mockRepozitorijum);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        u = null;
        zapamtiUsluguSO = null;
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(new Klijent());
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void testNazivNull() {

        u.setCena(1200);
        u.setUslugaId(1);
        u.setTip(new TipUsluge(1, "manikir"));
        u.setTrajanje(120);
       
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

  


    @Test
    public void testPredugoTrajanje() {

        u = new Usluga(1, "manikir", 1202456, 1200, new TipUsluge(1, "manikir"));
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

   
    @Test
    public void testNullTip() {

        u.setCena(1200);
        u.setUslugaId(1);
        u.setNaziv("manikir");
        u.setTrajanje(120);
     
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void uspesnaOperacija() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge());

        zapamtiUsluguSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).sacuvaj(u);

    }

    @Test
    public void greskaUBazi() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge());

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).sacuvaj(u);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).sacuvaj(u);

    }

}

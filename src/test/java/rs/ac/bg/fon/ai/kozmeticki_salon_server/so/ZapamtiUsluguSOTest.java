/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;

/**
 * Klasa koja predstavlja testove sistemske operacije ZapamtiUsluguSO
 *
 * @author Nikolina Baros
 */
public class ZapamtiUsluguSOTest extends TestCase {

    private ZapamtiUsluguSO zapamtiUsluguSO;
    Usluga u;
    private Repozitorijum mockRepozitorijum;

    public ZapamtiUsluguSOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        u = new Usluga();
        zapamtiUsluguSO = new ZapamtiUsluguSO(mockRepozitorijum);
    }

    @Override
    protected void tearDown() throws Exception {
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

        u = new Usluga(1, null, 120, 1200, new TipUsluge(1, "manikir"));
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void testNazivPrazan() {

        u = new Usluga(1, "", 120, 1200, new TipUsluge(1, "manikir"));
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void testNegativnoTrajanje() {

        u = new Usluga(1, "manikir", -120, 1200, new TipUsluge(1, "manikir"));
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
    public void testNegativnaCena() {

        u = new Usluga(1, "manikir", 120, -1200, new TipUsluge(1, "manikir"));
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void testNullTip() {

        u = new Usluga(1, "manikir", 120, -1200, null);
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Sistem ne moze da zapamti uslugu", exception.getMessage());
    }

    @Test
    public void uspesnaOperacija() throws Exception {

        u = new Usluga(1, "manikir", 120, -1200, null);

        zapamtiUsluguSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).sacuvaj(u);

    }

    @Test
    public void greskaUBazi() throws Exception {

        u = new Usluga(1, "manikir", 120, -1200, null);

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).sacuvaj(u);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiUsluguSO.izvrsi(u);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).sacuvaj(u);

    }

}

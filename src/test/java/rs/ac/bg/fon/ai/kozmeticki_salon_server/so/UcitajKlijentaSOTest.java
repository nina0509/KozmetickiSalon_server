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
 * Klasa koja predstavlja testove sistemske operacije UcitajKlijenta
 *
 * @author Nikolina Baros
 */
public class UcitajKlijentaSOTest {

    private UcitajKlijentaSO ucitajKlijentaSO;
    Klijent k;
    private Repozitorijum mockRepozitorijum;

    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        k = new Klijent();
        ucitajKlijentaSO = new UcitajKlijentaSO(mockRepozitorijum);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        k = null;
        ucitajKlijentaSO = null;
    }

    @Test
    public void testGetSetKlijent() {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        ucitajKlijentaSO.setK(k);
        assertEquals(k, ucitajKlijentaSO.getK());
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajKlijentaSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da ucita klijenta", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajKlijentaSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da ucita klijenta", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        String uslov = " WHERE klijent.klijentId=" + k.getKlijentId();
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);
        when(mockRepozitorijum.vratiSve(new Klijent(), uslov)).thenReturn(klijenti);
        ucitajKlijentaSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(k, ucitajKlijentaSO.getK());

    }

    @Test
    public void testNijePronadjenKlijentUspesnaOperacija() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        String uslov = " WHERE klijent.klijentId=" + k.getKlijentId();
        when(mockRepozitorijum.vratiSve(new Klijent(), uslov)).thenReturn(new ArrayList());
        ucitajKlijentaSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(null, ucitajKlijentaSO.getK());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        String uslov = " WHERE klijent.klijentId=" + k.getKlijentId();
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Klijent(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajKlijentaSO.izvrsi(k);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), uslov);
        assertEquals(null, ucitajKlijentaSO.getK());

    }

}

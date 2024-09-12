/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.ArrayList;
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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije UcitajUslugu
 *
 * @author Nikolina Baros
 */
public class UcitajUsluguSOTest {

    private UcitajUsluguSO ucitajUsluguSO;
    Usluga u;
    private Repozitorijum mockRepozitorijum;


    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        u = new Usluga();
        ucitajUsluguSO = new UcitajUsluguSO(mockRepozitorijum);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        u = null;
        ucitajUsluguSO = null;
    }

    @Test
    public void testGetSetUsluga() {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        ucitajUsluguSO.setU(u);
        assertEquals(u, ucitajUsluguSO.getU());
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajUsluguSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da ucita uslugu", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajUsluguSO.izvrsi(new Klijent());
        });

        assertEquals("Sistem ne moze da ucita uslugu", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId WHERE usluga.uslugaId=" + u.getUslugaId();
        List<Usluga> usluge = new ArrayList<>();
        usluge.add(u);
        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(usluge);
        ucitajUsluguSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(u, ucitajUsluguSO.getU());

    }

    @Test
    public void testNijePronadjenKlijentUspesnaOperacija() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId WHERE usluga.uslugaId=" + u.getUslugaId();
        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(new ArrayList());
        ucitajUsluguSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(null, ucitajUsluguSO.getU());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId WHERE usluga.uslugaId=" + u.getUslugaId();
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Usluga(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajUsluguSO.izvrsi(u);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(null, ucitajUsluguSO.getU());

    }

}

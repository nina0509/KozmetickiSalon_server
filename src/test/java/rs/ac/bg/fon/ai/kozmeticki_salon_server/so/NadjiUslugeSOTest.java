/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije NadjiUsluge
 *
 * @author Nikolina Baros
 */
public class NadjiUslugeSOTest extends TestCase {

    private NadjiUslugeSO nadjiUslugeSO;
    Usluga u;
    private Repozitorijum mockRepozitorijum;

    public NadjiUslugeSOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        u = new Usluga();
        nadjiUslugeSO = new NadjiUslugeSO(mockRepozitorijum);
    }

    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        u = null;
        nadjiUslugeSO = null;
    }

    @Test
    public void testGetSetUsluge() {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        List<Usluga> lista = new ArrayList<>();
        lista.add(u);
        nadjiUslugeSO.setUsluge(lista);

        assertEquals(lista, nadjiUslugeSO.getUsluge());

    }

    @Test
    public void testPredusloviNijeNullIDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            nadjiUslugeSO.izvrsi(new Klijent());
        });

        assertEquals("Sistem ne moze da nadje usluge po zadatoj vrednosti", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacijaPrazanUslovZaPretragu() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        List<Usluga> lista = new ArrayList<>();
        lista.add(u);

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";

        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(lista);
        nadjiUslugeSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(lista, nadjiUslugeSO.getUsluge());

    }

    @Test
    public void testUspesnaOperacijaPretragaSamoPoNazivu() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, null);
        List<Usluga> lista = new ArrayList<>();
        lista.add(u);

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        uslov += " WHERE usluga.naziv LIKE '%" + u.getNaziv() + "%'";

        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(lista);
        nadjiUslugeSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(lista, nadjiUslugeSO.getUsluge());

    }

    @Test
    public void testUspesnaOperacijaPretragaSamoPoTipu() throws Exception {

        u = new Usluga(1, "", 120, 1200, new TipUsluge(1, "manikir"));
        List<Usluga> lista = new ArrayList<>();
        lista.add(u);

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        uslov += " WHERE usluga.tipId=" + u.getTip().getTipId();
        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(lista);
        nadjiUslugeSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(lista, nadjiUslugeSO.getUsluge());

    }

    @Test
    public void testUspesnaOperacijaPretragaPoTipuINazivu() throws Exception {

        u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        List<Usluga> lista = new ArrayList<>();
        lista.add(u);

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        uslov += " WHERE usluga.naziv LIKE '%" + u.getNaziv() + "%'";
        uslov += " AND usluga.tipId=" + u.getTip().getTipId();
        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(lista);
        nadjiUslugeSO.izvrsi(u);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(lista, nadjiUslugeSO.getUsluge());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Usluga(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            nadjiUslugeSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        assertEquals(null, nadjiUslugeSO.getUsluge());

    }

}

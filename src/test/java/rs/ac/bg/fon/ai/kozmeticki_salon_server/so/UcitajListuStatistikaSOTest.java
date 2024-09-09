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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Statistika;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaStatistike;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije UcitajListuStatistikaSO
 *
 * @author Nikolina Baros
 */
public class UcitajListuStatistikaSOTest extends TestCase {

    private UcitajListuStatistikaSO ucitajListuStatistikaSO;
    private Repozitorijum mockRepozitorijum;
    private List<Statistika> lista;

    public UcitajListuStatistikaSOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        lista = new ArrayList<>();
        ucitajListuStatistikaSO = new UcitajListuStatistikaSO(mockRepozitorijum);
    }

    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        lista = null;
        ucitajListuStatistikaSO = null;
    }

    @Test
    public void testGetSetStatistika() {

        Statistika s = new Statistika(2021, 2);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaStatistike ss = new StavkaStatistike(s, u, 2);
        List<StavkaStatistike> stavke = new ArrayList<>();
        stavke.add(ss);
        s.setStavke(stavke);

        ucitajListuStatistikaSO.setLista(lista);
        assertEquals(lista, ucitajListuStatistikaSO.getLista());

    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        Statistika s = new Statistika(2021, 2);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaStatistike ss = new StavkaStatistike(s, u, 2);
        List<StavkaStatistike> stavke = new ArrayList<>();
        stavke.add(ss);
        s.setStavke(stavke);
        lista.add(s);
        String uslov1 = " JOIN statistika ON statistika.godina=stavkastatistike.godina JOIN usluga ON stavkastatistike.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE statistika.godina=2021";

        when(mockRepozitorijum.vratiSve(new Statistika(), null)).thenReturn(lista);
        when(mockRepozitorijum.vratiSve(new StavkaStatistike(), uslov1)).thenReturn(stavke);

        ucitajListuStatistikaSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Statistika(), null);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaStatistike(), uslov1);

        assertEquals(lista, ucitajListuStatistikaSO.getLista());
        assertEquals(s, ucitajListuStatistikaSO.getLista().get(0));
        assertEquals(ss, ucitajListuStatistikaSO.getLista().get(0).getStavke().get(0));
    }

    @Test
    public void testUspesnaOperacijaPraznaLista() throws Exception {

        when(mockRepozitorijum.vratiSve(new Statistika(), null)).thenReturn(lista);
        ucitajListuStatistikaSO.izvrsi(null);
        verify(mockRepozitorijum, times(1)).vratiSve(new Statistika(), null);

        assertEquals(lista, ucitajListuStatistikaSO.getLista());

    }

    @Test
    public void testGreskaUBaziUcitavanjeStatistika() throws Exception {

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Statistika(), null);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuStatistikaSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new Statistika(), null);
        assertEquals(new ArrayList<>(), ucitajListuStatistikaSO.getLista());

    }

    @Test
    public void testGreskaUBaziUcitavanjeStavkiStatistika() throws Exception {

        Statistika s = new Statistika(2021, 2);
        lista.add(s);
        String uslov1 = " JOIN statistika ON statistika.godina=stavkastatistike.godina JOIN usluga ON stavkastatistike.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE statistika.godina=2021";

        when(mockRepozitorijum.vratiSve(new Statistika(), null)).thenReturn(lista);
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new StavkaStatistike(), uslov1);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuStatistikaSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new Statistika(), null);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaStatistike(), uslov1);

        assertEquals(lista, ucitajListuStatistikaSO.getLista());
        assertEquals(s, ucitajListuStatistikaSO.getLista().get(0));
        assertEquals(new ArrayList<>(), ucitajListuStatistikaSO.getLista().get(0).getStavke());

    }

}

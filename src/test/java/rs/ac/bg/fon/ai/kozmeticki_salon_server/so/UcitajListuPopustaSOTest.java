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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije UcitajListuPopustaSO
 *
 * @author Nikolina Baros
 */
public class UcitajListuPopustaSOTest {

    private UcitajListuPopustaSO ucitajListuPopustaSO;
    private Repozitorijum mockRepozitorijum;
    private List<Popust> lista;

    
    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        lista = new ArrayList<>();
        ucitajListuPopustaSO = new UcitajListuPopustaSO(mockRepozitorijum);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        lista = null;
        ucitajListuPopustaSO = null;
    }

    @Test
    public void testGetSetPopusti() {

        Klijent k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        Popust p = new Popust(k, u, 7, 5);
        lista.add(p);
        ucitajListuPopustaSO.setPopusti(lista);
        assertEquals(lista, ucitajListuPopustaSO.getPopusti());

    }

    @Test
    public void testPreduslovNullParametar() throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuPopustaSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da ucita listu popusta", exception.getMessage());
    }

    @Test
    public void testPreduslovDrugaKlasaParametar() throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuPopustaSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da ucita listu popusta", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        Popust p = new Popust(k, u, 7, 5);
        lista.add(p);

        String uslov = " JOIN klijent ON klijent.klijentId=popust.klijentId JOIN usluga ON usluga.uslugaId=popust.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + k.getKlijentId();

        when(mockRepozitorijum.vratiSve(new Popust(), uslov)).thenReturn(lista);
        ucitajListuPopustaSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Popust(), uslov);

        assertEquals(lista, ucitajListuPopustaSO.getPopusti());
        assertEquals(p, ucitajListuPopustaSO.getPopusti().get(0));
    }

    @Test
    public void testUspesnaOperacijaPraznaLista() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612077777", new Date());

        String uslov = " JOIN klijent ON klijent.klijentId=popust.klijentId JOIN usluga ON usluga.uslugaId=popust.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + k.getKlijentId();

        when(mockRepozitorijum.vratiSve(new Popust(), uslov)).thenReturn(lista);
        ucitajListuPopustaSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Popust(), uslov);

        assertEquals(lista, ucitajListuPopustaSO.getPopusti());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        String uslov = " JOIN klijent ON klijent.klijentId=popust.klijentId JOIN usluga ON usluga.uslugaId=popust.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId WHERE klijent.klijentId=" + k.getKlijentId();

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Popust(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuPopustaSO.izvrsi(k);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Popust(), uslov);
        assertEquals(null, ucitajListuPopustaSO.getPopusti());

    }

}

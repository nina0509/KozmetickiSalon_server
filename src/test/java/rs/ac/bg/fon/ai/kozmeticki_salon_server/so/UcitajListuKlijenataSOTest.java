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

/**
 * Klasa koja predstavlja testove sistemske operacije UcitajListuKlijenataSO
 *
 * @author Nikolina Baros
 */
public class UcitajListuKlijenataSOTest extends TestCase {

    private UcitajListuKlijenataSO ucitajListuKlijenataSO;
    private Repozitorijum mockRepozitorijum;
    private List<Klijent> lista;

    public UcitajListuKlijenataSOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        lista = new ArrayList<>();
        ucitajListuKlijenataSO = new UcitajListuKlijenataSO(mockRepozitorijum);
    }

    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        lista = null;
        ucitajListuKlijenataSO = null;
    }

    @Test
    public void testGetSetKlijenti() {

        Klijent k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        lista.add(k);
        ucitajListuKlijenataSO.setKlijenti(lista);
        assertEquals(lista, ucitajListuKlijenataSO.getKlijenti());
    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        lista.add(k);

        when(mockRepozitorijum.vratiSve(new Klijent(), null)).thenReturn(lista);
        ucitajListuKlijenataSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), null);

        assertEquals(lista, ucitajListuKlijenataSO.getKlijenti());
        assertEquals(k, ucitajListuKlijenataSO.getKlijenti().get(0));
    }

    @Test
    public void testUspesnaOperacijaPraznaLista() throws Exception {

        when(mockRepozitorijum.vratiSve(new Klijent(), null)).thenReturn(lista);
        ucitajListuKlijenataSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), null);

        assertEquals(lista, ucitajListuKlijenataSO.getKlijenti());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Klijent(), null);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuKlijenataSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), null);
        assertEquals(null, ucitajListuKlijenataSO.getKlijenti());

    }
    
    
    

}

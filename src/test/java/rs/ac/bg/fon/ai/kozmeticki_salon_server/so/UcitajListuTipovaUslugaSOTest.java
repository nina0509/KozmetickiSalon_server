/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.ArrayList;
import java.util.List;
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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;

/**
 * Klasa koja predstavlja testove sistemske operacije UcitajListuTipovaUslugaSO
 *
 * @author Nikolina Baros
 */
public class UcitajListuTipovaUslugaSOTest extends TestCase {

    private UcitajListuTipovaUslugaSO ucitajListuTipovaUslugaSO;
    private Repozitorijum mockRepozitorijum;
    private List<TipUsluge> lista;

    public UcitajListuTipovaUslugaSOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        lista = new ArrayList<>();
        ucitajListuTipovaUslugaSO = new UcitajListuTipovaUslugaSO(mockRepozitorijum);
    }

    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        lista = null;
        ucitajListuTipovaUslugaSO = null;
    }

    @Test
    public void testGetSetTipUsluge() {

        TipUsluge tu1 = new TipUsluge(1, "manikir");
        TipUsluge tu2 = new TipUsluge(2, "pedikir");

        lista.add(tu1);
        lista.add(tu2);

        ucitajListuTipovaUslugaSO.setTipoviUsluge(lista);
        assertEquals(lista, ucitajListuTipovaUslugaSO.getTipoviUsluge());

    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        TipUsluge tu1 = new TipUsluge(1, "manikir");
        TipUsluge tu2 = new TipUsluge(2, "pedikir");

        lista.add(tu1);
        lista.add(tu2);

        when(mockRepozitorijum.vratiSve(new TipUsluge(), null)).thenReturn(lista);

        ucitajListuTipovaUslugaSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new TipUsluge(), null);

        assertEquals(lista, ucitajListuTipovaUslugaSO.getTipoviUsluge());
        assertEquals(2, ucitajListuTipovaUslugaSO.getTipoviUsluge().size());
        assertEquals(tu1, ucitajListuTipovaUslugaSO.getTipoviUsluge().get(0));
        assertEquals(tu2, ucitajListuTipovaUslugaSO.getTipoviUsluge().get(1));
    }
    
     @Test
    public void testUspesnaOperacijaPraznaLista() throws Exception {

        when(mockRepozitorijum.vratiSve(new TipUsluge(), null)).thenReturn(lista);

        ucitajListuTipovaUslugaSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new TipUsluge(), null);

        assertEquals(lista, ucitajListuTipovaUslugaSO.getTipoviUsluge());
        assertEquals(0, ucitajListuTipovaUslugaSO.getTipoviUsluge().size());
      
    }
    
      @Test
    public void testGreskaUBazi() throws Exception {

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new TipUsluge(), null);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuTipovaUslugaSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new TipUsluge(), null);
        
         assertEquals(null, ucitajListuTipovaUslugaSO.getTipoviUsluge());
       

    }

}

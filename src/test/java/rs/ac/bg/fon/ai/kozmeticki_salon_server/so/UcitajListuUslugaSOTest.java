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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.TipUsluge;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;
/**
 * Klasa koja predstavlja testove sistemske operacije UcitajListuUslugaSO
 *
 * @author Nikolina Baros
 */
public class UcitajListuUslugaSOTest{
     private UcitajListuUslugaSO ucitajListuUslugaSO;
    private Repozitorijum mockRepozitorijum;
    private List<Usluga> lista;
    
    
    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        lista = new ArrayList<>();
        ucitajListuUslugaSO = new UcitajListuUslugaSO(mockRepozitorijum);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum=null;
        lista=null;
        ucitajListuUslugaSO=null;
    }

    @Test
    public void testGetSetUsluga() {

        TipUsluge tu1 = new TipUsluge(1, "manikir");
        Usluga u=new Usluga(1, "manikir", 120, 1200, tu1);
        lista.add(u);
       
        ucitajListuUslugaSO.setUsluge(lista);
        assertEquals(lista, ucitajListuUslugaSO.getUsluge());

    }
    
    
     @Test
    public void testUspesnaOperacija() throws Exception {

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        TipUsluge tu1 = new TipUsluge(1, "manikir");
        Usluga u=new Usluga(1, "manikir", 120, 1200, tu1);
        lista.add(u);

        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(lista);

        ucitajListuUslugaSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);

        assertEquals(lista, ucitajListuUslugaSO.getUsluge());
        assertEquals(1, ucitajListuUslugaSO.getUsluge().size());
        assertEquals(u, ucitajListuUslugaSO.getUsluge().get(0));
        
    }
    
    
      @Test
    public void testUspesnaOperacijaPraznaLista() throws Exception {

         String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        when(mockRepozitorijum.vratiSve(new Usluga(), uslov)).thenReturn(lista);

        ucitajListuUslugaSO.izvrsi(null);
        
        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);

        assertEquals(lista, ucitajListuUslugaSO.getUsluge());
        assertEquals(0, ucitajListuUslugaSO.getUsluge().size());
      
    }
    
   @Test
    public void testGreskaUBazi() throws Exception {

         String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Usluga(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajListuUslugaSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new Usluga(), uslov);
        
         assertEquals(null, ucitajListuUslugaSO.getUsluge());
       

    }
    
    
    
   
}

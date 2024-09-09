/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.Date;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 *
 * @author ninic
 */
public class IzbrisiUsluguSOTest extends TestCase {
    
    private IzbrisiUsluguSO izbrisiUsluguSO;
     Usluga u;
    private Repozitorijum mockRepozitorijum; 
    
    public IzbrisiUsluguSOTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
          mockRepozitorijum=mock(DbRepozitorijumGenericki.class);
          izbrisiUsluguSO = new IzbrisiUsluguSO(mockRepozitorijum);
          u=new Usluga();
    }
    
    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum=null;
        izbrisiUsluguSO=null;
        u=null;
    }

    @Test
    public void testPredusloviNullParam() {
       
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiUsluguSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da izbrise uslugu", exception.getMessage());
    }
    
     @Test
    public void testPredusloviDrugaKlasa() {
       
       Exception exception = assertThrows(Exception.class, () -> {
            izbrisiUsluguSO.izvrsi(new Klijent());
        });

        assertEquals("Sistem ne moze da izbrise uslugu", exception.getMessage());
    }
    
    @Test
    public void testUspesnaOperacija() throws Exception {
        
        u.setUslugaId(1);
        doNothing().when(mockRepozitorijum).izbrisi((Usluga)u);  
        izbrisiUsluguSO.izvrsi((Usluga)u);
        
        verify(mockRepozitorijum, times(1)).izbrisi((Usluga)u);
      
         
    }
    
    
     @Test
    public void testGreskaUBaziPrilikomBrisanjaUsluge() throws Exception{
     
        u.setUslugaId(1);

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).izbrisi((Usluga)u);

        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiUsluguSO.izvrsi((Usluga)u);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).izbrisi((Usluga)u);
    }
    
    
   
}

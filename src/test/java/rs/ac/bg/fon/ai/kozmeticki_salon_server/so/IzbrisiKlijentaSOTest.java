/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.Date;
import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;

/**
 * Klasa koja predstavlja testove sistemske operacije IzbrisiKlijenta
 * 
 * @author Nikolina Baros
 */
public class IzbrisiKlijentaSOTest extends TestCase {

     private IzbrisiKlijentaSO izbrisiKlijentaSO;
     Klijent k;
    private Repozitorijum mockRepozitorijum; 

    public IzbrisiKlijentaSOTest(String testName) {
        super(testName);
    }

    
    @Override
    protected void setUp() throws Exception {
        
        mockRepozitorijum=mock(DbRepozitorijumGenericki.class);
        izbrisiKlijentaSO = new IzbrisiKlijentaSO(mockRepozitorijum);
         
        k=new Klijent();
        
    }

    @Override
    protected void tearDown() throws Exception {
        izbrisiKlijentaSO = null;
        k = null;
        mockRepozitorijum=null;
    }

    @Test
    public void testPredusloviNullParam() {
       
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiKlijentaSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da izbrise klijenta", exception.getMessage());
    }
    
    @Test
    public void testPredusloviDrugaKlasa() {
       
       Exception exception = assertThrows(Exception.class, () -> {
            izbrisiKlijentaSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da izbrise klijenta", exception.getMessage());
    }
    
     @Test
    public void testUspesnaOperacija() throws Exception {
         Klijent klijent = new Klijent();
        klijent.setKlijentId(1); 
        
        doNothing().when(mockRepozitorijum).izbrisi((Klijent)klijent);
        
        izbrisiKlijentaSO.izvrsi(klijent);

        // Proveri da li je pozvana metoda izbrisi u repozitorijumu
        verify(mockRepozitorijum, times(1)).izbrisi(klijent);
      
         
    }
    
    
    @Test
    public void testGreskaUBaziPrilikomBrisanjaKlijenta() throws Exception{
     
        Klijent klijent = new Klijent();
        klijent.setKlijentId(1); 

        // Simuliranje greške prilikom brisanja klijenta 
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).izbrisi((Klijent)klijent);

        //hvatamo izuzetak
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiKlijentaSO.izvrsi(klijent);
        });

        // da li je izuzetak ocekivana poruka
        assertEquals("Greška u bazi", exception.getMessage());

        // da li je pozvana metoda izbrisi u repozitorijumu, ali da je bačen izuzetak
        verify(mockRepozitorijum, times(1)).izbrisi(klijent);
    }
}
        
    
    
    
    
    



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije IzbrisiRezervaciju
 * 
 * @author Nikolina Baros
 */
public class IzbrisiRezervacijuSOTest extends TestCase {
    
     private IzbrisiRezervacijuSO izbrisiRezervacijuSO;
     Rezervacija r;
    private Repozitorijum mockRepozitorijum; 
    public IzbrisiRezervacijuSOTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum=mock(DbRepozitorijumGenericki.class);
        izbrisiRezervacijuSO = new IzbrisiRezervacijuSO(mockRepozitorijum);
        r=new Rezervacija();
    }
    
    @Override
    protected void tearDown() throws Exception {
      izbrisiRezervacijuSO = null;
        r = null;
        mockRepozitorijum=null;
    }
    
     @Test
    public void testPredusloviNullParam() {
       
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiRezervacijuSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da izbrise rezervaciju", exception.getMessage());
    }
    
     @Test
    public void testPredusloviDrugaKlasa() {
       
       Exception exception = assertThrows(Exception.class, () -> {
            izbrisiRezervacijuSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da izbrise rezervaciju", exception.getMessage());
    }
    
    
     @Test
    public void testUspesnaOperacija() throws Exception {
        
         r=new Rezervacija(1, new Date(), 120, true, new Klijent()); 
        doNothing().when(mockRepozitorijum).izbrisi((Rezervacija)r);  
        izbrisiRezervacijuSO.izvrsi(r);
        // Proveri da li je pozvana metoda izbrisi u repozitorijumu
        verify(mockRepozitorijum, times(1)).izbrisi((Rezervacija)r);
      
         
    }
    
    @Test
    public void testGreskaUBaziPrilikomBrisanjaRezervacije() throws Exception{
     
          r=new Rezervacija(1, new Date(), 120, true, new Klijent()); 

        // Simuliranje greške prilikom brisanja 
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).izbrisi((Rezervacija)r);

        //hvatamo izuzetak
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiRezervacijuSO.izvrsi((Rezervacija)r);
        });

        // da li je izuzetak ocekivana poruka
        assertEquals("Greška u bazi", exception.getMessage());

        // da li je pozvana metoda izbrisi u repozitorijumu, ali da je bačen izuzetak
        verify(mockRepozitorijum, times(1)).izbrisi((Rezervacija)r);
    }
    
    
     @Test
    public void testGreskaUBaziPrilikomBrisanjaStavki() throws Exception{
     
          r=new Rezervacija(1, new Date(), 120, true, new Klijent()); 
          StavkaRezervacije sr=new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MAX, 120, null);
         List<StavkaRezervacije> stavke=new ArrayList<>();
         stavke.add(sr);
          r.setStavke(stavke);
         // Simuliranje greške prilikom brisanja  neke od stavki rezervacija
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).izbrisi(any(StavkaRezervacije.class));

        //hvatamo izuzetak
       Exception exception = assertThrows(Exception.class, () -> {
            izbrisiRezervacijuSO.izvrsi((Rezervacija)r);
        });

        // da li je izuzetak ocekivana poruka
        assertEquals("Greška u bazi", exception.getMessage());

        // da li je pozvana metoda izbrisi u repozitorijumu, ali da je bačen izuzetak
         verify(mockRepozitorijum, times(1)).izbrisi(any(StavkaRezervacije.class));
    }
    
    
    
    

}

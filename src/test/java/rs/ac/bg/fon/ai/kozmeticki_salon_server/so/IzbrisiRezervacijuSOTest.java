/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Popust;
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
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        izbrisiRezervacijuSO = new IzbrisiRezervacijuSO(mockRepozitorijum);
        r = new Rezervacija();
    }

    @Override
    protected void tearDown() throws Exception {
        izbrisiRezervacijuSO = null;
        r = null;
        mockRepozitorijum = null;
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
    public void testUspesnaOperacijaIzmenaPopusta() throws Exception {

      Klijent klijent = new Klijent();
        klijent.setKlijentId(1);

        Usluga usluga = new Usluga();
        usluga.setUslugaId(1);

        StavkaRezervacije stavka1 = new StavkaRezervacije();
        stavka1.setRBStavke(1);
        stavka1.setUsluga(usluga);

        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(stavka1);

        r = new Rezervacija();
        r.setRezervacijaId(1);
        r.setKlijent(klijent);
        r.setStavke(stavke);
        
        Popust popust = new Popust();
        popust.setBrojRezUsluge(6);
        popust.setPopust(5);
        
        List<Popust> popusti=new ArrayList<>();
        popusti.add(popust);
        
        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);

        doNothing().when(mockRepozitorijum).izbrisi((StavkaRezervacije) stavka1);
        doNothing().when(mockRepozitorijum).izbrisi((Rezervacija) r);
        popust.setBrojRezUsluge(6);
        doNothing().when(mockRepozitorijum).izmeni((Popust) popust);
        doNothing().when(mockRepozitorijum).izbrisi((Rezervacija) r);
       
        izbrisiRezervacijuSO.izvrsi(r);
        verify(mockRepozitorijum, times(1)).izbrisi((Rezervacija) r);
        verify(mockRepozitorijum, times(1)).izbrisi((StavkaRezervacije) stavka1);
        verify(mockRepozitorijum, times(1)).izmeni((Popust) popust);
        
        
    }
    
     @Test
    public void testUspesnaOperacijaBrisanjePopusta() throws Exception {
 
        Klijent klijent = new Klijent();
        klijent.setKlijentId(1);

        Usluga usluga = new Usluga();
        usluga.setUslugaId(1);

        StavkaRezervacije stavka1 = new StavkaRezervacije();
        stavka1.setRBStavke(1);
        stavka1.setUsluga(usluga);

        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(stavka1);

        r.setRezervacijaId(1);
        r.setKlijent(klijent);
        r.setStavke(stavke);

        Popust popust = new Popust();
        popust.setBrojRezUsluge(1);  
        List<Popust> popusti=new ArrayList<>();
        popusti.add(popust);
       
        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);

        doNothing().when(mockRepozitorijum).izbrisi((StavkaRezervacije) stavka1);
        doNothing().when(mockRepozitorijum).izbrisi((Rezervacija) r);
        doNothing().when(mockRepozitorijum).izbrisi((Popust) popust);
        doNothing().when(mockRepozitorijum).izbrisi((Rezervacija) r);
       
        izbrisiRezervacijuSO.izvrsi(r);
        verify(mockRepozitorijum, times(1)).izbrisi((Rezervacija) r);
        verify(mockRepozitorijum, times(1)).izbrisi((StavkaRezervacije) stavka1);
        popust.setBrojRezUsluge(0);
        verify(mockRepozitorijum, times(1)).izbrisi((Popust) popust);
    
}
   

    @Test
    public void testGreskaUBaziPrilikomBrisanjaRezervacije() throws Exception {

        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        r = new Rezervacija(1, datum, 120, true, new Klijent());

        // Simuliranje greške prilikom brisanja 
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).izbrisi((Rezervacija) r);

        //hvatamo izuzetak
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiRezervacijuSO.izvrsi((Rezervacija) r);
        });

        // da li je izuzetak ocekivana poruka
        assertEquals("Greška u bazi", exception.getMessage());

        // da li je pozvana metoda izbrisi u repozitorijumu, ali da je bačen izuzetak
        verify(mockRepozitorijum, times(1)).izbrisi((Rezervacija) r);
    }

    @Test
    public void testGreskaUBaziPrilikomBrisanjaStavki() throws Exception {

        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        r = new Rezervacija(1, datum, 120, true, new Klijent());
        StavkaRezervacije sr = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MAX, 120, new Usluga());
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(sr);
        r.setStavke(stavke);
        // Simuliranje greške prilikom brisanja  neke od stavki rezervacija
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).izbrisi(any(StavkaRezervacije.class));

        //hvatamo izuzetak
        Exception exception = assertThrows(Exception.class, () -> {
            izbrisiRezervacijuSO.izvrsi((Rezervacija) r);
        });

        // da li je izuzetak ocekivana poruka
        assertEquals("Greška u bazi", exception.getMessage());

        // da li je pozvana metoda izbrisi u repozitorijumu, ali da je bačen izuzetak
        verify(mockRepozitorijum, times(1)).izbrisi(any(StavkaRezervacije.class));
    }

}

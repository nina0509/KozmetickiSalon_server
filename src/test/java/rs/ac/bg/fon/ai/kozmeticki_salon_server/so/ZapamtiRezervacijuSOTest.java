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
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;

/**
 * Klasa koja predstavlja testove sistemske operacije ZapamtiRezervacijuSO
 *
 * @author Nikolina Baros
 */
public class ZapamtiRezervacijuSOTest{

    private ZapamtiRezervacijuSO zapamtiRezervacijuSO;
    Rezervacija r;
    private Repozitorijum mockRepozitorijum;


    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        r = new Rezervacija();
        zapamtiRezervacijuSO = new ZapamtiRezervacijuSO(mockRepozitorijum);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        r = null;
        zapamtiRezervacijuSO = null;
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(new Klijent());
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }

    @Test
    public void testPredusloviKlijentNull() {

        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        r.setRezervacijaId(1);
        r.setDatum(datum);
        r.setUkupnaCena(1200);
        r.setPojavljivanje(true);
        r.setKlijent(new Klijent());
       
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);

        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);

        r.setStavke(stavke);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(new Rezervacija());
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }

  

    @Test
    public void testPredusloviRezervacijaBezStavki() {

        
        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        r = new Rezervacija(1, datum, 1200, true, k);

        List<StavkaRezervacije> stavke = new ArrayList<>();
        r.setStavke(stavke);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(new Rezervacija());
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }

    @Test
    public void testPredusloviStavkeNull() {

        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        r.setRezervacijaId(1);
        r.setDatum(datum);
        r.setUkupnaCena(1200);
        r.setPojavljivanje(true);
        r.setKlijent(k);
        

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(new Rezervacija());
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }

    @Test
    public void testPredusloviDatumNull() {

        
        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        r.setRezervacijaId(1);
        r.setUkupnaCena(1200);
        r.setPojavljivanje(true);
        r.setKlijent(k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);

        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);

        r.setStavke(stavke);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(new Rezervacija());
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }
    
     @Test
    public void testPredusloviDatumUProslosti() {

        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        r.setRezervacijaId(1);
        r.setUkupnaCena(1200);
        r.setPojavljivanje(true);
        r.setKlijent(k);
        r.setDatum(datum);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);

        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);

        r.setStavke(stavke);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(new Rezervacija());
        });

        assertEquals("Sistem ne moze da zapamti rezervaciju", exception.getMessage());
    }


    @Test
    public void testUspehDodavanjeRezervacijeKreiranjePopusta() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        //rez 0 kad dodajemo
        r = new Rezervacija(0, new Date(), 1200, true, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavke);
        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(new ArrayList<>());
        when(mockRepozitorijum.sacuvajVratiPK(any(Rezervacija.class))).thenReturn(1);

        zapamtiRezervacijuSO.izvrsiOperaciju(r);

        verify(mockRepozitorijum, times(1)).sacuvajVratiPK(r);
        verify(mockRepozitorijum, times(1)).sacuvaj(s);
        verify(mockRepozitorijum, times(1)).sacuvaj(any(Popust.class));
    }

    @Test
    public void testUspehDodavanjeRezervacijeIzmenaPopusta() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        //rez id 0 kad dodajemo
        r = new Rezervacija(0, new Date(), 1200, true, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        Popust p = new Popust(k, u, 9, 5);
        List<Popust> popusti = new ArrayList<>();
        popusti.add(p);

        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavke);
        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);
        when(mockRepozitorijum.sacuvajVratiPK(any(Rezervacija.class))).thenReturn(1);

        zapamtiRezervacijuSO.izvrsiOperaciju(r);

        verify(mockRepozitorijum, times(1)).sacuvajVratiPK(r);
        verify(mockRepozitorijum, times(1)).sacuvaj(s);
        p.setBrojRezUsluge(p.getBrojRezUsluge() + 1);
        p.setPopust(10);
        verify(mockRepozitorijum, times(1)).izmeni(p);
    }

    @Test
    public void testUspehIzmenaRezervacijePromenaStavki() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        //rez -1 kad dodajemo
        r = new Rezervacija(1, new Date(), 1200, true, k);
        Rezervacija r1 = new Rezervacija(1, new Date(), 1200, true, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        Popust p = new Popust(k, u, 9, 5);
        List<Popust> popusti = new ArrayList<>();
        popusti.add(p);

        StavkaRezervacije s1 = new StavkaRezervacije(2, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        StavkaRezervacije s2 = new StavkaRezervacije(3, r, LocalTime.NOON, LocalTime.NOON, 1200, u);

        List<StavkaRezervacije> stavkeNove = new ArrayList<>();
        stavkeNove.add(s1);
        stavkeNove.add(s2);

        r1.setStavke(stavkeNove);

        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        when(mockRepozitorijum.vratiSve(any(Rezervacija.class), anyString())).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavke);

        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);

        zapamtiRezervacijuSO.izvrsiOperaciju(r1);
        //s se brise
        verify(mockRepozitorijum, times(1)).izbrisi(s);

        //s1 i s2 dodajemo
        verify(mockRepozitorijum, times(1)).sacuvaj(s1);
        verify(mockRepozitorijum, times(1)).sacuvaj(s2);
        p.setBrojRezUsluge(p.getBrojRezUsluge() + 1);
        p.setPopust(10);

        verify(mockRepozitorijum, times(1)).izmeni(r);

        InOrder inOrder = inOrder(mockRepozitorijum);
        p.setBrojRezUsluge(8);
        inOrder.verify(mockRepozitorijum).izmeni(p);
        p.setBrojRezUsluge(9);
        inOrder.verify(mockRepozitorijum).izmeni(p);
        p.setBrojRezUsluge(10);
        p.setPopust(10);
        inOrder.verify(mockRepozitorijum).izmeni(p);
    }

    @Test
    public void testUspehIzmenaRezervacijeUnosNepojavljivanja() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        r = new Rezervacija(1, new Date(), 1200, true, k);
        Rezervacija r1 = new Rezervacija(1, new Date(), 1200, false, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        Popust p = new Popust(k, u, 9, 5);
        List<Popust> popusti = new ArrayList<>();
        popusti.add(p);

        r1.setStavke(stavke);

        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        when(mockRepozitorijum.vratiSve(any(Rezervacija.class), anyString())).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavke);

        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);

        zapamtiRezervacijuSO.izvrsiOperaciju(r1);

        verify(mockRepozitorijum, times(1)).izmeni(r);

        p.setBrojRezUsluge(8);
        verify(mockRepozitorijum, times(1)).izmeni(p);

    }

    @Test
    public void testUspehIzmenaRezervacijeUnosDolaska() throws Exception {

        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        r = new Rezervacija(1, new Date(), 1200, false, k);
        Rezervacija r1 = new Rezervacija(1, new Date(), 1200, true, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        Popust p = new Popust(k, u, 9, 5);
        List<Popust> popusti = new ArrayList<>();
        popusti.add(p);

        r1.setStavke(stavke);

        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        when(mockRepozitorijum.vratiSve(any(Rezervacija.class), anyString())).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavke);

        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);

        zapamtiRezervacijuSO.izvrsiOperaciju(r1);

        verify(mockRepozitorijum, times(1)).izmeni(r);

        p.setBrojRezUsluge(10);
        verify(mockRepozitorijum, times(1)).izmeni(p);

    }

    @Test
    public void testPreklapanjeTermina() throws Exception {

        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        //rez id 0 kad dodajemo
        r = new Rezervacija(0, datum, 1200, true, k);
        Rezervacija r1 = new Rezervacija(1, new Date(), 1200, true, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MAX, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        Popust p = new Popust(k, u, 9, 5);
        List<Popust> popusti = new ArrayList<>();
        popusti.add(p);

        StavkaRezervacije s1 = new StavkaRezervacije(2, r, LocalTime.MIN, LocalTime.MAX, 1200, u);
        StavkaRezervacije s2 = new StavkaRezervacije(3, r, LocalTime.MIN, LocalTime.MAX, 1200, u);

        List<StavkaRezervacije> stavkeNove = new ArrayList<>();
        stavkeNove.add(s1);
        stavkeNove.add(s2);

        r1.setStavke(stavkeNove);

        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        when(mockRepozitorijum.vratiSve(any(Rezervacija.class), anyString())).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavkeNove);

        when(mockRepozitorijum.vratiSve(any(Popust.class), anyString())).thenReturn(popusti);

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(r);
        });

        assertEquals("Sistem ne moze da doda stavku rezervacije. Termini usluga se preklapaju!", exception.getMessage());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        Klijent k = new Klijent(1, "nina", "nina", "0612020222", new Date());
        r = new Rezervacija(1, datum, 1200, false, k);
        Usluga u = new Usluga(1, "manikir", 120, 1200, new TipUsluge(1, "manikir"));
        StavkaRezervacije s = new StavkaRezervacije(1, r, LocalTime.MIN, LocalTime.MIN, 1200, u);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        stavke.add(s);
        r.setStavke(stavke);

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(any(Rezervacija.class), anyString());

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiRezervacijuSO.izvrsi(r);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(any(Rezervacija.class), anyString());

    }

}

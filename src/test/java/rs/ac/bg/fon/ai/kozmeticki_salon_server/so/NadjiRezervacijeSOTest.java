/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Klijent;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije NadjiRezervacije
 *
 * @author Nikolina Baros
 */
public class NadjiRezervacijeSOTest {

    private NadjiRezervacijeSO nadjiRezervacijeSO;
    Rezervacija r;
    private Repozitorijum mockRepozitorijum;
    Klijent k;

    

    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        r = new Rezervacija();
        k = new Klijent();
        nadjiRezervacijeSO = new NadjiRezervacijeSO(mockRepozitorijum);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockRepozitorijum = null;
        r = null;
        nadjiRezervacijeSO = null;
        k = null;
    }

    @Test
    public void testGetSetRezervacije() {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        r = new Rezervacija(1, datum, 120, true, k);

        List<Rezervacija> lista = new ArrayList<>();
        lista.add(r);
        nadjiRezervacijeSO.setRezervacije(lista);

        assertEquals(lista, nadjiRezervacijeSO.getRezervacije());

    }

    @Test
    public void testPredusloviNijeNullIDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            nadjiRezervacijeSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da nadje rezervacije po zadatoj vrednosti", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacijaPrazanUslovZaPretragu() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Date datum = new Date();
        r = new Rezervacija(1, datum, 120, true, k);

        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId";
        uslov += " ORDER BY rezervacija.datum DESC";
        String uslov1 = " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(rez);
        nadjiRezervacijeSO.izvrsi(null);

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaRezervacije(), uslov1);
        assertEquals(r, nadjiRezervacijeSO.getRezervacije().get(0));

    }

    @Test
    public void testUspesnaOperacijaPretragaSamoPoImenuKlijenta() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        k.setIme("nina");
        r.setRezervacijaId(1);
        r.setKlijent(k);
        r.setPojavljivanje(true);
        r.setUkupnaCena(1000);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        r.setStavke(stavke);
        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE klijent.ime LIKE '%nina%' ORDER BY rezervacija.datum DESC";
        String uslov1 = " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(new StavkaRezervacije(), uslov1)).thenReturn(stavke);
        nadjiRezervacijeSO.izvrsi((Rezervacija) r);

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaRezervacije(), uslov1);
        assertEquals(r, nadjiRezervacijeSO.getRezervacije().get(0));

    }

    @Test
    public void testUspesnaOperacijaPretragaSamoPoDatumu() throws Exception {
        Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        
        r = new Rezervacija(1, datum, 120, true, new Klijent());
        List<StavkaRezervacije> stavke = new ArrayList<>();
        r.setStavke(stavke);
        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        java.sql.Date datum1 = new java.sql.Date(r.getDatum().getTime());
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId";
        uslov += " WHERE rezervacija.datum='" + datum1 + "'";
        uslov += " ORDER BY rezervacija.datum DESC";
        String uslov1 = " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(new StavkaRezervacije(), uslov1)).thenReturn(stavke);
        nadjiRezervacijeSO.izvrsi((Rezervacija) r);

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaRezervacije(), uslov1);
        assertEquals(r, nadjiRezervacijeSO.getRezervacije().get(0));

    }

    @Test
    public void testUspesnaOperacijaPretragaPoImenuIPoDatumu() throws Exception {
         Calendar myCalendar = new GregorianCalendar(2025, 2, 11);
        Date datum = myCalendar.getTime();
        r = new Rezervacija(1, datum, 120, true, new Klijent(1, "nina", "nina", "0616277777", new Date()));
        List<StavkaRezervacije> stavke = new ArrayList<>();
        r.setStavke(stavke);
        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);

        java.sql.Date datum1 = new java.sql.Date(r.getDatum().getTime());
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId";
        uslov += " WHERE klijent.ime LIKE '%" + r.getKlijent().getIme() + "%'";
        uslov += " AND rezervacija.datum='" + datum1 + "'";
        uslov += " ORDER BY rezervacija.datum DESC";
        String uslov1 = " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(new StavkaRezervacije(), uslov1)).thenReturn(stavke);
        nadjiRezervacijeSO.izvrsi((Rezervacija) r);

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaRezervacije(), uslov1);
        assertEquals(r, nadjiRezervacijeSO.getRezervacije().get(0));

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId";
        uslov += " ORDER BY rezervacija.datum DESC";
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Rezervacija(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            nadjiRezervacijeSO.izvrsi(null);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        assertEquals(null, nadjiRezervacijeSO.getRezervacije());

    }

}

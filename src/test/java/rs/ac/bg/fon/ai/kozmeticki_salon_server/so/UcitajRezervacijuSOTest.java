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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Rezervacija;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.StavkaRezervacije;

/**
 *
 * @author ninic
 */
public class UcitajRezervacijuSOTest {

    private UcitajRezervacijuSO ucitajRezervacijuSO;
    Rezervacija r;
    private Repozitorijum mockRepozitorijum;
    Klijent k;


    @BeforeEach
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        r = new Rezervacija();
        k = new Klijent();
        ucitajRezervacijuSO = new UcitajRezervacijuSO(mockRepozitorijum);
    }

    @AfterEach
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        r = null;
        k = null;
        ucitajRezervacijuSO = null;
    }

    @Test
    public void testGetSetRezervacija() {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Date datum = new Date();
        r = new Rezervacija(1, datum, 120, true, k);
        ucitajRezervacijuSO.setR(r);
        assertEquals(r, ucitajRezervacijuSO.getR());
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajRezervacijuSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da ucita rezervaciju", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajRezervacijuSO.izvrsi(new Klijent());
        });

        assertEquals("Sistem ne moze da ucita rezervaciju", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Date datum = new Date();
        r = new Rezervacija(1, datum, 120, true, k);
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();
        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        r.setStavke(stavke);
        String uslov1 = " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();
        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(rez);
        when(mockRepozitorijum.vratiSve(new StavkaRezervacije(), uslov1)).thenReturn(stavke);
        ucitajRezervacijuSO.izvrsi(r);

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaRezervacije(), uslov1);
        assertEquals(r, ucitajRezervacijuSO.getR());

    }

    @Test
    public void testNijePronadjenKlijentUspesnaOperacija() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Date datum = new Date();
        r = new Rezervacija(1, datum, 120, true, k);
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(new ArrayList());
        ucitajRezervacijuSO.izvrsi(r);

        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        assertEquals(null, ucitajRezervacijuSO.getR());

    }

    @Test
    public void testGreskaUBaziKodUcitavanjaRezervacije() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Date datum = new Date();
        r = new Rezervacija(1, datum, 120, true, k);
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();
        List<Rezervacija> rez = new ArrayList<>();
        rez.add(r);
        List<StavkaRezervacije> stavke = new ArrayList<>();
        r.setStavke(stavke);
        String uslov1 = " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        when(mockRepozitorijum.vratiSve(new Rezervacija(), uslov)).thenReturn(rez);
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new StavkaRezervacije(), uslov1);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajRezervacijuSO.izvrsi(r);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        verify(mockRepozitorijum, times(1)).vratiSve(new StavkaRezervacije(), uslov1);
        assertEquals(null, ucitajRezervacijuSO.getR());

    }

    @Test
    public void testGreskaUBaziKodUcitavanjaStavki() throws Exception {

        k = new Klijent(1, "nina", "nina", "0612077777", new Date());
        Date datum = new Date();
        r = new Rezervacija(1, datum, 120, true, k);
        String uslov = " JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE rezervacija.rezervacijaId=" + r.getRezervacijaId();

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Rezervacija(), uslov);

        Exception exception = assertThrows(Exception.class, () -> {
            ucitajRezervacijuSO.izvrsi(r);
        });

        assertEquals("Greška u bazi", exception.getMessage());
        verify(mockRepozitorijum, times(1)).vratiSve(new Rezervacija(), uslov);
        assertEquals(null, ucitajRezervacijuSO.getR());

    }

}

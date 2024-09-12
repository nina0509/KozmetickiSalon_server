/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.util.ArrayList;
import java.util.Calendar;
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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije ZapamtiKlijentaSO
 *
 * @author Nikolina Baros
 */
public class ZapamtiKlijentaSOTest extends TestCase {

    private ZapamtiKlijentaSO zapamtiKlijentaSO;
    Klijent k;
    private Repozitorijum mockRepozitorijum;

    public ZapamtiKlijentaSOTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        k = new Klijent();
        zapamtiKlijentaSO = new ZapamtiKlijentaSO(mockRepozitorijum);
    }

    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        k = null;
        zapamtiKlijentaSO = null;
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(null);
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

    
    @Test
    public void testImeKlijentaNull() {
        k.setKlijentId(1);
        k.setDatRodj(new Date());
        k.setBrTel("0611111111");
        k.setPrezime("nina");

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(k);
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

  

    @Test
    public void testPrezimeKlijentaNull() {
        k.setKlijentId(1);
        k.setDatRodj(new Date());
        k.setBrTel("0611111111");
        k.setIme("nina");
        
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(k);
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

   

    @Test
    public void testBrTelKlijentaNull() {
        
        k.setKlijentId(1);
        k.setIme("nina");
        k.setDatRodj(new Date());
        k.setIme("nina");

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(k);
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

    @Test
    public void testDatRodjKlijentaNull() {
        k.setKlijentId(1);
        k.setIme("nina");
        k.setBrTel("0612077888");
        k.setIme("nina");
      
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(k);
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

  
    @Test
    public void testPogresanFormatBrTelKlijenta() {

        k = new Klijent(1, "nina", "nina", "7772077888", new Date());

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(k);
        });

        assertEquals("Sistem ne moze da zapamti klijenta", exception.getMessage());
    }

    @Test
    public void testUspehDodavanje_KlijentNePostoji() throws Exception {

        k.setKlijentId(1);
        k.setIme("Nikola");
        k.setPrezime("Petrovic");
        k.setBrTel("061 123 4567");
        k.setDatRodj(new Date());
        when(mockRepozitorijum.vratiSve(new Klijent(), " WHERE klijent.klijentId=1")).thenReturn(new ArrayList<>());

        zapamtiKlijentaSO.izvrsi(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), " WHERE klijent.klijentId=1");
        verify(mockRepozitorijum, times(1)).sacuvaj(k);
        assertEquals("0611234567", k.getBrTel());

    }

    @Test
    public void testUspehAzuriranje_KlijentPostoji() throws Exception {

        k.setKlijentId(1);
        k.setIme("Nikola");
        k.setPrezime("Petrovic");
        k.setBrTel("061 123 4567");
        k.setDatRodj(new Date());
        List<Klijent> klijenti = new ArrayList<>();
        klijenti.add(k);
        when(mockRepozitorijum.vratiSve(new Klijent(), " WHERE klijent.klijentId=1")).thenReturn(klijenti);

        zapamtiKlijentaSO.izvrsiOperaciju(k);

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), " WHERE klijent.klijentId=1");
        verify(mockRepozitorijum, times(1)).izmeni(k);
        assertEquals("0611234567", k.getBrTel());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        k.setKlijentId(1);
        k.setIme("Nikola");
        k.setPrezime("Petrovic");
        k.setBrTel("061 123 4567");
        k.setDatRodj(new Date());
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(new Klijent(), " WHERE klijent.klijentId=1");

        //hvatamo izuzetak
        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiKlijentaSO.izvrsi(k);
        });

        assertEquals("Greška u bazi", exception.getMessage());

        verify(mockRepozitorijum, times(1)).vratiSve(new Klijent(), " WHERE klijent.klijentId=1");
        assertEquals("0611234567", k.getBrTel());

    }
    
  
    

}

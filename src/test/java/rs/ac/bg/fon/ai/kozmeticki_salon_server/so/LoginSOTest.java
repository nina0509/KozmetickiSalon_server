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
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Menadzer;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.Usluga;

/**
 * Klasa koja predstavlja testove sistemske operacije Login
 *
 * @author Nikolina Baros
 */
public class LoginSOTest {

    private LoginSO loginSO;
    Menadzer m;
    private Repozitorijum mockRepozitorijum;


    @BeforeEach
    public void setUp() throws Exception {
        mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        loginSO = new LoginSO(mockRepozitorijum);
        m = new Menadzer();
    }

    @AfterEach
    protected void tearDown() throws Exception {
        mockRepozitorijum = null;
        loginSO = null;
        m = null;
    }
    
    @Test
    public void testGetSetMenadzer() {

        m.setId(2);
        loginSO.setMenadzer(m);
        assertEquals(m, loginSO.getMenadzer());
    }

    @Test
    public void testPredusloviNullParam() {

        Exception exception = assertThrows(Exception.class, () -> {
            loginSO.izvrsi(null);
        });

        assertEquals("Sistem nije ulogovao menadzera", exception.getMessage());
    }

    @Test
    public void testPredusloviDrugaKlasa() {

        Exception exception = assertThrows(Exception.class, () -> {
            loginSO.izvrsi(new Usluga());
        });

        assertEquals("Sistem nije ulogovao menadzera", exception.getMessage());
    }

    @Test
    public void testUspesnaOperacija() throws Exception {

        m = new Menadzer(1, "nina", "nina", "nina", "nina");
        List<Menadzer> menadzeri = new ArrayList<>();
        menadzeri.add(m);
        when(mockRepozitorijum.vratiSve((Menadzer) m, null)).thenReturn(menadzeri);
        loginSO.izvrsi((Menadzer) m);

        verify(mockRepozitorijum, times(1)).vratiSve((Menadzer) m, null);

        assertEquals(m, loginSO.getMenadzer());

    }

    @Test
    public void testPogresniLoginParametri() throws Exception {

        m = new Menadzer(1, "nina", "nina", "nina", "nina");

        List<Menadzer> menadzeri = new ArrayList<>();
        when(mockRepozitorijum.vratiSve((Menadzer) m, null)).thenReturn(menadzeri);
        loginSO.izvrsi((Menadzer) m);

        verify(mockRepozitorijum, times(1)).vratiSve((Menadzer) m, null);

        assertEquals(null, loginSO.getMenadzer());

    }

    @Test
    public void testGreskaUBazi() throws Exception {

        m = new Menadzer(1, "nina", "nina", "nina", "nina");

        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve((Menadzer) m, null);

        Exception exception = assertThrows(Exception.class, () -> {
            loginSO.izvrsi((Menadzer) m);

        });

        assertEquals("Greška u bazi", exception.getMessage());
        assertEquals(null, loginSO.getMenadzer());
        verify(mockRepozitorijum, times(1)).vratiSve((Menadzer) m, null);

    }
    
    
    

}

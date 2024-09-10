/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package rs.ac.bg.fon.ai.kozmeticki_salon_server.so;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.Repozitorijum;
import rs.ac.bg.fon.ai.kozmeticki_salon_server.repozitorijum.db.impl.DbRepozitorijumGenericki;
import rs.ac.bg.fon.ai.kozmeticki_salon_zajednicki.domen.*;

/**
 * Klasa koja predstavlja testove sistemske operacije ZapamtiStatistikeSO
 *
 * @author Nikolina Baros
 */
public class ZapamtiStatistikeSOTest extends TestCase {
    
     private ZapamtiStatistikeSO zapamtiStatistikeSO;
    Statistika s;
    private Repozitorijum mockRepozitorijum;
    
    public ZapamtiStatistikeSOTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
         mockRepozitorijum = mock(DbRepozitorijumGenericki.class);
        s = new Statistika();
        zapamtiStatistikeSO = new ZapamtiStatistikeSO(mockRepozitorijum);
    }
    
    @Override
    protected void tearDown() throws Exception {
        mockRepozitorijum=null;
        s=null;
        zapamtiStatistikeSO=null;
    }

     @Test
    public void testUspesnoKreiranjaNoveStatistike() throws Exception {
       
        when(mockRepozitorijum.vratiSve(any(Statistika.class), anyString())).thenReturn(new ArrayList<>());
        
       
        List<StavkaRezervacije> stavkeRezervacije = new ArrayList<>();
        StavkaRezervacije sr1 = new StavkaRezervacije();
        sr1.setUsluga(new Usluga(1, "Manikir",120,1200, new TipUsluge(1, "Nega")));
        stavkeRezervacije.add(sr1);
        
        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavkeRezervacije);
        
        zapamtiStatistikeSO.izvrsiOperaciju(new Statistika());
        
        s=new Statistika(2024, 1);
        StavkaStatistike ss=new StavkaStatistike(s, new Usluga(1, "Manikir",120,1200, new TipUsluge(1, "Nega")), 1);
    
        verify(mockRepozitorijum).vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE EXTRACT(YEAR FROM rezervacija.datum)=EXTRACT(YEAR FROM current_date) ORDER BY usluga.uslugaId");
        verify(mockRepozitorijum).vratiSve(new Statistika(), " WHERE statistika.godina=2024");
        verify(mockRepozitorijum).sacuvaj(s);
        verify(mockRepozitorijum).sacuvaj(ss);
    }
    
    
     @Test
    public void testUspesnoAzuriranjePostojeceStatistike() throws Exception {
       
        s = new Statistika(LocalDate.now().getYear(), 1);
        List<Statistika> statList = new ArrayList<>();
        statList.add(s);
        StavkaStatistike ss=new StavkaStatistike(s, new Usluga(1, "Manikir",120,1200, new TipUsluge(1, "Nega")), 1);
        List<StavkaStatistike> stavkeStat=new ArrayList<>();
        stavkeStat.add(ss);
        s.setStavke(stavkeStat);
        
        
        StavkaStatistike ss1=new StavkaStatistike(s, new Usluga(2, "Pedikir",120,1200, new TipUsluge(1, "Nega")), 1);

        List<StavkaRezervacije> stavkeRezervacije = new ArrayList<>();
        StavkaRezervacije sr1 = new StavkaRezervacije();
        sr1.setUsluga(new Usluga(1, "Manikir",120,1200, new TipUsluge(1, "Nega")));
        stavkeRezervacije.add(sr1);
        StavkaRezervacije sr2 = new StavkaRezervacije();
        sr2.setUsluga(new Usluga(2, "Pedikir",120,1200, new TipUsluge(1, "Nega")));
        stavkeRezervacije.add(sr2);
        
        when(mockRepozitorijum.vratiSve(any(StavkaRezervacije.class), anyString())).thenReturn(stavkeRezervacije);
        when(mockRepozitorijum.vratiSve(any(Statistika.class), anyString())).thenReturn(statList);
        

        zapamtiStatistikeSO.izvrsiOperaciju(new Statistika());
        
       Statistika s1=new Statistika(2024, 2);
        List<StavkaStatistike> stavkeNove=new ArrayList<>();
       stavkeNove.add(ss);
       stavkeNove.add(ss1);
        s1.setStavke(stavkeNove);
       
        verify(mockRepozitorijum).vratiSve(new StavkaRezervacije(), " JOIN rezervacija ON rezervacija.rezervacijaId=stavkarezervacije.rezervacijaId JOIN usluga ON stavkarezervacije.uslugaId=usluga.uslugaId JOIN tipusluge ON usluga.tipId=tipusluge.tipId JOIN klijent ON klijent.klijentId=rezervacija.klijentId WHERE EXTRACT(YEAR FROM rezervacija.datum)=EXTRACT(YEAR FROM current_date) ORDER BY usluga.uslugaId");
        verify(mockRepozitorijum).vratiSve(new Statistika(), " WHERE statistika.godina=2024");
        verify(mockRepozitorijum).izmeni(s1);
        verify(mockRepozitorijum).izbrisi(ss);
        verify(mockRepozitorijum).sacuvaj(ss);
        verify(mockRepozitorijum).izbrisi(ss1);
        verify(mockRepozitorijum).sacuvaj(ss1);
    }
    
     @Test
    public void testGreskaUBazi() throws Exception {
       
       
        doThrow(new Exception("Greška u bazi")).when(mockRepozitorijum).vratiSve(any(StavkaRezervacije.class), anyString());

        Exception exception = assertThrows(Exception.class, () -> {
            zapamtiStatistikeSO.izvrsiOperaciju(new Statistika());
        });
       
        assertEquals("Greška u bazi", exception.getMessage());
        
      
       verify(mockRepozitorijum, times(1)).vratiSve(any(StavkaRezervacije.class), anyString());
      
    }
    
    
   
}

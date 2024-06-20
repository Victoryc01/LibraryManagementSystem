package libraryMGMTT.service;

import libraryMGMTT.entity.Patron;
import libraryMGMTT.repository.PatronRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class PatronServiceTest {

    @InjectMocks
    private PatronService patronService;
    @Mock
    private PatronRepo patronRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPatrons(){

        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("Blaze");

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Vic");


        List<Patron> patron = Arrays.asList(patron1,patron2);
        when(patronRepo.findAll()).thenReturn(patron);

        List<Patron> result = patronService.getAllPatron();
        assertEquals(2, result.size());
        assertEquals("Blaze", result.get(0).getName());
    }

    @Test
    public void testGetPatronById() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Blaze");

        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));

        Optional<Patron> result = Optional.ofNullable(patronService.getPatronById(1L));
        assertTrue(result.isPresent());
        assertEquals("Blaze", result.get().getName());
    }

    @Test
    public void testAddPatron() {
        Patron patron = new Patron();
        patron.setName("Blaze");

        when(patronRepo.save(patron)).thenReturn(patron);

        Patron result = patronService.addPatron(patron);
        assertEquals("Blaze", result.getName());
    }

    @Test
    public void testUpdatePatron() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Old Blaze");
        patron.setContactInfo("Abuja");

        Patron updatedPatron = new Patron();
        updatedPatron.setName("New Blaze");
        updatedPatron.setContactInfo("Lagos");

        when(patronRepo.existsById(1L)).thenReturn(true);
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepo.save(patron)).thenReturn(patron);

        Optional<Patron> result = patronService.updatePatron(1L, updatedPatron);
        assertTrue(result.isPresent());
        assertEquals("New Blaze", result.get().getName());
        assertEquals("Lagos", result.get().getContactInfo());

    //    verify(patronRepo, times(1)).existsById(1L);
        verify(patronRepo, times(1)).findById(1L);
        verify(patronRepo, times(1)).save(patron);
    }


    @Test
    public void testDeletePatron() {
        Patron patron = new Patron();
        patron.setId(1L);

        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));

        patronService.deletePatron(1L);

        verify(patronRepo, times(1)).findById(1L);
        verify(patronRepo, times(1)).delete(patron);
    }

    @Test
    public void testDeletePatronNotFound() {
        when(patronRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            patronService.deletePatron(1L);
        });

        assertEquals("No book found with id 1", exception.getMessage());

        verify(patronRepo, times(1)).findById(1L);
        verify(patronRepo, never()).delete(any(Patron.class));
    }
}

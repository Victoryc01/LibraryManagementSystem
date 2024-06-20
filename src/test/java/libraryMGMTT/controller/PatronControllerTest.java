package libraryMGMTT.controller;

import libraryMGMTT.entity.Patron;
import libraryMGMTT.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatronControllerTest {

    @InjectMocks
    private PatronController patronController;

    @Mock
    private PatronService patronService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController)
                .build();
    }

    @Test
    public void testGetAllPatron() throws Exception {
        Patron patron1= new Patron();
        patron1.setId(1L);
        patron1.setName("Blaze");

        Patron patron2= new Patron();
        patron2.setId(2L);
        patron2.setName("Vic");

        List<Patron> patrons = Arrays.asList(patron1,patron2);

        when(patronService.getAllPatron()).thenReturn(patrons);

        mockMvc.perform(get("/api/patrons/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Blaze"))
                .andExpect(jsonPath("$[1].name").value("Vic"));

        verify(patronService, times(1)).getAllPatron();
    }

    @Test
    public void testGetPatronById() throws Exception {
        Patron patron1= new Patron();
        patron1.setId(1L);
        patron1.setName("Blaze");

        when(patronService.getPatronById(1L)).thenReturn(patron1);

        mockMvc.perform(get("/api/patrons/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Blaze"));

        verify(patronService, times(1)).getPatronById(1L);
    }

    @Test
    public void testAddPatron() throws Exception {
        Patron patron1= new Patron();
        patron1.setName("Blaze");


        when(patronService.addPatron(any(Patron.class))).thenReturn(patron1);

        mockMvc.perform(post("/api/patrons/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Blaze\", \"contactInfo\": \"Lagos\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Blaze"));

        verify(patronService, times(1)).addPatron(any(Patron.class));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        Patron patron1= new Patron();
        patron1.setId(1L);
        patron1.setName("Old Blaze");

        Patron updatedPatron = new Patron();
        updatedPatron.setName("New Blaze");

        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(Optional.of(updatedPatron));

        mockMvc.perform(put("/api/patrons/updatePatron/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Blaze\", \"contactInfo\": \"Lagos\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Blaze"));

        verify(patronService, times(1)).updatePatron(eq(1L), any(Patron.class));
    }

    @Test
    public void testDeletePatron() throws Exception {
        doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/deletePatron/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Patron with id 1 deleted"));

        verify(patronService, times(1)).deletePatron(1L);
    }
}

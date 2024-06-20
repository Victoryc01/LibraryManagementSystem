package libraryMGMTT.controller;

import libraryMGMTT.entity.Book;
import libraryMGMTT.entity.BorrowingRecord;
import libraryMGMTT.entity.Patron;
import libraryMGMTT.exceptions.GlobalExceptionHandler;
import libraryMGMTT.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BorrowingRecordControllerTest {

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testBorrowBook() throws Exception {
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());

        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(Optional.of(record));

        mockMvc.perform(post("/api/borrowingRecord/borrowBook/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L));

        verify(borrowingRecordService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    public void testBorrowBookNotFound() throws Exception {
        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/borrowingRecord/borrowBook/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book or Patron not found"));

        verify(borrowingRecordService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    public void testReturnBook() throws Exception {
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(LocalDate.now());

        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(Optional.of(record));

        mockMvc.perform(put("/api/borrowingRecord/returnBook/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L))
                .andExpect(jsonPath("$.returnDate").exists());

        verify(borrowingRecordService, times(1)).returnBook(1L, 1L);
    }

    @Test
    public void testReturnBookNotFound() throws Exception {
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/borrowingRecord/returnBook/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Borrowing record not found"));

        verify(borrowingRecordService, times(1)).returnBook(1L, 1L);
    }
}

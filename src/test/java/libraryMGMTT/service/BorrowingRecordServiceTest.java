package libraryMGMTT.service;

import libraryMGMTT.entity.Book;
import libraryMGMTT.entity.BorrowingRecord;
import libraryMGMTT.entity.Patron;
import libraryMGMTT.repository.BookRepo;
import libraryMGMTT.repository.BorrowingRecordRepo;
import libraryMGMTT.repository.PatronRepo;
import libraryMGMTT.service.serviceImpl.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowingRecordServiceTest {

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    @Mock
    private BorrowingRecordRepo borrowingRecordRepository;

    @Mock
    private BookRepo bookRepository;

    @Mock
    private PatronRepo patronRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowBook() {
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(record);

        Optional<BorrowingRecord> result = borrowingRecordService.borrowBook(1L, 1L);
        assertTrue(result.isPresent());
        assertEquals(record, result.get());

        verify(bookRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findById(1L);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    public void testReturnBook() {
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());

        when(borrowingRecordRepository.findByBookIdAndPatronId(1L, 1L)).thenReturn(Optional.of(record));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(record);

        Optional<BorrowingRecord> result = borrowingRecordService.returnBook(1L, 1L);
        assertTrue(result.isPresent());
        assertEquals(record, result.get());

        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronId(1L, 1L);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    public void testBorrowBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingRecordService.borrowBook(1L, 1L);
        });

        assertEquals("Book or Patron not found", exception.getMessage());

        verify(bookRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findById(1L);
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
    }

    @Test
    public void testReturnBookNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronId(1L, 1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingRecordService.returnBook(1L, 1L);
        });

        assertEquals("Borrowing record not found", exception.getMessage());

        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronId(1L, 1L);
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
    }
}

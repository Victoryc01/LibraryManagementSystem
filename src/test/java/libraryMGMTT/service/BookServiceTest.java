package libraryMGMTT.service;

import libraryMGMTT.entity.Book;
import libraryMGMTT.repository.BookRepo;
import libraryMGMTT.service.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepo bookRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks(){
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");

        List<Book> books = Arrays.asList(book1,book2);
        when(bookRepo.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = Optional.ofNullable(bookService.getBookById(1L));
        assertTrue(result.isPresent());
        assertEquals("Book 1", result.get().getTitle());
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("New Book");

        when(bookRepo.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);
        assertEquals("New Book", result.getTitle());
    }

    @Test
    public void testUpdateBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setTitle("New Title");
        updatedBook.setAuthor("New Author");
        updatedBook.setPublicationYear(2020L);
        updatedBook.setIsbn("1234567890");

        when(bookRepo.existsById(1L)).thenReturn(true);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepo.save(book)).thenReturn(book);

        Optional<Book> result = bookService.updateBooks(1L, updatedBook);
        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        assertEquals("New Author", result.get().getAuthor());
        assertEquals(2020, result.get().getPublicationYear());
        assertEquals("1234567890", result.get().getIsbn());

        verify(bookRepo, times(1)).findById(1L);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepo, times(1)).findById(1L);
        verify(bookRepo, times(1)).delete(book);
    }

    @Test
    public void testDeleteBookNotFound() {
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.deleteBook(1L);
        });

        assertEquals("No book found with id 1", exception.getMessage());

        verify(bookRepo, times(1)).findById(1L);
        verify(bookRepo, never()).delete(any(Book.class));
    }

}

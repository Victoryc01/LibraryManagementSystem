package libraryMGMTT.controller;

import libraryMGMTT.entity.Book;
import libraryMGMTT.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = new Book();
        book.setTitle("New Book");

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book\", \"author\": \"Author\", \"publicationYear\": 2020, \"isbn\": \"1234567890\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setTitle("New Title");

        when(bookService.updateBooks(eq(1L), any(Book.class))).thenReturn(Optional.of(updatedBook));

        mockMvc.perform(put("/api/books/updateBook/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Title\", \"author\": \"Author\", \"publicationYear\": 2020, \"isbn\": \"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"));

        verify(bookService, times(1)).updateBooks(eq(1L), any(Book.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/deleteBook/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with id 1 deleted"));

        verify(bookService, times(1)).deleteBook(1L);
    }
}

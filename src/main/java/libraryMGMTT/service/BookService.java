package libraryMGMTT.service;

import libraryMGMTT.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book addBook(Book book);
    Optional<Book> updateBooks(Long id, Book bookDetails);
    void deleteBook(Long id);




}

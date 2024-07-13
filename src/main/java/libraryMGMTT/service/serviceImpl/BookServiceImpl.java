package libraryMGMTT.service.serviceImpl;

import libraryMGMTT.entity.Book;
import libraryMGMTT.repository.BookRepo;
import libraryMGMTT.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    @Cacheable(value = "books")
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    @Cacheable(value = "book", key = "#id")
    public Book getBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No book found with id " + id));
    }

    @Override
    @CachePut(value = "book", key = "book.id")
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    @CachePut(value = "book", key = "#id")
    public Optional<Book> updateBooks(Long id, Book bookDetails) {
            return bookRepo.findById(id).map(book -> {
                book.setTitle(bookDetails.getTitle());
                book.setAuthor(bookDetails.getAuthor());
                book.setPublicationYear(bookDetails.getPublicationYear());
                book.setIsbn(bookDetails.getIsbn());
                return bookRepo.save(book);
            });
    }

    @Override
    @CacheEvict(value = "book", key = "#id")
    public void deleteBook(Long id) {
        Book book =bookRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No book found with id "+id));
        bookRepo.delete(book);
    }
}

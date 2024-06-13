package libraryMGMTT.service.serviceImpl;

import libraryMGMTT.entity.Book;
import libraryMGMTT.repository.BookRepo;
import libraryMGMTT.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No book found with id " + id));
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Optional<Book> updateBooks(Long id, Book bookDetails) {
        if (bookRepo.existsById(id)) {
            return bookRepo.findById(id).map(book -> {
                book.setTitle(bookDetails.getTitle());
                book.setAuthor(bookDetails.getAuthor());
                book.setPublicationYear(bookDetails.getPublicationYear());
                book.setIsbn(bookDetails.getIsbn());
                return bookRepo.save(book);
            });
        }
          throw new RuntimeException("No book found with id "+id);
    }

    @Override
    public void deleteBook(Long id) {
        Book deleteBook = getBookById(id);
            bookRepo.delete(deleteBook);
    }
}

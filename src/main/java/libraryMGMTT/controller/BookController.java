package libraryMGMTT.controller;

import libraryMGMTT.entity.Book;
import libraryMGMTT.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/getAll")
    public List<Book> getAllBooks(){
       return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBooks(id, bookDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteBook/{id}")
    public void deleteBooks(@PathVariable Long id){
        bookService.deleteBook(id);
    }
}

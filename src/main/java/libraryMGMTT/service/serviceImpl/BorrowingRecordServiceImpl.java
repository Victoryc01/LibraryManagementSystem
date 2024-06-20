package libraryMGMTT.service.serviceImpl;

import libraryMGMTT.entity.Book;
import libraryMGMTT.entity.BorrowingRecord;
import libraryMGMTT.entity.Patron;
import libraryMGMTT.repository.BookRepo;
import libraryMGMTT.repository.BorrowingRecordRepo;
import libraryMGMTT.repository.PatronRepo;
import libraryMGMTT.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepo borrowingRecordRepo;
    private final BookRepo bookRepo;
    private final PatronRepo patronRepo;


    @Override
    @Transactional
    public Optional<BorrowingRecord> borrowBook(Long bookId, Long patronId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);
        Optional<Patron> patronOpt = patronRepo.findById(patronId);

        if (bookOpt.isPresent() && patronOpt.isPresent()){
            Book book = bookOpt.get();
            Patron patron = patronOpt.get();

            BorrowingRecord record = new BorrowingRecord();
            record.setBook(book);
            record.setPatron(patron);
            record.setBorrowDate(LocalDate.now());

            return Optional.of(borrowingRecordRepo.save(record));
        }
            throw new RuntimeException("Book or Patron not found");
    }

    @Override
    @Transactional
    public Optional<BorrowingRecord> returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> recordOpt = borrowingRecordRepo.findByBookIdAndPatronId(bookId, patronId);

        if (recordOpt.isPresent()){
            BorrowingRecord record = recordOpt.get();
            record.setReturnDate(LocalDate.now());
            return Optional.of(borrowingRecordRepo.save(record));
        }
            throw new RuntimeException("Borrowing record not found");
    }
}

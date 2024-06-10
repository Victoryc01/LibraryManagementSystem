package libraryMGMTT.service;

import libraryMGMTT.entity.BorrowingRecord;

import java.util.Optional;

public interface BorrowingRecordService {

    Optional<BorrowingRecord> borrowBook(Long bookId, Long patronId);
    Optional<BorrowingRecord> returnBook(Long bookId, Long patronId);

}

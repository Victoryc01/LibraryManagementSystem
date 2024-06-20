package libraryMGMTT.controller;

import libraryMGMTT.entity.BorrowingRecord;
import libraryMGMTT.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/borrowingRecord")
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrowBook/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId){

        return borrowingRecordService.borrowBook(bookId, patronId)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book or Patron not found"));
    }

    @PutMapping("/returnBook/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
        return borrowingRecordService.returnBook(bookId, patronId)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrowing record not found"));
    }
}

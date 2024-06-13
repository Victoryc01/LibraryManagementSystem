package libraryMGMTT.controller;

import libraryMGMTT.entity.BorrowingRecord;
import libraryMGMTT.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrowingRecord")
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @PostMapping("borrowBook/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId){
        return borrowingRecordService.borrowBook(bookId, patronId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("returnBook/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
        return borrowingRecordService.returnBook(bookId, patronId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

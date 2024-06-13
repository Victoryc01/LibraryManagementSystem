package libraryMGMTT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    @ManyToOne
    private Book book;
    @ManyToOne
    private Patron patron;

}

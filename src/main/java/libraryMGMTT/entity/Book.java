package libraryMGMTT.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 50, message = "Title should not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 50, message = "Author should not exceed 50 characters")
    private String author;

    @NotNull(message = "Publication year is mandatory")
    private Long publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Size(max = 20, message = "ISBN should not exceed 20 characters")
    private String isbn;

}

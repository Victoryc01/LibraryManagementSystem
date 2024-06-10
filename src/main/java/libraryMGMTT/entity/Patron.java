package libraryMGMTT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patron")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String name;
    String contactInfo;


    //● Patron: Contains details like ID, name, contact information, etc.
}

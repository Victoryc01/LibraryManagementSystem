package libraryMGMTT.controller;

import libraryMGMTT.entity.Patron;
import libraryMGMTT.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
@Validated
public class PatronController {

    private final PatronService patronService;

    @GetMapping("/getAll")
    public List<Patron> getAllPatron(){
       return patronService.getAllPatron();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id){
        Patron patron = patronService.getPatronById(id);
        if (patron != null){
            return new ResponseEntity<>(patron, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron){
        Patron addPatron = patronService.addPatron(patron);
        return new ResponseEntity<>(addPatron, HttpStatus.CREATED);
    }

    @PutMapping("/updatePatron/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron){
        return patronService.updatePatron(id, patron)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron not found"));
    }

    @DeleteMapping("/deletePatron/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable Long id){
        try {
            patronService.deletePatron(id);
            return ResponseEntity.ok().body("Patron with id "+id+" deleted");
        }catch (RuntimeException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}

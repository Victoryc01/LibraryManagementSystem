package libraryMGMTT.controller;

import libraryMGMTT.entity.Patron;
import libraryMGMTT.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Patron getPatronById(@PathVariable Long id){
        return patronService.getPatronById(id);
    }

    @PostMapping("/add")
    public Patron addPatron(@RequestBody Patron patron){
        return patronService.addPatron(patron);
    }

    @PutMapping("/updatePatron/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron){
        return patronService.updatePatron(id, patron)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletePatron/{id}")
    public void deletePatron(@PathVariable Long id){
         patronService.deletePatron(id);
    }

}

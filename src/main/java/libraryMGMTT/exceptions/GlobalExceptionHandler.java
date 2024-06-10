package libraryMGMTT.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){

        return ResponseEntity.badRequest().body(e.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){

        return ResponseEntity.status(500).body(e.getMessage());
    }
}

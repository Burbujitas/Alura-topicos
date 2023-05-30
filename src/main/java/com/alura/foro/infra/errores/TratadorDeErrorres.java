package com.alura.foro.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratadorDeErrorres {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratasError404(){ return ResponseEntity.notFound().build(); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratasError400(MethodArgumentNotValidException e){
        var errores= e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity prueba(SQLIntegrityConstraintViolationException e)
    {
        return ResponseEntity.badRequest().build();
    }
    private record DatosErrorValidacion(String campo,String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

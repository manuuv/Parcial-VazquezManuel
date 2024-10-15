package com.example.parcial.controllers;

import com.example.parcial.entities.Persona;
import com.example.parcial.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/parcial/")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @PostMapping ("/mutant")
    public ResponseEntity<?> esMutante (@RequestBody Persona entity) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.esMutante(entity));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno del servidor. Por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> calcStats(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.calcStats());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno del servidor. Por favor intente más tarde.\"}");
        }
    }
}

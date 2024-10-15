package com.example.parcial.repositories;

import com.example.parcial.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface PersonaRepository <E extends Persona, ID extends Serializable> extends JpaRepository<E, ID> {
    Persona findByDna(String[] dna);
    int countByEsMutanteTrue();
    int countByEsMutanteFalse();

}

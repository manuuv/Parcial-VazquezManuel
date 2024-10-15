package com.example.parcial.services;

import com.example.parcial.entities.Persona;
import com.example.parcial.entities.Stats;
import com.example.parcial.repositories.PersonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackages = "com.example.parcial")
class PersonaServiceTest {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaService personaService;

    @BeforeEach
    void setUp() {
        personaRepository.deleteAll();
    }

    @Test
    void testCalcStats() throws Exception {

        Persona mutante = new Persona();
        mutante.setEsMutante(true);
        personaRepository.save(mutante);

        Persona humano = new Persona();
        humano.setEsMutante(false);
        personaRepository.save(humano);

        Stats stats = personaService.calcStats();

        assertEquals(1, stats.getCount_mutant_dna());
        assertEquals(1, stats.getCount_human_dna());
        assertEquals(1.0, stats.getRatio());
    }

    @Test
    void testEsMutante_NuevoMutante() throws Exception {
        Persona persona = new Persona();
        persona.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});

        Persona result = personaService.esMutante(persona);

        assertTrue(result.isEsMutante());
    }

    @Test
    void testEsMutante_MutanteExtistente() throws Exception {
        Persona persona = new Persona();
        persona.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        personaService.esMutante(persona);

        Persona persona2 = new Persona();
        persona2.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});

        Persona result = personaService.esMutante(persona2);

        assertTrue(result.isEsMutante());
    }

    @Test
    void testEsMutante_AdnHumano() {
        Persona persona = new Persona();
        persona.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});

        Exception exception = assertThrows(Exception.class, () -> {
            personaService.esMutante(persona);
        });

        assertEquals("El ADN proporcionado no es el de un mutante", exception.getMessage());
    }

    @Test
    void testFindAll() throws Exception {
        Persona persona1 = new Persona();
        persona1.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        personaService.esMutante(persona1);

        Persona persona2 = new Persona();
        persona2.setDna(new String[]{"ATGCGA", "CAGTGC", "CTATGT", "AGAATG", "CCCCTA", "TCACTG"});
        personaService.esMutante(persona2);

        List<Persona> result = personaService.findAll();

        assertEquals(2, result.size());
    }


    @Test
    void testEsMutante_MatrizNxM() {
        Persona persona = new Persona();
        String[] dna = {"ATGCGA", "CAGTGC", "AGAAGG", "CCCCTA", "TCACTGA"};
        persona.setDna(dna);

        Exception exception = assertThrows(Exception.class, () -> {
            personaService.esMutante(persona);
        });

        assertEquals("Error. El ADN debe estar representado como matriz NXN.", exception.getMessage());
    }

    @Test
    void testEsMutante_CaracterInvalido() {
        Persona persona = new Persona();
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "CACTGX"};
        persona.setDna(dna);

        Exception exception = assertThrows(Exception.class, () -> {
            personaService.esMutante(persona);
        });

        assertEquals("Error. El ADN proporcionado sólo debe contener las letras A, T, C, G.", exception.getMessage());
    }
}

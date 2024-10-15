package com.example.parcial.services;

import com.example.parcial.entities.Persona;
import com.example.parcial.entities.Stats;
import com.example.parcial.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public Stats calcStats() throws Exception {
        Stats stats = new Stats();
        stats.setCount_mutant_dna(personaRepository.countByEsMutanteTrue());
        stats.setCount_human_dna(personaRepository.countByEsMutanteFalse());
        if(stats.getCount_human_dna() == 0){
            stats.setRatio(stats.getCount_mutant_dna());
            return stats;
        }
        stats.setRatio((double) stats.getCount_mutant_dna() / stats.getCount_human_dna());
        return stats;
    }

    @Transactional
    public Persona esMutante(Persona entity) throws Exception {
        try{

            Persona personaGuardada = personaRepository.findByDna(entity.getDna());

            if(personaGuardada != null){


                if(!personaGuardada.isEsMutante()){
                    throw new Exception("El ADN proporcionado no es el de un mutante");
                }

                return personaGuardada;

            }else{

                entity.setEsMutante(isMutant(entity.getDna()));
                entity = (Persona) personaRepository.save(entity);
                if(!entity.isEsMutante()){
                    throw new Exception("El ADN proporcionado no es el de un mutante");
                }
                return entity;
            }

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Persona> findAll() throws Exception {
        try{
            List<Persona> entities = personaRepository.findAll();
            return entities;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean isMutant(String[] dna) throws Exception {

        int n = dna.length;

        for (String fila : dna) {
            if (fila.length() != n) {
                throw new Exception("Error. El ADN debe estar representado como matriz NXN.");
            }
        }

        for (String fila : dna) {
            for (char c : fila.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    throw new Exception("Error. El ADN proporcionado sólo debe contener las letras A, T, C, G.");
                }
            }
        }
        char[][] matrix = new char[n][n];



        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (checkSequence(matrix, i, j, n)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkSequence(char[][] matrix, int fila, int col, int n) {
        char base = matrix[fila][col];

        if (col + 3 < n &&
                base == matrix[fila][col+1] &&
                base == matrix[fila][col+2] &&
                base == matrix[fila][col+3]) {
            return true;
        }

        if (fila + 3 < n &&
                base == matrix[fila+1][col] &&
                base == matrix[fila+2][col] &&
                base == matrix[fila+3][col]) {
            return true;
        }

        if (fila + 3 < n && col + 3 < n &&
                base == matrix[fila+1][col+1] &&
                base == matrix[fila+2][col+2] &&
                base == matrix[fila+3][col+3]) {
            return true;
        }

        if (fila - 3 >= 0 && col + 3 < n &&
                base == matrix[fila-1][col+1] &&
                base == matrix[fila-2][col+2] &&
                base == matrix[fila-3][col+3]) {
            return true;
        }
        return false;
    }
}

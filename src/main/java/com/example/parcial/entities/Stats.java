package com.example.parcial.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    private int count_mutant_dna;
    private int count_human_dna;
    private double ratio;

}



package com.example.parcial.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.*;


import jakarta.persistence.*;

@Entity
@Table(name = "persona")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String[] dna;
    private boolean esMutante;


}

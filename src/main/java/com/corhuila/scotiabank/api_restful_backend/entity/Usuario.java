package com.corhuila.scotiabank.api_restful_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToMany
    @JoinTable(
            name = "programa_academico_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "programa_academico_id")
    )
    private Set<ProgramaAcademico> programasAcademicos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private List<Actividad> actividades;
}

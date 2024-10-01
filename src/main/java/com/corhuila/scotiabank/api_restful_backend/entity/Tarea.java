package com.corhuila.scotiabank.api_restful_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sub_actividad_id", nullable = false)
    private SubActividad subActividad;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private Boolean asignable = true;
}

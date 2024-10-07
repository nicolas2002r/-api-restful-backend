package com.corhuila.scotiabank.api_restful_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_actividades")
public class SubActividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;

    @OneToMany(mappedBy = "subActividad")
    private List<Tarea> tareas;
}

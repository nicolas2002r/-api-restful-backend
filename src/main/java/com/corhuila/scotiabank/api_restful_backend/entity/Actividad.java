package com.corhuila.scotiabank.api_restful_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipoActividad;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal dedicacionSemanal;

    @Column(nullable = false)
    private BigDecimal dedicacionSemestral;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "actividad")
    private List<SubActividad> subActividades;
}

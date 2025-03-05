package com.verdeando.backend.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Log {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")   
    private String idLog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAccion tipoAccion;

    @ManyToOne @JoinColumn(name = "id", nullable = false)
    private Usuario usuario;

    @Column(nullable = true)
    private String detalle;

    @Column(nullable = false)
    private LocalDateTime fechaAccion;

}

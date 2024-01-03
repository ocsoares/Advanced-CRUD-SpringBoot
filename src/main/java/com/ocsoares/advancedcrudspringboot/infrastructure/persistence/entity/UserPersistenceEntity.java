package com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

// Entidade de Usuário que VAI ser Salva no BANCO de DADOS, por isso tem o "id", por exemplo!!!
@Entity
@Table(name = "users")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class UserPersistenceEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private final String name;

    @Column(unique = true, nullable = false)
    private final String email;

    @Column(nullable = false)
    private final String password;
}

package com.estudos.br.pdv.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TB_USUARIO")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório.")
    private String name;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo username é obrigatório.")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "O campo senha é obrigatório.")
    private String password;

    private boolean isEnabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Sale> sales;
}

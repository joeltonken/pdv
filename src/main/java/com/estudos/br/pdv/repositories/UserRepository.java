package com.estudos.br.pdv.repositories;

import com.estudos.br.pdv.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

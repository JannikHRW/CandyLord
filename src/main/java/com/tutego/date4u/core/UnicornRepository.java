package com.tutego.date4u.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnicornRepository extends JpaRepository<Unicorn, Long> {
    Unicorn findByEmail(String email);
}
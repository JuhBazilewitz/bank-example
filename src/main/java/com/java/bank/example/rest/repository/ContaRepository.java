package com.java.bank.example.rest.repository;

import com.java.bank.example.rest.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}

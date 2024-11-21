package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entity.Funcionario;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Consulta para quantidade de funcionários por cargo
    @Query("SELECT f.cargo, COUNT(f) FROM Funcionario f GROUP BY f.cargo")
    List<Object[]> findFuncionariosPorCargo();

    // Consulta para média salarial por departamento
    @Query("SELECT f.departamento, AVG(f.salario) FROM Funcionario f GROUP BY f.departamento")
    List<Object[]> findMediaSalarialPorDepartamento();
}

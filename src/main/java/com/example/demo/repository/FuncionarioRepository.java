package com.example.demo.repository;

import com.example.demo.entity.Funcionario;
import com.example.demo.model.RelatorioCargo;
import com.example.demo.model.RelatorioDepartamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Consulta com paginação e ordenação
    Page<Funcionario> findAll(Pageable pageable);

    // Consulta para obter a quantidade de funcionários por cargo (em formato genérico)
    @Query("SELECT f.cargo, COUNT(f) FROM Funcionario f GROUP BY f.cargo")
    List<Object[]> findFuncionariosPorCargo();

    // Consulta para obter a quantidade de funcionários por cargo (usando RelatorioCargo)
    @Query("SELECT new com.example.demo.model.RelatorioCargo(f.cargo, COUNT(f)) " +
           "FROM Funcionario f GROUP BY f.cargo")
    List<RelatorioCargo> countFuncionariosPorCargo();

    @Query("SELECT new com.example.demo.model.RelatorioDepartamento(f.departamento, SUM(f.salario)) " +
    	       "FROM Funcionario f GROUP BY f.departamento")
    	List<RelatorioDepartamento> somaSalariosPorDepartamento();






}

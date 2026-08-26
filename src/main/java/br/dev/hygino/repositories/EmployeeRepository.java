package br.dev.hygino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.hygino.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

package org.github.ehayik.playground.repositories;

import org.github.ehayik.playground.entities.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @EntityGraph(attributePaths = "employees")
    @Query("FROM Department dp WHERE dp.name = :name")
    Optional<Department> findByNameWithEmployees(@Param("name") String name);
}

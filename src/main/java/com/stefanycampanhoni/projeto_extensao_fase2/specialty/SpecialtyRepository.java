package com.stefanycampanhoni.projeto_extensao_fase2.specialty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    @Query(value = "select s.type from specialty s", nativeQuery = true)
    Set<String> findAllSpecialtyTypes();

    @Query(value = "select * from specialty s " +
            "where s.type = :type",
           nativeQuery = true)
    List<Specialty> findAvailableSpecialtiesByType(@Param("type") String type);
}

package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Integer> {
    @Query(value = "select m.* from mentor m " +
            "join city c on m.city_id = c.id " +
            "join specialty s on m.specialty_id = s.id " +
            "where m.name ilike %:name% " +
            "and c.name ilike %:cityName% " +
            "and s.type ilike %:specialtyType%",
           nativeQuery = true)
    List<Mentor> findMentorsByFilters(@Param("name") String name,
                                      @Param("cityName") String cityName,
                                      @Param("specialtyType") String specialtyType);
    Optional<Mentor> findByEmail(String email);
}

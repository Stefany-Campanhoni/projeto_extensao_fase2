package com.stefanycampanhoni.projeto_extensao_fase2.specialty;

import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public Specialty save(SpecialtyDto specialtyDto) {
        Specialty specialty = Specialty.builder()
                .name(specialtyDto.name())
                .type(specialtyDto.type())
                .build();

        return specialtyRepository.save(specialty);
    }

    public Specialty findById(Integer id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specialty not found"));
    }

    public Set<String> findAllTypes() {
        return specialtyRepository.findAllSpecialtyTypes();
    }

    public List<Specialty> findSpecialtyNamesByType(String type) {
        return specialtyRepository.findAvailableSpecialtiesByType(type);
    }

    public void delete(Integer id) {
        specialtyRepository.deleteById(id);
    }
}

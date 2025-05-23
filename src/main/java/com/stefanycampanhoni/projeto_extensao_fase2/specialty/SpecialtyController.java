package com.stefanycampanhoni.projeto_extensao_fase2.specialty;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/specialties")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @GetMapping(path = "/types")
    public ResponseEntity<Set<String>> getAllSpecialtyTypes() {
        return ResponseEntity.ok(specialtyService.findAllTypes());
    }

    @GetMapping
    public ResponseEntity<List<Specialty>> getSpecialtyNamesByType(@RequestParam(name = "type") String type) {
        return ResponseEntity.ok(specialtyService.findSpecialtyNamesByType(type));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Specialty> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(specialtyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Specialty> save(SpecialtyDto specialty) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.save(specialty));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

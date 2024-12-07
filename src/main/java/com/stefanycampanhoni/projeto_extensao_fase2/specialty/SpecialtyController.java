package com.stefanycampanhoni.projeto_extensao_fase2.specialty;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/specialty")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> getAll() {
        return ResponseEntity.ok(specialtyService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Specialty> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(specialtyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Specialty> save(Specialty specialty) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.save(specialty));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
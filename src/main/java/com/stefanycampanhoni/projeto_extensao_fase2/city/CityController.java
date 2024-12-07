package com.stefanycampanhoni.projeto_extensao_fase2.city;

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
@RequestMapping(path = "/city")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<City> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<City> save(CityDto city) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(city));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

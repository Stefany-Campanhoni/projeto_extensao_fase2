package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mentors")
public class MentorController {
    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<Mentor>> getAll() {
        return ResponseEntity.ok(mentorService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Mentor> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(mentorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Mentor> save(@RequestBody @Valid MentorDto mentor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.save(mentor));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        mentorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Mentor> update(@PathVariable(name = "id") Integer id,
                                         @RequestBody @Valid MentorDto mentor) {
        return ResponseEntity.ok(mentorService.update(id, mentor));
    }
}

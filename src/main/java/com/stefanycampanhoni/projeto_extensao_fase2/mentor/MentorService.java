package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;

    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    public Mentor findById(Integer id) {
        return mentorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mentor not found"));
    }

    public List<Mentor> findAll() {
        return mentorRepository.findAll();
    }

    public void delete(Integer id) {
        mentorRepository.deleteById(id);
    }
}

package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import com.stefanycampanhoni.projeto_extensao_fase2.city.CityService;
import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import com.stefanycampanhoni.projeto_extensao_fase2.specialty.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;
    private final CityService cityService;
    private final SpecialtyService specialtyService;

    public Mentor save(MentorDto mentorDto) {
        Mentor mentor = Mentor.builder()
                .name(mentorDto.name())
                .description(mentorDto.description())
                .email(mentorDto.email())
                .city(cityService.findById(mentorDto.cityId()))
                .specialty(specialtyService.findById(mentorDto.specialtyId()))
                .build();

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

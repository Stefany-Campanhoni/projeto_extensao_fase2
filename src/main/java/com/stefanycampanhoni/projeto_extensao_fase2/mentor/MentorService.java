package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import com.stefanycampanhoni.projeto_extensao_fase2.city.CityService;
import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import com.stefanycampanhoni.projeto_extensao_fase2.specialty.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        return mentorRepository.findAll()
                .stream()
                .peek(mentor -> {
                    mentor.setCity(cityService.findById(mentor.getCity().getId()));
                    mentor.setSpecialty(specialtyService.findById(mentor.getSpecialty().getId()));
                })
                .sorted(Comparator.comparing(Mentor::getId))
                .toList();
    }

    public void delete(Integer id) {
        mentorRepository.deleteById(id);
    }

    public Mentor update(Integer id, MentorDto mentorDto) {
        Mentor mentor = this.findById(id);
        mentor.setName(mentorDto.name());
        mentor.setEmail(mentorDto.email());
        mentor.setDescription(mentorDto.description());
        mentor.setCity(cityService.findById(mentorDto.cityId()));
        mentor.setSpecialty(specialtyService.findById(mentorDto.specialtyId()));

        return mentorRepository.save(mentor);
    }

    public List<Mentor> filterMentors(String name, String cityName, String specialtyType) {
        return mentorRepository.findMentorsByFilters(name, cityName, specialtyType)
                .stream()
                .peek(mentor -> {
                    mentor.setCity(cityService.findById(mentor.getCity().getId()));
                    mentor.setSpecialty(specialtyService.findById(mentor.getSpecialty().getId()));
                })
                .sorted(Comparator.comparing(Mentor::getId))
                .toList();
    }
}

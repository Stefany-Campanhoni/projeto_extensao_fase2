package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import com.stefanycampanhoni.projeto_extensao_fase2.city.CityService;
import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import com.stefanycampanhoni.projeto_extensao_fase2.jwt.TokenService;
import com.stefanycampanhoni.projeto_extensao_fase2.specialty.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;
    private final CityService cityService;
    private final SpecialtyService specialtyService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Mentor save(MentorDto mentorDto) {
        Role role = mentorDto.role() != null ? mentorDto.role() : Role.USER;

        Mentor mentor = Mentor.builder()
                .name(mentorDto.name())
                .description(mentorDto.description())
                .email(mentorDto.email())
                .password(passwordEncoder.encode(mentorDto.password()))
                .role(role)
                .city(cityService.findById(mentorDto.cityId()))
                .specialty(specialtyService.findById(mentorDto.specialtyId()))
                .build();

        return mentorRepository.save(mentor);
    }

    public MentorResponse login(Mentor usuario) {
        Optional<Mentor> usuarioResult = mentorRepository.findByEmail(usuario.getEmail());
        if (usuarioResult.isEmpty()){
            throw new RuntimeException("USUARIO NÃO ENCONTRADO");
        }
        Mentor usuarioBanco = usuarioResult.get();

        if (passwordEncoder.matches(usuario.getPassword(), usuarioBanco.getPassword())){
            MentorResponse usuario1 = new MentorResponse();
            usuario1.setId(usuarioBanco.getId());
            usuario1.setEmail(usuarioBanco.getEmail());
            usuario1.setToken(tokenService.gerarToken(usuarioBanco));

            return usuario1;
        }
        throw new RuntimeException("SENHA INVÁLIDA!");
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
        mentor.setPassword(mentorDto.password());
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

package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import com.stefanycampanhoni.projeto_extensao_fase2.city.CityService;
import com.stefanycampanhoni.projeto_extensao_fase2.exception.InvalidEmailException;
import com.stefanycampanhoni.projeto_extensao_fase2.exception.InvalidPasswordException;
import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import com.stefanycampanhoni.projeto_extensao_fase2.jwt.TokenService;
import com.stefanycampanhoni.projeto_extensao_fase2.specialty.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;
    private final CityService cityService;
    private final SpecialtyService specialtyService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Mentor save(MentorDto mentorDto) {
        Mentor mentor = Mentor.builder()
                .name(mentorDto.name())
                .description(mentorDto.description())
                .email(mentorDto.email())
                .password(passwordEncoder.encode(mentorDto.password()))
                .role(Role.USER)
                .city(cityService.findById(mentorDto.cityId()))
                .specialty(specialtyService.findById(mentorDto.specialtyId()))
                .build();

        return mentorRepository.save(mentor);
    }

    public Mentor login(LoginDto loginDto) {
        return mentorRepository
                .findByEmail(loginDto.email())
                .map(user -> {
                    if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
                        throw new InvalidPasswordException();
                    }
                    user.setToken(tokenService.generateToken(user));
                    return user;
                })
                .orElseThrow(InvalidEmailException::new);
    }

    public Mentor findById(Integer id) {
        return mentorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mentor not found"));
    }

    public List<Mentor> findAll() {
        return mentorRepository.findAll()
                .stream()
                .filter(mentor -> mentor.getRole() != Role.ADMIN)
                .peek(mentor -> {
                    mentor.setCity(cityService.findById(mentor.getCity().getId()));
                    mentor.setSpecialty(specialtyService.findById(mentor.getSpecialty().getId()));
                })
                .sorted(Comparator.comparing(Mentor::getId))
                .toList();
    }

    public void delete(Integer id) {
        this.checkPermissionToModifyMentor(id);
        mentorRepository.deleteById(id);
    }

    public Mentor update(Integer id, MentorDto mentorDto) {
        this.checkPermissionToModifyMentor(id);

        Mentor mentor = this.findById(id);
        mentor.setName(mentorDto.name());
        mentor.setEmail(mentorDto.email());
        mentor.setDescription(mentorDto.description());
        mentor.setPassword(mentorDto.password());
        mentor.setCity(cityService.findById(mentorDto.cityId()));
        mentor.setSpecialty(specialtyService.findById(mentorDto.specialtyId()));

        return mentorRepository.save(mentor);
    }

    private void checkPermissionToModifyMentor(Integer mentorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Mentor authenticatedMentor = (Mentor) authentication.getPrincipal();

        boolean isAdmin = authenticatedMentor.getRole() == Role.ADMIN;
        boolean isSelf = authenticatedMentor.getId().equals(Long.valueOf(mentorId));
        if (!isAdmin && !isSelf) {
            throw new NotFoundException("Você não tem permissão para modificar este registro.");
        }
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

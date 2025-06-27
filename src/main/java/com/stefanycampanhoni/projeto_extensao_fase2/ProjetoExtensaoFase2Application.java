package com.stefanycampanhoni.projeto_extensao_fase2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.Mentor;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.MentorRepository;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.Role;
import com.stefanycampanhoni.projeto_extensao_fase2.city.CityRepository;
import com.stefanycampanhoni.projeto_extensao_fase2.specialty.SpecialtyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjetoExtensaoFase2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoExtensaoFase2Application.class, args);
	}

	@Bean
	public CommandLineRunner createAdminMentor(MentorRepository mentorRepository,
											   CityRepository cityRepository,
											   SpecialtyRepository specialtyRepository,
											   PasswordEncoder passwordEncoder) {
		return args -> {
			String adminEmail = "admin@admin.com";
			if (mentorRepository.findByEmail(adminEmail).isEmpty()) {
				var city = cityRepository.findAll().stream()
					.filter(c -> c.getName().equalsIgnoreCase("Criciúma"))
					.findFirst().orElse(null);
				var specialty = specialtyRepository.findAll().stream()
					.filter(s -> s.getName().equalsIgnoreCase("Engenharia de Software"))
					.findFirst().orElse(null);
				if (city != null && specialty != null) {
					Mentor admin = Mentor.builder()
						.name("Admin")
						.description("Administrador do sistema")
						.email(adminEmail)
						.password(passwordEncoder.encode("admin123"))
						.role(Role.ADMIN)
						.city(city)
						.specialty(specialty)
						.build();
					mentorRepository.save(admin);
				}
			}
		};
	}
}

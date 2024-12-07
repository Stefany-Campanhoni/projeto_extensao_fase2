package com.stefanycampanhoni.projeto_extensao_fase2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjetoExtensaoFase2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoExtensaoFase2Application.class, args);
	}

	@Configuration
	@EnableWebMvc
	public static class WebSecurityConfig implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("*")
					.allowedHeaders("*");
		}
	}
}

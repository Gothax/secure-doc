package com.gothaxcity.securedoc_be;

import com.gothaxcity.securedoc_be.domain.RequestContext;
import com.gothaxcity.securedoc_be.entity.RoleEntity;
import com.gothaxcity.securedoc_be.enumeration.Authority;
import com.gothaxcity.securedoc_be.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
//			RequestContext.setUserId(0L);
//			var userRole = new RoleEntity();
//			userRole.setName(Authority.USER.name());
//			userRole.setAuthorities(Authority.USER);
//
//			var adminRole = new RoleEntity();
//			adminRole.setName(Authority.ADMIN.name());
//			adminRole.setAuthorities(Authority.ADMIN);
//
//
//			roleRepository.save(userRole);
//			roleRepository.save(adminRole);
//			RequestContext.start();
		};
	}

}

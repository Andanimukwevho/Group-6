package com.Future_Transitions.Future_Transitions;

import com.Future_Transitions.Future_Transitions.model.Province;
import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FutureTransitionsApplication {

	private final UserRepository userRepository;

	public FutureTransitionsApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FutureTransitionsApplication.class, args);
	}


	@PostConstruct
	public void initAdminUser() {
		boolean exists = userRepository.existsByEmail("admin@gmail.com");

		if (!exists) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setName("siyabonga");
			user.setSurname("shabangu");
			user.setPhoneNumber("7714774294");
			user.setAddress("230 jozi street");
			user.setAge(30);
			user.setProvince(Province.MPUMALANGA);
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));

			userRepository.save(user);
			System.out.println("our admin");
		}
	}
}
package net.gymsrote;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.Role;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.MediaResourceService;
import net.gymsrote.service.impl.UserService;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@SpringBootApplication
@EnableSwagger2
public class TdgymApplication {
	public static void main(String[] args) {
		SpringApplication.run(TdgymApplication.class, args);
	}
	// @Bean
	// PasswordEncoder passwordEncoder() {
	// return new BCryptPasswordEncoder();
	// }

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(EUserRole.ROLE_USER));
			userService.saveRole(new Role(EUserRole.ROLE_ADMIN));
			userService.saveUser(new User("ttt14.11.21@gmail.com", "ttt141121", "141121",
					"Thanh Trung Tran", "0338989824", new ArrayList<>()));
			userService.addRoleToUser("ttt141121", EUserRole.ROLE_ADMIN);
			userService.addRoleToUser("ttt141121", EUserRole.ROLE_USER);
			// File file = new File("test.png");
			// byte[] bytes = Files.readAllBytes(file.toPath());
			// CloudResource temp = cloudResourceService.save(bytes);
			// System.out.println(temp.getUrl());
		};
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("net.gymsrote")).build();
	}
	// @Bean
	// PasswordEncoder passwordEncoder() {
	// return new BCryptPasswordEncoder();
	// }
	// @Bean
	// CommandLineRunner run(CloudResourceService cloudResourceService) {
	// return args -> {
	//// userService.saveRole(new RoleEntity(EUserRole.ROLE_USER));
	//// userService.saveRole(new RoleEntity(EUserRole.ROLE_ADMIN));
	//// userService.saveUser(new UserEntity("ttt14.11.21@gmail.com", "ttt141121", "141121", "Thanh
	// Trung Tran", "0338989824", new ArrayList<>()));
	//// userService.addRoleToUser("ttt141121", EUserRole.ROLE_ADMIN);
	//// userService.addRoleToUser("ttt141121", EUserRole.ROLE_USER);
	// File file = new File("test.png");
	// byte[] bytes = Files.readAllBytes(file.toPath());
	// CloudResource temp = cloudResourceService.save(bytes);
	// System.out.println(temp.getUrl());
	// };
	// }
}

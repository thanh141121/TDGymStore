package net.gymsrote;

import java.util.ArrayList;

import javax.management.relation.Role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.service.UserService;

@SpringBootApplication
public class TdgymApplication {
	public static void main(String[] args) {
		SpringApplication.run(TdgymApplication.class, args);
	}
	//	@Bean
	//	PasswordEncoder passwordEncoder() {
	//		return new BCryptPasswordEncoder();
	//	}
	// 	@Bean
	// 	CommandLineRunner run(UserService userService) {
	// 		return args -> {
	// 			userService.saveRole(new UserRole(EUserRole.ROLE_USER));
	// 			userService.saveRole(new UserRole(EUserRole.ROLE_ADMIN));
	// 			userService.saveUser(new User("ttt14.11.21@gmail.com", "string", "stringst", "Thanh Trung Tran", "0338989824", new ArrayList<>()));
	// 			userService.addRoleToUser("ttt141121", EUserRole.ROLE_ADMIN);
	// 			userService.addRoleToUser("ttt141121", EUserRole.ROLE_USER);
	// //			File file = new File("test.png");
	// //			byte[] bytes = Files.readAllBytes(file.toPath());
	// //			CloudResource temp = cloudResourceService.save(bytes);
	// //			System.out.println(temp.getUrl());
	// 		};
	// 	}
}

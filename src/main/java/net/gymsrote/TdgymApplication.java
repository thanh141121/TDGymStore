package net.gymsrote;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
	 	@Bean
	 	CommandLineRunner run(UserService userService) {
	 		return args -> {
	 			// userService.saveRole(new UserRole(EUserRole.USER));
	 			// userService.saveRole(new UserRole(EUserRole.ADMIN));
	 			// userService.saveUser(new User("duy123@gmail.com", "duy123", "duy123", "Pham Phong Duy", "113114115"));
	 			// userService.saveUser(new User("thanh123@gmail.com", "thanh123", "thanh123", "Thanh Trung Tran", "0338989824"));
	 			// userService.addRoleToUser("thanh123", EUserRole.ADMIN);
	 			// userService.addRoleToUser("duy123", EUserRole.USER);
	 //			File file = new File("test.png");
	 //			byte[] bytes = Files.readAllBytes(file.toPath());
	 //			CloudResource temp = cloudResourceService.save(bytes);
	 //			System.out.println(temp.getUrl());
	 		};
	 	}
}

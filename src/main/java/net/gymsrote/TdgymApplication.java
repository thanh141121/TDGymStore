package net.gymsrote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TdgymApplication {
	public static void main(String[] args) {
		SpringApplication.run(TdgymApplication.class, args);
	}
	//	@Bean
	//	PasswordEncoder passwordEncoder() {
	//		return new BCryptPasswordEncoder();
	//	}
	//	@Bean
	//	CommandLineRunner run(UserService userService) {
	//		return args -> {
	//			userService.saveRole(new Role(EUserRole.ROLE_USER));
	//			userService.saveRole(new Role(EUserRole.ROLE_ADMIN));
	//			userService.saveUser(new User("ttt14.11.21@gmail.com", "ttt141121", "141121", "Thanh Trung Tran", "0338989824", new ArrayList<>()));
	//			userService.addRoleToUser("ttt141121", EUserRole.ROLE_ADMIN);
	//			userService.addRoleToUser("ttt141121", EUserRole.ROLE_USER);
	////			File file = new File("test.png");
	////			byte[] bytes = Files.readAllBytes(file.toPath());
	////			CloudResource temp = cloudResourceService.save(bytes);
	////			System.out.println(temp.getUrl());
	//		};
	//	}
}

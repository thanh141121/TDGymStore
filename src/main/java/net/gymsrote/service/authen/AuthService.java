package net.gymsrote.service.authen;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.utility.RandomString;
import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.advice.exception.UnknownException;
import net.gymsrote.controller.payload.request.SignUpRequest;
import net.gymsrote.controller.payload.response.BaseResponse;
import net.gymsrote.controller.payload.response.LoginResponse;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.RoleRepository;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.UserService;
import net.gymsrote.utility.component.JwtUtil;
@Service
public class AuthService {
	private 
	// @Autowired
	// LogService logService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SendMailService mailService;

	@Autowired
	UserService userService;
    
	@Autowired
	UserRepo userRepo;
	@Autowired
	private RoleRepository roleRepo;
	
//	@Autowired
//	BuyerRepo buyerRepo;
	
	public LoginResponse<?> authenticateWithUsernamePassword(String username, String password) {
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(auth);
		@SuppressWarnings("rawtypes")
		UserDetailsImpl userDetails = (UserDetailsImpl)auth.getPrincipal();
		String token = generateToken(username);
		User u = (User) userDetails.getUser();
		return new LoginResponse<>(
				token, 
				mapper.map((User)userDetails.getUser(), UserDTO.class)
		);
	}
	
	public String generateToken(String username) {
		return jwtUtil.generateJwtToken(username);
	}
	
	@Transactional(rollbackFor = { UnknownException.class, Exception.class })
	public BaseResponse register(SignUpRequest user, String siteURL){ //throws UnsupportedEncodingException, MessagingException
		try {
			User newUser = new User(user.getEmail(), 
					user.getUsername(), 
					passwordEncoder.encode(user.getPassword()),
					user.getFullname(), 
					user.getPhone());
			newUser = userRepo.save(newUser);
			newUser.setRole( roleRepo.findByName(EUserRole.USER));
		    String randomCode = RandomString.make(64);
			newUser.setVerificationCode(randomCode);
			mailService.sendVerificationEmail(newUser, siteURL);
			return new BaseResponse(true, "Register succesfully!");
		}catch (Exception e) {
			throw new UnknownException("Something is wrong with your infomation: "+e.getMessage());
		}
	}
	
	@Transactional
	public boolean verify(String verificationCode) {
		User user = userRepo.findByVerificationCode(verificationCode);
		
		if (user == null) {
			return false;
		} else if(user.getIsEnabled()) {
			throw new UnknownException("Your account is already enabled!");
		}
		else {
			user.setVerificationCode(null);
			user.setIsEnabled(true);
			user.setVerificationCode(null);
			// userRepo.save(user);
			return true;
		}
		
	}
	

}

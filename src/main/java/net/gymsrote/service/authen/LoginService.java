package net.gymsrote.service.authen;
import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.response.LoginResponse;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.LogService;
import net.gymsrote.utility.component.JwtUtil;
@Service
public class LoginService {
	@Autowired
	LogService logService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserRepo userRepo;
	
//	@Autowired
//	BuyerRepo buyerRepo;
	
	public LoginResponse<?> authenticateWithUsernamePassword(String username, String password, EUserRole requestedRole) {
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
}

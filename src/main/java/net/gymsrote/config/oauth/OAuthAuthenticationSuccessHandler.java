package net.gymsrote.config.oauth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.URLEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.UserService;
import net.gymsrote.service.authen.AuthService;

@Component
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final UserService userService;
	
	private final AuthService authService;
	
	@Autowired
	ModelMapper mapper;
	


    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	
	@Value("${net.gymstore.security.oauth2.fe-login-url}")
	String redirectUrl;
	
	public OAuthAuthenticationSuccessHandler(@Lazy UserService userService, @Lazy AuthService authService, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.userService = userService;
		this.authService = authService;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		CustomOAuthUser user = (CustomOAuthUser)authentication.getPrincipal();
		String email = user.getEmail();
		User userReturned;
		
		if (!userService.existsByEmail(email))
			userReturned = userService.createOAuth2User(email, user.getName());
		else
			userReturned = userService.getUsernameByEmail(email);
		
		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(
				request, 
				response, 
		        redirectUrl + "?token=" + authService.generateToken(userReturned.getUsername()) +
                  "&user=" + java.net.URLEncoder.encode(new Gson().toJson(mapper.map(userReturned, UserDTO.class)), StandardCharsets.UTF_8.name()));
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	


    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}

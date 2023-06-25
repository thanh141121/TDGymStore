package net.gymsrote.config.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import net.gymsrote.service.UserService;
import net.gymsrote.service.authen.AuthService;

@Component
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final UserService userService;
	
	private final AuthService authService;
	


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
		String username;
		
		if (!userService.existsByEmail(email))
			username = userService.createOAuth2User(email, user.getName());
		else
			username = userService.getUsernameByEmail(email);
		
		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(
				request, 
				response, 
				redirectUrl + "?token=" + authService.generateToken(username));
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	


    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}

package net.gymsrote.config.login;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserInfo;
@Getter
public class UserDetailsImpl<T extends UserInfo> implements UserDetails{
	private T user;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(T userInfo) {
		super();
		this.user = userInfo;
		this.authorities = getAuthorities();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		((User) user).getRoles().forEach(uRole -> {
//			authorities.add(new SimpleGrantedAuthority(uRole.getName().name()));
//		});
		authorities.add(new SimpleGrantedAuthority("ROLE_" +user.getRole().getName().name()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

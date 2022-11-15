package net.gymsrote.dto;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EUserStatus;

@Getter @Setter
public class UserDTO {
	private Long id;
	private String email;
	private String username;
	private String password;
	private String fullname;
	private String phone;
	private EUserStatus status;
	private Collection<UserRoleDTO> userRoles;

}

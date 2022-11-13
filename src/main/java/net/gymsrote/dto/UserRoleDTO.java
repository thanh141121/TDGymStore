package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.EnumEntity.EUserStatus;

@Getter @Setter
public class UserRoleDTO {
	private Long id;
	private EUserRole name;

}

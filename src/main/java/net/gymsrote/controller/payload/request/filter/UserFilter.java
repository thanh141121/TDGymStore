package net.gymsrote.entity.EnumEntity.filter;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.EnumEntity.EUserStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFilter {
	String searchFullname;

	String searchUsername;

	String searchEmail;

	String searchPhone;

	EUserRole role;

	EUserStatus status;

	public UserFilter(String searchFullname, String searchUsername, String searchEmail,
			String searchPhone, EUserRole role, EUserStatus status) {
		this.searchFullname = searchFullname;
		this.searchUsername = searchUsername;
		this.searchEmail = searchEmail;
		this.searchPhone = searchPhone;
		this.role = role;
		this.status = status;
	}
}

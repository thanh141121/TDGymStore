package net.gymsrote.entity.user;

import net.gymsrote.entity.EnumEntity.EUserStatus;

public interface UserInfo {
	String getUsername();
	String getPassword();
	UserRole getRole();
	EUserStatus getStatus();
}

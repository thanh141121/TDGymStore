package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressDTO {
	private Long id;
	private String addressDetail;
	private String receiveName;
	private String receivePhone;
	private Long userId;
	
}

package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.dto.address.WardDTO;

@Getter @Setter
public class UserAddressDTO {
	private Long id;
	private String addressDetail;
	private String receiverName;
	private String receiverPhone;
	private WardDTO ward;

}

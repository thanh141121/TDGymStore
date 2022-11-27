package net.gymsrote.dto.address;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DistrictDTO {
	private Long DistrictID;
	private String DistrictName;
	private ProvinceDTO province;
}

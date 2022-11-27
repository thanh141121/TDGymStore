package net.gymsrote.dto.address;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WardDTO {
	private Long WardCode;
	private String WardName;
	private DistrictDTO district;

}

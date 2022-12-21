package net.gymsrote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProductCartDetailDTO {
	private Long id;
	
	private String name;
	private String description;

	private Long minPrice;
	private Long maxPrice;

}

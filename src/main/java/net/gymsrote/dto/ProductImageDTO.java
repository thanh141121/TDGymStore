package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductImageDTO {
	private Long id;
	private Long productId;
	private String url;
	private String resourceType;

}

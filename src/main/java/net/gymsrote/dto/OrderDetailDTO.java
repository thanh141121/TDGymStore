package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class OrderDetailDTO {
	private Long orderId;
	private ProductVariationDTO productVariation;
	private Long quantity;
	private Long unitPrice;
}

package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDTO {
	private Long cartId;
	private Long productId;
	private Long quantity;
	
}

package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class OrderDetailDTO {
	private Long ordersId;
	private Long productId;
	private Long quantity;

}

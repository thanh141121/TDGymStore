package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDTO {
	private Long userId;
	private ProductVariationDTO productVariation;
	private Long quantity;

    
    private ProductCartDetailDTO productDetail;
	
}

package net.gymsrote.controller.payload.request.product;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateVariationReq {
	@NotNull
	private String variationName;
	
	@NotNull
	private Long price;
	
	@NotNull
	@Range(min = 0)
	private Long availableQuantity;
	
	@Range(min = 0, max = 100)
	private Integer discount;

}

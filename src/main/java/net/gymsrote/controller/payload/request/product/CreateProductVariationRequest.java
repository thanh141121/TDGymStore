package net.gymsrote.controller.payload.request.product;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CreateProductVariationRequest {
	@NotBlank
	private String variationName;
	
	@NotBlank
	private String tier;
	
	@NotNull
	@Range(min = 1)
	private Long price;
	
	@NotNull
	@Range(min = 0)
	private Long availableQuantity;
	
	@Range(min = 0, max = 100)
	private Integer discount;
}
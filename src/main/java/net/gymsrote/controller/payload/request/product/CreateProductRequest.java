package net.gymsrote.controller.payload.request.product;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductRequest {
	@NotBlank
	private String name;
	
	@NotNull
	private List<CreateProductVariationRequest> variations;
	
	@NotBlank
	private String description;
	
	@NotNull
	private Long idCategory;
}

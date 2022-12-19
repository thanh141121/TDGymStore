package net.gymsrote.controller.payload.request.product;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductVariationStatus;

@Getter @Setter
public class UpdateProductVariationRequest {	
	
	private String variationName;
	
	@Range(min = 1)
	private Long price;
	
	@Range(min = 0)
	private Long availableQuantity;
	
	@Range(min = 0, max = 100, message = "Allowed discount value is from 0 to 100")
	private Integer discount;
	
	private EProductVariationStatus status;
	
	private MultipartFile image;
}
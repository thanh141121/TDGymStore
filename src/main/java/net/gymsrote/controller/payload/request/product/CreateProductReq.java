package net.gymsrote.controller.payload.request.product;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductReq {
	@NotNull
	private String name;
	
	private Long min_price;
	private Long max_price;
	
	@NotBlank
	private String description;
	@NotNull
	private Long categoryId;
	
	@NotNull
	private List<CreateVariationReq> variations;

}

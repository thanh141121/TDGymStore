package net.gymsrote.controller.payload.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductStatus;

@Getter @Setter
@NoArgsConstructor
public class UpdateProductRequest {

	private String name;
	
	private String description;

	private Long idCategory;
	
	private EProductStatus status;
}

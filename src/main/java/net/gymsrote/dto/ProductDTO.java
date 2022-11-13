package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductStatus;

@Getter @Setter
public class ProductDTO extends BaseDTO{
	private Long id;
	private String description;
	private String name;
	private Long price;
	private Long quantity;
	private EProductStatus status;
	private Long categoryId;
	private Long cloudResourceId;
	

}

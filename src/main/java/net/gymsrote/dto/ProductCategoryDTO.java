package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;

@Getter @Setter
public class ProductCategoryDTO {
	private Long id;
	private String name;
	private EProductCategoryStatus status;

}

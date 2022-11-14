package net.gymsrote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductStatus;

@Getter @Setter
@NoArgsConstructor
public class ProductGeneralDetailDTO {
	private Long id;
	private ProductCategoryGeneralDTO category;
	
	private String name;
	private String avatar;
	private String description;

	private Long minPrice;
	private Long maxPrice;
	private Integer maxDiscount;
	private Long nsold;
	private Long nvisit;
	private EProductStatus status;
	
	private Double averageRating;
}

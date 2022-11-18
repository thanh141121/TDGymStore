package net.gymsrote.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductStatus;

@Getter @Setter
@NoArgsConstructor
public class ProductDetailDTO {
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
	private Integer rating1;
	private Integer rating2;
	private Integer rating3;
	private Integer rating4;
	private Integer rating5;
	private Integer totalRatingTimes;
	private List<ProductImageDTO> images;
	private List<ProductVariationDTO> variations;
}
package net.gymsrote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EProductVariationStatus;

@Getter @Setter
@NoArgsConstructor
public class ProductVariationDTO {
	private Long id;
	private Long idProduct;
	private String variationName;
	private Long price;
	private Long availableQuantity;
	private Integer discount;
	private MediaResourceDTO avatar;
	private EProductVariationStatus status;

	private ProductGeneralDetailDTO product;
}

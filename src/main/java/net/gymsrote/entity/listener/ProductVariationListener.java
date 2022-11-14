package net.gymsrote.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.stereotype.Component;

import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.service.ProductService;
import org.springframework.context.annotation.Lazy;

@Component
public class ProductVariationListener {
	private ProductService productService;

	public ProductVariationListener(@Lazy ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@PrePersist
	public void updateProductPriceWhenPersist(ProductVariation pv) {
		productService.updatePriceWhenVariationCreated(pv);
	}
	
    @PreUpdate
    @PreRemove
	public void updateProductPriceWhenUpdate(ProductVariation pv) {
		productService.updatePriceWhenVariationChange(pv);
	}
}

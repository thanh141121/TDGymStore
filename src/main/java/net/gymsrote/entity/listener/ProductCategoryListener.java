package net.gymsrote.entity.listener;

import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.stereotype.Component;

import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.product.ProductCategory;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.service.ProductService;
import org.springframework.context.annotation.Lazy;

@Component
public class ProductCategoryListener {
	private ProductService productService;

	public ProductCategoryListener(@Lazy ProductService productService) {
		super();
		this.productService = productService;
	}
	
    @PostUpdate
	public void updateProductStatus(ProductCategory c) {
    	if(c.getStatus().equals(EProductCategoryStatus.ENABLED))
    		productService.updateProductStatus(c.getId(), EProductStatus.ENABLED);
    	else
    		productService.updateProductStatus(c.getId(), EProductStatus.DISABLED);
	}
}

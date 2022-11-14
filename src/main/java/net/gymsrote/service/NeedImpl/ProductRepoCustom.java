package net.gymsrote.service.NeedImpl;

import org.springframework.data.domain.Page;

import net.gymsrote.controller.payload.request.filter.ProductFilter;
import net.gymsrote.entity.product.Product;
import net.gymsrote.utility.PagingInfo;

public interface ProductRepoCustom {
	
	Page<Product> search(ProductFilter filter, PagingInfo page);
	
}
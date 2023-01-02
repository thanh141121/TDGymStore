package net.gymsrote.service;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.controller.advice.exception.CommonRestException;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.product.CreateVariationReq;
import net.gymsrote.controller.payload.request.product.UpdateProductVariationRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.dto.ProductVariationDTO;
import net.gymsrote.entity.EnumEntity.EFolderMediaResource;
import net.gymsrote.entity.EnumEntity.EProductVariationStatus;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.repository.ProductRepo;
//import net.gymsrote.controller.payload.dto.ProductVariationDTO;
//import net.gymsrote.controller.payload.request.product.CreateProductVariationRequest;
//import net.gymsrote.controller.payload.request.product.UpdateProductVariationRequest;
//import net.gymsrote.controller.payload.response.DataResponse;
//import net.gymsrote.entity.product.Product;
//import net.gymsrote.entity.product.ProductVariation;
//import net.gymsrote.repository.ProductRepo;
//import net.gymsrote.repository.ProductVariationRepo;
//import net.gymsrote.service.util.ServiceUtils;
//import net.gymsrote.entity.EnumEntity.EProductVariationStatus;
//import net.gymsrote.entity.EnumEntity.PlatformPolicyParameter;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.PlatformPolicyParameter;

@Service
public class ProductVariationService {
	
	@Autowired
	MediaResourceService mediaResourceService;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	ProductVariationRepo productVariationRepo;

	@Autowired
	ServiceUtils serviceUtils;

	public DataResponse<ProductVariationDTO> getById(Long id) {
		ProductVariation variation = productVariationRepo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("No product category found with given id"));
		return serviceUtils.convertToDataResponse(variation, ProductVariationDTO.class);
	}

	public ProductVariation create(Long idProduct, CreateVariationReq var) throws IOException {
		Optional<Product> p = productRepo.findById(idProduct);
		if(p.isPresent()) {
			ProductVariation ProductVariation = productVariationRepo.saveAndFlush(new ProductVariation(p.get(), var.getVariationName(), var.getPrice(),
					var.getAvailableQuantity(), var.getDiscount(),mediaResourceService.save(var.getImage().getBytes(), EFolderMediaResource.ProductVariation), EProductVariationStatus.ENABLED));
			updatePriceProduct(p.get(), ProductVariation);
			return ProductVariation;
		}
		if (!productRepo.existsById(idProduct))
			throw new InvalidInputDataException("No product found with given id");
		return null;
	}
	
	@Transactional
	public static void updatePriceProduct(Product p, ProductVariation var) {
		if(var.getStatus().equals(EProductVariationStatus.ENABLED)) {
			Long minPricePro = p.getMinPrice();
			Long maxPricePro = p.getMaxPrice();
			long price = var.getFinalPrice().longValue();
			if(minPricePro == null) {
				p.setMinPrice(price);
			}else {
				if(minPricePro.longValue() > price)
					p.setMinPrice(price);
			}
			if(p.getMaxPrice() == null) {
				p.setMaxPrice(price);
			}else {
				if(maxPricePro.longValue() < price)
					p.setMaxPrice(price);
			}
			if(var.getDiscount() != null){
				int maxDis = var.getDiscount();
				Integer pMaxDis = p.getMaxDiscount();
				if(pMaxDis != null ){
					if(pMaxDis < maxDis)
						p.setMaxDiscount(maxDis);
				}
				else
					p.setMaxDiscount(maxDis);
			}
		}
	}

	/*public void create(Product p, List<CreateProductVariationRequest> data) {
		for (CreateProductVariationRequest variation : data) {
			productVariationRepo.save(new ProductVariation(p, variation.getVariationName(),
					variation.getPrice(), variation.getAvailableQuantity(),
					variation.getDiscount(), EProductVariationStatus.ENABLED));
		}
	}*/

	@Transactional
	public DataResponse<ProductVariationDTO> update(Long id, Long idProduct,
			UpdateProductVariationRequest data) {
		ProductVariation variation = productVariationRepo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("No product variation found with given id"));
		if (!variation.getProduct().getId().equals(idProduct)) {
			throw new InvalidInputDataException("Wrong product variation with given product");
		}
		if (StringUtils.isNotEmpty(data.getVariationName())
				&& !variation.getVariationName().equals(data.getVariationName())) {
			variation.setVariationName(data.getVariationName());
		}
		if (data.getPrice() != null && !variation.getPrice().equals(data.getPrice())) {
			variation.setPrice(data.getPrice());
		}
		if (data.getAvailableQuantity() != null
				&& !variation.getAvailableQuantity().equals(data.getAvailableQuantity())) {
			variation.setAvailableQuantity(data.getAvailableQuantity());
		}
		if (data.getDiscount() != null && !variation.getDiscount().equals(data.getDiscount())) {
			variation.setDiscount(data.getDiscount());
		}
		if (data.getStatus() != null && !variation.getStatus().equals(data.getStatus())) {
			variation.setStatus(data.getStatus());
			if (data.getStatus() == EProductVariationStatus.DISABLED) 
			{ 
				if(variation.getProduct().getVariations()
							.size() <= PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION)
					throw new CommonRestException(String.format(
							"A product must have at least %s enabled product(s) variation",
							PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION));
				Long min = variation.getProduct().getMinPrice();
				Long max = variation.getProduct().getMaxPrice();
				Product p = variation.getProduct();
				if(variation.getDiscount().equals(p.getMaxDiscount()))
					p.setMaxDiscount(null);
				if(variation.getFinalPrice().equals(min)) {
					p.setMinPrice(null);
					p.getVariations().stream().forEach(vari -> {
						updatePriceProduct(p, vari);
					});
				}
				if(variation.getFinalPrice().equals(max)) {
					p.setMaxPrice(null);
					p.getVariations().stream().forEach(vari -> {
						updatePriceProduct(p, vari);
					});
				}
			}
		}		
		if (data.getImage() != null) {
			serviceUtils.updateAvatar(variation, data.getImage(), EFolderMediaResource.ProductVariation);
		}
		return serviceUtils.convertToDataResponse(productVariationRepo.save(variation),
				ProductVariationDTO.class);
	}
}

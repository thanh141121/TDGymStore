package net.gymsrote.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.gymsrote.controller.advice.exception.CommonRuntimeException;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.ProductImageDTO;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EFolderMediaResource;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductImage;
import net.gymsrote.repository.ProductImageRepo;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.PlatformPolicyParameter;

@Service
public class ProductImageService {
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ProductImageRepo productImageRepo;
	
	@Autowired
	MediaResourceService mediaResourceService;
	
	@Autowired
	ServiceUtils serviceUtils;
	@Transactional
	public ListResponse<ProductImageDTO> create(Long idProduct, List<MultipartFile> images) {
		return create(productRepo.getReferenceById(idProduct), images);
	}
	
	@Transactional
	public ListResponse<ProductImageDTO> create(Product p, List<MultipartFile> images) {
		List<MediaResource> mrs = new ArrayList<>();
		List<ProductImage> pimgs = new ArrayList<>();
		
		try {
			for (MultipartFile image : images) {
				MediaResource mr = mediaResourceService.save(image.getBytes(), EFolderMediaResource.ProductImage);
				mrs.add(mr);
				pimgs.add(
						productImageRepo.save(
							new ProductImage(
									p, 
									mr
								)
						)
					);
			}
		} catch (IOException e) {
			for (MediaResource mr : mrs) {
				mediaResourceService.delete(mr);
			}
			throw new CommonRuntimeException("Error occurred when trying to save product image");
		}
		
		return serviceUtils.convertToListResponse(pimgs, ProductImageDTO.class);
	}
	
	public void delete(Long idProduct, List<Long> idProductImages) {
		if (idProductImages.isEmpty()) {
			throw new CommonRuntimeException("At least one image is specified to be deleted");
		}
		
		List<ProductImage> l = new ArrayList<>();
		for (Long idProductImage : idProductImages) {
			ProductImage img = productImageRepo.getReferenceById(idProductImage);
			Product p = img.getProduct();
			
			if (!p.getId().equals(idProduct)) {
				throw new CommonRuntimeException("Product doesn't have any image with id " + idProductImage.toString());
			}
			
			l.add(img);
		}
		deleteWithEntity(l);
	}
	
	@Transactional
	private void deleteWithEntity(List<ProductImage> productImages) {
		if (productImages.get(0).getProduct().getImages().size() - productImages.size() < PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_IMAGE) {
			throw new InvalidInputDataException(String.format("Product should have at least %d image(s)", PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_IMAGE));
		}
		
		for (ProductImage productImage : productImages) {
			MediaResource media = productImage.getMedia();
			productImage.setMedia(null);
			mediaResourceService.delete(media.getId());
			productImageRepo.delete(productImage);
		}
	}
}

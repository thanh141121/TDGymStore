package net.gymsrote.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.product.CreateProductReq;
import net.gymsrote.controller.payload.request.product.CreateVariationReq;
import net.gymsrote.controller.payload.request.product.UpdateProductRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.ProductDetailDTO;
import net.gymsrote.dto.ProductGeneralDetailDTO;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EFolderMediaResource;
import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductCategory;
import net.gymsrote.entity.product.ProductImage;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.ProductCategoryRepo;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.service.thirdparty.CloudinaryService;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.PlatformPolicyParameter;


@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;

	@Autowired
	ProductImageService productImageService;

	@Autowired
	MediaResourceService mediaResourceService;
	
	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	ProductVariationService productVariationService;

	@Autowired
	ProductCategoryRepo productCategoryRepo;

	@Autowired
	ProductVariationRepo productVariationRepo;

	@Autowired
	ServiceUtils serviceUtils;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateProductStatus(Long idCategory, EProductStatus status) {
		productRepo.updateStatusProduct(idCategory, status);
	}

//	public ListWithPagingResponse<ProductGeneralDetailDTO> search(ProductFilter filter,
//			PagingInfo pagingInfo) {
//		return serviceUtils.convertToListResponse(productRepo.search(filter, pagingInfo),
//				ProductGeneralDetailDTO.class);
//	}

	public ListResponse<ProductGeneralDetailDTO> getTopSaleProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByMaxDiscountDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopLastedProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByCreatedDateDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopVisitProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByNvisitDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopSoldProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByNsoldDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	@SuppressWarnings("null")
	@Transactional
	public DataResponse<ProductDetailDTO> getById(Long id, boolean isBuyer) {
		Product p = productRepo.findById(id).orElseThrow(() -> new InvalidInputDataException("No product found with given id"));
		
		if (isBuyer && 
				(p.getStatus() == EProductStatus.DISABLED ))
			throw new InvalidInputDataException("No product found with given id");
		else {
			if (isBuyer)
				productRepo.updateVisitCount(id);
		}
		
		return serviceUtils.convertToDataResponse(
				p,
				ProductDetailDTO.class
			);
	}

	@SuppressWarnings("null")
	@Transactional(rollbackFor = { InvalidInputDataException.class })
	public DataResponse<ProductDetailDTO> create(CreateProductReq product,
			MultipartFile avatar, List<MultipartFile> images) throws IOException {
		//a list to save PublicId to handle before rollback
		List<String> tempCloudianaryImage = new ArrayList<>();
		
		try{
			ProductCategory productCategory = productCategoryRepo.findById(
					product.getCategoryId()).orElseThrow(() -> new InvalidInputDataException("Category not found"));
			
			if (product.getVariations().size() < PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION) {
				throw new InvalidInputDataException(
						String.format("At least %d product variation(s) is required",
								PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION));
			}
			
			Product p = new Product(productCategory,
						product.getName(), 
						product.getDescription(),
						mediaResourceService.save(avatar.getBytes(), EFolderMediaResource.ThumnailProduct), 
						EProductStatus.ENABLED 
						);
			tempCloudianaryImage.add(p.getAvatar().getPublicId());
			
			p = productRepo.saveAndFlush(p);
			List<ProductImage> productImage= p.getImages();
			List<ProductVariation> productVariation = p.getVariations();
			
			//create ProductImage
			if(images != null){
				for(MultipartFile image: images) {
					MediaResource media = mediaResourceService.save(image.getBytes(), EFolderMediaResource.ProductImage);
					if(media != null)
					productImage.add(new ProductImage(p,media));
					tempCloudianaryImage.add(media.getPublicId());
				}
			}
			
			//create ProductVariation
			for(CreateVariationReq var : product.getVariations()) {
				ProductVariation proVar = productVariationService.create(p.getId(), var);
				productVariation.add(proVar);
				tempCloudianaryImage.add(proVar.getAvatar().getPublicId());
			}
			return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
			
		}catch(Exception e){
			//handle cloudinary before rollback
			if(tempCloudianaryImage.size() > 0) {
				for(String publicId : tempCloudianaryImage) {
					cloudinaryService.delete(publicId);
				}
			}
			throw new InvalidInputDataException(e.getMessage());
		}
	}
	
	@Transactional
	public DataResponse<ProductDetailDTO> update(Long id, UpdateProductRequest data,
			MultipartFile avatar) {
		Product p = productRepo.findById(id)
				.orElseThrow(() -> new InvalidInputDataException("No product found with given id"));
		if (data != null) {
			if (StringUtils.isNotEmpty(data.getName().trim()) && !p.getName().equals(data.getName().trim()))
				p.setName(data.getName());
			if (StringUtils.isNotEmpty(data.getDescription().trim())
					&& !p.getDescription().equals(data.getDescription().trim()))
				p.setDescription(data.getDescription());
			if (data.getIdCategory() != null
					&& !p.getCategory().getId().equals(data.getIdCategory())) {
			if (!productCategoryRepo.existsById(data.getIdCategory()))
					throw new InvalidInputDataException("No category found with given id");
				p.setCategory(productCategoryRepo.getReferenceById(data.getIdCategory()));
			}
			if (data.getStatus() != null && !p.getStatus().equals(data.getStatus()))
				p.setStatus(data.getStatus());
		}
		if (avatar != null) {
			serviceUtils.updateAvatar(p, avatar, EFolderMediaResource.ThumnailProduct);
		}
		p = productRepo.save(p);
		return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
	}

	public ListWithPagingResponse<ProductDetailDTO> getAllProducts(Pageable pageable) {
		Page<Product> products = productRepo.findAll(pageable);
		return serviceUtils.convertToListResponse(products, ProductDetailDTO.class);
	}
	
	public ListWithPagingResponse<ProductGeneralDetailDTO> search(String keyword, Pageable pageable) {
		return serviceUtils.convertToListResponse(
				productRepo.search(keyword, pageable),
				ProductGeneralDetailDTO.class);
	}

	/*public void updateRating(Long id, Integer point) {
		switch (point) {
			case 1:
				productRepo.updateRating1(id);
				break;
			case 2:
				productRepo.updateRating2(id);
				break;
			case 3:
				productRepo.updateRating3(id);
				break;
			case 4:
				productRepo.updateRating4(id);
				break;
			case 5:
				productRepo.updateRating5(id);
				break;
		}
		productRepo.updateAverageRating(id, point);
	}*/
//
//	/***
//	 * For entity listener
//	 * 
//	 * @param pv
//	 */
	/*public void updatePriceWhenVariationCreated(ProductVariation pv) {
		Product p = pv.getProduct();
		List<ProductVariation> pvs = new ArrayList<>(p.getVariations());
		pvs.add(pv);
		updatePrice(p, pvs);
	}*/
//
//	/***
//	 * For entity listener
//	 * 
//	 * @param pv
//	 */
	/*public void updatePriceWhenVariationChange(ProductVariation pv) {
		Product p = pv.getProduct();
		List<ProductVariation> pvs = new ArrayList<>(p.getVariations());
		updatePrice(p, pvs);
	}

	private void updatePrice(Product p, List<ProductVariation> pvs) {
		Supplier<Stream<ProductVariation>> streamSupplier =
				() -> pvs.stream().filter(x -> x.getAvailableQuantity() > 0
						&& x.getStatus() == EProductVariationStatus.ENABLED);
		Optional<ProductVariation> maxDiscount = streamSupplier.get()
				.max((first, second) -> Long.compare(first.getDiscount(), second.getDiscount()));
		Optional<ProductVariation> minPv = streamSupplier.get().min((first, second) -> Long
				.compare(first.getPriceAfterDiscount(), second.getPriceAfterDiscount()));
		Optional<ProductVariation> maxPv = streamSupplier.get().max((first, second) -> Long
				.compare(first.getPriceAfterDiscount(), second.getPriceAfterDiscount()));
		if (maxDiscount.isPresent())
			p.setMaxDiscount(maxDiscount.get().getDiscount());
		if (minPv.isPresent())
			p.setMinPrice(minPv.get().getPriceAfterDiscount());
		if (maxPv.isPresent())
			p.setMaxPrice(maxPv.get().getPriceAfterDiscount());
		productRepo.save(p);
	}*/

}

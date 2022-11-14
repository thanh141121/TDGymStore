package net.gymsrote.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//import net.gymsrote.controller.TestRequest;
//import net.gymsrote.controller.TierVariation;
//import net.gymsrote.controller.advice.exception.CommonRuntimeException;
//import net.gymsrote.controller.advice.exception.InvalidInputDataException;
//import net.gymsrote.controller.payload.dto.ProductGeneralDetailDTO;
//import net.gymsrote.controller.payload.dto.productdetail.ProductDetailDTO;
//import net.gymsrote.controller.payload.request.product.CreateProductRequest;
//import net.gymsrote.controller.payload.request.product.UpdateProductRequest;
//import net.gymsrote.controller.payload.response.DataResponse;
//import net.gymsrote.controller.payload.response.ListResponse;
//import net.gymsrote.controller.payload.response.ListWithPagingResponse;
//import net.gymsrote.entity.product.Product;
//import net.gymsrote.entity.product.ProductCategory;
//import net.gymsrote.entity.product.ProductVariation;
//import net.gymsrote.repository.ProductCategoryRepo;
//import net.gymsrote.repository.ProductRepo;
//import net.gymsrote.repository.ProductVariationRepo;
//import net.gymsrote.service.util.ServiceUtils;
//import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
//import net.gymsrote.entity.EnumEntity.EProductStatus;
//import net.gymsrote.entity.EnumEntity.EProductVariationStatus;
//import net.gymsrote.entity.EnumEntity.PlatformPolicyParameter;
//import net.gymsrote.controller.payload.request.filter.PagingInfo;
//import net.gymsrote.controller.payload.request.filter.ProductFilter;

@Service
public class ProductService {
//	@Autowired
//	LogService logService;
//
//	@Autowired
//	ProductRepo productRepo;
//
//	@Autowired
//	ProductImageService productImageService;
//
//	@Autowired
//	MediaResourceService mediaResourceService;
//
//	@Autowired
//	ProductVariationService productVariationService;
//
//	@Autowired
//	ProductCategoryRepo productCategoryRepo;
//
//	@Autowired
//	ServiceUtils serviceUtils;
//
//	public ListWithPagingResponse<ProductGeneralDetailDTO> search(ProductFilter filter,
//			PagingInfo pagingInfo) {
//		return serviceUtils.convertToListResponse(productRepo.search(filter, pagingInfo),
//				ProductGeneralDetailDTO.class);
//	}
//
//	public ListResponse<ProductGeneralDetailDTO> getTopSaleProduct() {
//		return serviceUtils.convertToListResponse(
//				productRepo.findTop10ByStatusOrderByMaxDiscountDesc(EProductStatus.ENABLED),
//				ProductGeneralDetailDTO.class);
//	}
//
//	public ListResponse<ProductGeneralDetailDTO> getTopLastedProduct() {
//		return serviceUtils.convertToListResponse(
//				productRepo.findTop10ByStatusOrderByCreatedDateDesc(EProductStatus.ENABLED),
//				ProductGeneralDetailDTO.class);
//	}
//
//	public ListResponse<ProductGeneralDetailDTO> getTopVisitProduct() {
//		return serviceUtils.convertToListResponse(
//				productRepo.findTop10ByStatusOrderByNvisitDesc(EProductStatus.ENABLED),
//				ProductGeneralDetailDTO.class);
//	}
//
//	public ListResponse<ProductGeneralDetailDTO> getTopSoldProduct() {
//		return serviceUtils.convertToListResponse(
//				productRepo.findTop10ByStatusOrderByNsoldDesc(EProductStatus.ENABLED),
//				ProductGeneralDetailDTO.class);
//	}
//
//	public DataResponse<ProductDetailDTO> getById(Long id, boolean isBuyer) {
//		Product p = productRepo.findById(id)
//				.orElseThrow(() -> new InvalidInputDataException("No product found with given id"));
//		if (isBuyer && (p.getStatus() == EProductStatus.DISABLED || serviceUtils
//				.checkStatusProductCategory(p.getCategory(), EProductCategoryStatus.BANNED)))
//			throw new InvalidInputDataException("No product found with given id");
//		else {
//			if (isBuyer)
//				productRepo.updateVisitCount(id);
//			if (p.getCategory().getParent() != null)
//				p.setParents(productCategoryRepo.findAncestry(p.getCategory().getParent().getId()));
//		}
//		return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
//	}
//
//	@Transactional
//	public DataResponse<ProductDetailDTO> create(Long idUser, CreateProductRequest data,
//			MultipartFile avatar, List<MultipartFile> images) {
//		var productCategory = productCategoryRepo.findById(data.getIdCategory()).orElseThrow(
//				() -> new InvalidInputDataException("No category found with given id"));
//		if (!serviceUtils.checkStatusProductCategory(productCategory,
//				EProductCategoryStatus.ACTIVE))
//			throw new InvalidInputDataException(
//					"Can not create new product to this category or its parent category because it has been disabled");
//		if (data.getVariations().size() < PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION) {
//			throw new InvalidInputDataException(
//					String.format("At least %d product variation(s) is required",
//							PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION));
//		}
//		Product p;
//		try {
//			p = new Product(productCategoryRepo.getReferenceById(data.getIdCategory()),
//					data.getName(), data.getDescription(),
//					mediaResourceService.save(avatar.getBytes()), EProductStatus.DISABLED);
//		} catch (IOException e) {
//			throw new CommonRuntimeException(
//					"Error occurred when trying to save product avatar image");
//		}
//		p = productRepo.saveAndFlush(p);
//		productImageService.create(p, images);
//		productVariationService.create(p, data.getVariations());
//		productRepo.refresh(p);
//		updatePrice(p, p.getVariations());
//		if (p.getCategory().getParent() != null) {
//			p.setParents(productCategoryRepo.findAncestry(p.getCategory().getParent().getId()));
//		}
//		logService.logInfo(idUser,
//				String.format("New product has been created with id %d", p.getId()));
//		return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
//	}
//
//	@Transactional
//	public DataResponse<ProductDetailDTO> update(Long idUser, Long id, UpdateProductRequest data,
//			MultipartFile avatar) {
//		Product p = productRepo.findById(id)
//				.orElseThrow(() -> new InvalidInputDataException("No product found with given id"));
//		if (data != null) {
//			if (StringUtils.isNotEmpty(data.getName()) && !p.getName().equals(data.getName()))
//				p.setName(data.getName());
//			if (StringUtils.isNotEmpty(data.getDescription())
//					&& !p.getDescription().equals(data.getDescription()))
//				p.setDescription(data.getDescription());
//			if (data.getIdCategory() != null
//					&& !p.getCategory().getId().equals(data.getIdCategory())) {
//				if (!productCategoryRepo.existsById(data.getIdCategory()))
//					throw new InvalidInputDataException("No category found with given id");
//				p.setCategory(productCategoryRepo.getReferenceById(data.getIdCategory()));
//			}
//			if (data.getStatus() != null && !p.getStatus().equals(data.getStatus()))
//				p.setStatus(data.getStatus());
//		}
//		if (avatar != null) {
//			serviceUtils.updateAvatar(p, avatar);
//		}
//		p = productRepo.save(p);
//		if (p.getCategory().getParent() != null)
//			p.setParents(productCategoryRepo.findAncestry(p.getCategory().getParent().getId()));
//		logService.logInfo(idUser, String.format("Product with id %d has been edited", p.getId()));
//		return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
//	}
//
//	public void updateRating(Long id, Integer point) {
//		switch (point) {
//			case 1:
//				productRepo.updateRating1(id);
//				break;
//			case 2:
//				productRepo.updateRating2(id);
//				break;
//			case 3:
//				productRepo.updateRating3(id);
//				break;
//			case 4:
//				productRepo.updateRating4(id);
//				break;
//			case 5:
//				productRepo.updateRating5(id);
//				break;
//		}
//		productRepo.updateAverageRating(id, point);
//	}
//
//	/***
//	 * For entity listener
//	 * 
//	 * @param pv
//	 */
//	public void updatePriceWhenVariationCreated(ProductVariation pv) {
//		Product p = pv.getProduct();
//		List<ProductVariation> pvs = new ArrayList<>(p.getVariations());
//		pvs.add(pv);
//		updatePrice(p, pvs);
//	}
//
//	/***
//	 * For entity listener
//	 * 
//	 * @param pv
//	 */
//	public void updatePriceWhenVariationChange(ProductVariation pv) {
//		Product p = pv.getProduct();
//		List<ProductVariation> pvs = new ArrayList<>(p.getVariations());
//		updatePrice(p, pvs);
//	}
//
//	private void updatePrice(Product p, List<ProductVariation> pvs) {
//		Supplier<Stream<ProductVariation>> streamSupplier =
//				() -> pvs.stream().filter(x -> x.getAvailableQuantity() > 0
//						&& x.getStatus() == EProductVariationStatus.ENABLED);
//		Optional<ProductVariation> maxDiscount = streamSupplier.get()
//				.max((first, second) -> Long.compare(first.getDiscount(), second.getDiscount()));
//		Optional<ProductVariation> minPv = streamSupplier.get().min((first, second) -> Long
//				.compare(first.getPriceAfterDiscount(), second.getPriceAfterDiscount()));
//		Optional<ProductVariation> maxPv = streamSupplier.get().max((first, second) -> Long
//				.compare(first.getPriceAfterDiscount(), second.getPriceAfterDiscount()));
//		if (maxDiscount.isPresent())
//			p.setMaxDiscount(maxDiscount.get().getDiscount());
//		if (minPv.isPresent())
//			p.setMinPrice(minPv.get().getPriceAfterDiscount());
//		if (maxPv.isPresent())
//			p.setMaxPrice(maxPv.get().getPriceAfterDiscount());
//		productRepo.save(p);
//	}
//
//	public List<Long> getAllParentCategories(Long idCategory, boolean isBuyer) {
//		List<Long> idsCategory = new ArrayList<>();
//		if (idCategory != null) {
//			var pc = productCategoryRepo.findById(idCategory).orElse(null);
//			if (pc != null) {
//				if (isBuyer) {
//					if (serviceUtils.checkStatusProductCategory(pc, EProductCategoryStatus.BANNED))
//						throw new InvalidInputDataException("Given category id has been banned");
//					else {
//						if (!pc.getChild().isEmpty()) {
//							for (var c : pc.getChild()) {
//								if (c.getStatus() != EProductCategoryStatus.BANNED)
//									idsCategory.add(c.getId());
//							}
//						}
//					}
//				} else
//					idsCategory.addAll(pc.getChild().stream().map(ProductCategory::getId).toList());
//				idsCategory.add(idCategory);
//			} else
//				throw new InvalidInputDataException("Given category id does not exists");
//		}
//		return idsCategory;
//	}
//	/*** FOR DATA GENERATION ***/
//	/*
//	 * public boolean tier(TestRequest data) { var ps =
//	 * productRepo.findByName(data.getProductName()); if (ps.isEmpty()) return false; for (var p :
//	 * ps) { if (p.getId() == 27L) { System.out.println("hi"); } assignTier(p,
//	 * data.getTier_variations()); } return true; }
//	 * 
//	 * @Autowired ProductVariationRepo productVariationRepo;
//	 * 
//	 * @Transactional private void assignTier(Product p, List<TierVariation> tiers) { for (var tier
//	 * : tiers) { for (var pvName : tier.getOptions()) { var pvs =
//	 * productVariationRepo.findByProductAndVariationName(p, pvName); if (pvs.size() > 1) continue;
//	 * //return; //throw new CommonRuntimeException("Duplicate variation <" +
//	 * pvs.get(0).getVariationName() + ">"); else if (pvs.isEmpty()) continue; //return; //throw new
//	 * CommonRuntimeException("Not found variation <" + pvName + "> in product id " +
//	 * p.getId().toString()); else { var pv = pvs.get(0); pv.setTier(tier.getName());
//	 * productVariationRepo.save(pv); } } } }
//	 */
}
